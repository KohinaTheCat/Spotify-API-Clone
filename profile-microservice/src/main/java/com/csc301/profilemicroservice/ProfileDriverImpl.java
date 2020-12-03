package com.csc301.profilemicroservice;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileDriverImpl implements ProfileDriver {

  Driver driver = ProfileMicroserviceApplication.driver;

  public static void InitProfileDb() {
    String queryStr;

    try (Session session = ProfileMicroserviceApplication.driver.session()) {
      try (Transaction trans = session.beginTransaction()) {
        queryStr = "CREATE CONSTRAINT ON (nProfile:profile) ASSERT exists(nProfile.userName)";
        trans.run(queryStr);

        queryStr = "CREATE CONSTRAINT ON (nProfile:profile) ASSERT exists(nProfile.password)";
        trans.run(queryStr);

        queryStr = "CREATE CONSTRAINT ON (nProfile:profile) ASSERT nProfile.userName IS UNIQUE";
        trans.run(queryStr);

        trans.success();
      }
      session.close();
    }
  }

  @Override
  public DbQueryStatus createUserProfile(String userName, String fullName, String password) {
    boolean valid = userName != null && fullName != null && password != null;
    if (!valid)
      return new DbQueryStatus("POST", DbQueryExecResult.QUERY_ERROR_GENERIC);

    try (Session session = driver.session()) {
      Map<String, Object> params = new HashMap<>();
      params.put("userName", userName);
      params.put("fullName", fullName);
      params.put("password", password);
      params.put("plName", userName + "-favourites");

      session.writeTransaction((Transaction tx) -> tx.run(
          "CREATE (m:profile {userName: $userName, fullName: $fullName, password: $password})-[r:created]->(n:playlist {plName: $plName})",
          params));

      session.close();
      return new DbQueryStatus("POST", DbQueryExecResult.QUERY_OK);
    } catch (Exception e) {
      System.out.println(e);
      return new DbQueryStatus("POST", DbQueryExecResult.QUERY_ERROR_GENERIC);
    }
  }

  @Override
  public DbQueryStatus followFriend(String userName, String frndUserName) {
    boolean valid = userName != null && frndUserName != null;

    if (!valid)
      return new DbQueryStatus("POST", DbQueryExecResult.QUERY_ERROR_GENERIC);

    // TODO: check if we return ok even after person tries to follow themselves
    if (userName.equals(frndUserName))
      return new DbQueryStatus("POST", DbQueryExecResult.QUERY_OK);

    try (Session session = driver.session()) {
      Map<String, Object> params = new HashMap<>();
      params.put("userName", userName);
      params.put("frndUserName", frndUserName);

      boolean followed = false;

      try (Transaction trans = session.beginTransaction()) {
        StatementResult existUserRes = trans.run("MATCH (n:profile {userName: $userName}) RETURN n.userName as u",
            params);
        StatementResult existFrndRes = trans.run("MATCH (n:profile {userName: $frndUserName}) RETURN n.userName as f",
            params);

        // CASE: at least one user doesn't exist
        if (!existUserRes.hasNext() || !existFrndRes.hasNext())
          return new DbQueryStatus("POST", DbQueryExecResult.QUERY_ERROR_GENERIC);

        StatementResult result = trans.run(
            "RETURN EXISTS( (:profile {userName: $userName})-[:follows]-(:profile {userName: $frndUserName}) ) as bool",
            params);

        if (result.hasNext()) {
          Record record = result.next();
          followed = record.get("bool").asBoolean();

          if (!followed)
            trans.run(
                "MATCH (a:profile),(b:profile) \n WHERE a.userName = $userName AND b.userName = $frndUserName \nCREATE (a)-[r:follows]->(b)",
                params);

          trans.success();
        }
      }
      session.close();

      // TODO: check if we return ok even after dup. follows
      return new DbQueryStatus("POST", DbQueryExecResult.QUERY_OK);

    } catch (Exception e) {
      return new DbQueryStatus("POST", DbQueryExecResult.QUERY_ERROR_GENERIC);
    }
  }

  @Override
  public DbQueryStatus unfollowFriend(String userName, String frndUserName) {
    boolean valid = userName != null && frndUserName != null;

    if (!valid)
      return new DbQueryStatus("POST", DbQueryExecResult.QUERY_ERROR_GENERIC);

    // TODO: check if we return ok even after person tries to follow themselves
    if (userName.equals(frndUserName))
      return new DbQueryStatus("POST", DbQueryExecResult.QUERY_OK);

    try (Session session = driver.session()) {
      Map<String, Object> params = new HashMap<>();
      params.put("userName", userName);
      params.put("frndUserName", frndUserName);

      try (Transaction trans = session.beginTransaction()) {
        StatementResult existUserRes = trans.run("MATCH (n:profile {userName: $userName}) RETURN n.userName as u",
            params);
        StatementResult existFrndRes = trans.run("MATCH (n:profile {userName: $frndUserName}) RETURN n.userName as f",
            params);

        // CASE: at least one user doesn't exist
        if (!existUserRes.hasNext() || !existFrndRes.hasNext())
          return new DbQueryStatus("POST", DbQueryExecResult.QUERY_ERROR_GENERIC);

        trans.run(
            "MATCH (n:profile { userName: $userName })-[r:follows]->(m:profile { userName: $frndUserName }) DELETE r",
            params);

        trans.success();
      }

      session.close();

      // TODO: check if we return ok even after dup. unfollows
      return new DbQueryStatus("POST", DbQueryExecResult.QUERY_OK);

    } catch (Exception e) {
      return new DbQueryStatus("POST", DbQueryExecResult.QUERY_ERROR_GENERIC);
    }
  }

  @Override
  public DbQueryStatus getAllSongFriendsLike(String userName) {
    boolean valid = userName != null;
    String queryStr;
    StatementResult res;

    DbQueryStatus dbQueryStatus = new DbQueryStatus("GET", DbQueryExecResult.QUERY_OK);

    if (!valid)
      new DbQueryStatus("GET", DbQueryExecResult.QUERY_ERROR_GENERIC);

    try (Session session = driver.session()) {
      Map<String, Object> params = new HashMap<>();
      params.put("userName", userName);

      try (Transaction trans = session.beginTransaction()) {
        queryStr = "MATCH (a:profile {userName: $userName})-[:follows]->(b:profile) \n MATCH (c:playlist {plName: b.userName + '-favourites'})-[:includes]->(s:song) \n RETURN b.userName as username, collect(s.songName) as songs";
        res = trans.run(queryStr, params);
        Map<String, Object> data = new HashMap<>();
        Record rec;
        if (!res.hasNext())
          return new DbQueryStatus("GET", DbQueryExecResult.QUERY_ERROR_GENERIC);
        while (res.hasNext()) {
          rec = res.next();
          data.put(rec.get("username").asString(), rec.get("songs").asList());
        }
        dbQueryStatus.setData(data);
        trans.success();
      }
      session.close();
      dbQueryStatus.setdbQueryExecResult(DbQueryExecResult.QUERY_OK);
      return dbQueryStatus;
    } catch (Exception e) {
      return new DbQueryStatus("GET", DbQueryExecResult.QUERY_ERROR_GENERIC);
    }

  }
}