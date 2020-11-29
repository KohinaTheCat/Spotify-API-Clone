package com.csc301.profilemicroservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import org.springframework.stereotype.Repository;
import org.neo4j.driver.v1.Transaction;

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

    DbQueryStatus status;

    try (Session session = driver.session()) {
      Map<String, Object> params = new HashMap<>();
      params.put("userName", userName);
      params.put("fullName", fullName);
      params.put("password", password);
      params.put("plName", userName + "-favorites");

      // TODO: error when adding duplicates
      session.writeTransaction((Transaction tx) -> tx
          .run("CREATE (m:profile {userName: $userName, fullName: $fullName, password: $password})-[r:created]->(n:playlist {plName: $plName})", params));

      status = new DbQueryStatus("POST", DbQueryExecResult.QUERY_OK);
      session.close();
    } catch (Exception e) {
      System.out.println(e);
      status = new DbQueryStatus("POST", DbQueryExecResult.QUERY_ERROR_GENERIC);
    }

    return status;
  }

  @Override
  public DbQueryStatus followFriend(String userName, String frndUserName) {
    boolean valid = userName != null && frndUserName != null;
    if (!valid)
      return new DbQueryStatus("POST", DbQueryExecResult.QUERY_ERROR_GENERIC);

    DbQueryStatus status;

    try (Session session = driver.session()) {
      Map<String, Object> params = new HashMap<>();
      params.put("userName", userName);
      params.put("frndUserName", frndUserName);

      // TODO: verify duplicate relationships
      session.writeTransaction((Transaction tx) -> tx.run(
          "MATCH (a:profile),(b:profile) \n WHERE a.userName = $userName AND b.userName = $frndUserName \nCREATE (a)-[r:follows]->(b)",
          params));

      status = new DbQueryStatus("POST", DbQueryExecResult.QUERY_OK);
      session.close();
    } catch (Exception e) {
      System.out.println(e);
      status = new DbQueryStatus("POST", DbQueryExecResult.QUERY_ERROR_GENERIC);
    }

    return status;
  }

  @Override
  public DbQueryStatus unfollowFriend(String userName, String frndUserName) {
    boolean valid = userName != null && frndUserName != null;
    if (!valid)
      return new DbQueryStatus("POST", DbQueryExecResult.QUERY_ERROR_GENERIC);

    DbQueryStatus status;

    try (Session session = driver.session()) {
      Map<String, Object> params = new HashMap<>();
      params.put("userName", userName);
      params.put("frndUserName", frndUserName);

      // TODO: verify if we have to check if there was a relationship before
      session.writeTransaction((Transaction tx) -> tx.run(
          "MATCH (n:profile { userName: $userName })-[r:follows]->(m:profile { userName: $frndUserName }) DELETE r",
          params));

      status = new DbQueryStatus("POST", DbQueryExecResult.QUERY_OK);
      session.close();
    } catch (Exception e) {
      System.out.println(e);
      status = new DbQueryStatus("POST", DbQueryExecResult.QUERY_ERROR_GENERIC);
    }

    return status;
  }

  @Override
  public DbQueryStatus getAllSongFriendsLike(String userName) {

    return null;
  }
}
