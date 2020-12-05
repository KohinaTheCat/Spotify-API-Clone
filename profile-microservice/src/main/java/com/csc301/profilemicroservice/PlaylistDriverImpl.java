package com.csc301.profilemicroservice;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class PlaylistDriverImpl implements PlaylistDriver {

  Driver driver = ProfileMicroserviceApplication.driver;

  public static void InitPlaylistDb() {
    String queryStr;

    try (Session session = ProfileMicroserviceApplication.driver.session()) {
      try (Transaction trans = session.beginTransaction()) {
        queryStr = "CREATE CONSTRAINT ON (nPlaylist:playlist) ASSERT exists(nPlaylist.plName)";
        trans.run(queryStr);
        trans.success();
      }
      session.close();
    }
  }

  /**
   * Like song
   * 
   * @param userName username of user
   * @param songId   id of the song
   */
  @Override
  public DbQueryStatus likeSong(String userName, String songId) {
    boolean valid = userName != null && songId != null;
    String queryStr;
    StatementResult res;

    if (!valid)
      return new DbQueryStatus("PUT", DbQueryExecResult.QUERY_ERROR_GENERIC);

    try (Session session = driver.session()) {
      Map<String, Object> params = new HashMap<>();
      params.put("plName", userName + "-favourites");
      params.put("songId", songId);
      try (Transaction trans = session.beginTransaction()) {

        // Check if playlist of user node exists
        queryStr = "MATCH (p:playlist {plName: $plName}) RETURN p";
        res = trans.run(queryStr, params);
        if (!res.hasNext())
          return new DbQueryStatus("PUT", DbQueryExecResult.QUERY_ERROR_GENERIC);

        // Check if song node exists
        queryStr = "MATCH (s:song {songId: $songId}) RETURN s";
        res = trans.run(queryStr, params);
        if (!res.hasNext())
          return new DbQueryStatus("PUT", DbQueryExecResult.QUERY_ERROR_GENERIC);

        // Check if existing relationship exists
        queryStr = "MATCH r=(p:playlist {plName: $plName})-[:includes]->(s:song {songId: $songId}) RETURN r";
        res = trans.run(queryStr, params);
        if (res.hasNext())
          return new DbQueryStatus("PUT", DbQueryExecResult.QUERY_ERROR_NOT_FOUND);

        queryStr = "MATCH (p:playlist {plName: $plName}) \n  MATCH (s:song {songId: $songId}) \n CREATE (p)-[:includes]->(s)";
        trans.run(queryStr, params);
        trans.success();
      }
      session.close();
      return new DbQueryStatus("PUT", DbQueryExecResult.QUERY_OK);
    } catch (Exception e) {
      e.printStackTrace();
      return new DbQueryStatus("PUT", DbQueryExecResult.QUERY_ERROR_GENERIC);
    }
  }

  /**
   * Unlike song
   * 
   * @param userName username of user
   * @param songId   id of the song
   */
  @Override
  public DbQueryStatus unlikeSong(String userName, String songId) {
    boolean valid = userName != null && songId != null;
    String queryStr;
    StatementResult res;

    if (!valid)
      return new DbQueryStatus("PUT", DbQueryExecResult.QUERY_ERROR_GENERIC);

    try (Session session = driver.session()) {
      Map<String, Object> params = new HashMap<>();
      params.put("plName", userName + "-favourites");
      params.put("songId", songId);
      try (Transaction trans = session.beginTransaction()) {

        // Check if playlist of user node exists
        queryStr = "MATCH (p:playlist {plName: $plName}) RETURN p";
        res = trans.run(queryStr, params);
        if (!res.hasNext())
          return new DbQueryStatus("PUT", DbQueryExecResult.QUERY_ERROR_GENERIC);

        // Check if song node exists
        queryStr = "MATCH (s:song {songId: $songId}) RETURN s";
        res = trans.run(queryStr, params);
        if (!res.hasNext())
          return new DbQueryStatus("PUT", DbQueryExecResult.QUERY_ERROR_GENERIC);

        // Check if existing relationship exists
        queryStr = "MATCH (p:playlist {plName: $plName})-[r:includes]->(s:song {songId: $songId}) RETURN r";
        res = trans.run(queryStr, params);
        if (!res.hasNext())
          return new DbQueryStatus("PUT", DbQueryExecResult.QUERY_ERROR_GENERIC);

        queryStr = "MATCH (p:playlist {plName: $plName})-[r:includes]->(s:song {songId: $songId}) DELETE r";
        trans.run(queryStr, params);
        trans.success();
      }
      session.close();
      return new DbQueryStatus("PUT", DbQueryExecResult.QUERY_OK);
    } catch (Exception e) {
      e.printStackTrace();
      return new DbQueryStatus("PUT", DbQueryExecResult.QUERY_ERROR_GENERIC);
    }
  }

  /**
   * deletes song node given songId
   * 
   * @param songId id of the song
   */
  @Override
  public DbQueryStatus deleteSongFromDb(String songId) {
    String queryStr;
    try (Session session = driver.session()) {
      queryStr = "MATCH (s:song {songId: $songId}) DETACH DELETE s";
      Map<String, Object> params = new HashMap<>();
      params.put("songId", songId);
      session.writeTransaction((Transaction tx) -> tx.run(queryStr, params));
      session.close();
      return new DbQueryStatus("DELETE", DbQueryExecResult.QUERY_OK);
    } catch (Exception e) {
      e.printStackTrace();
      return new DbQueryStatus("DELETE", DbQueryExecResult.QUERY_ERROR_GENERIC);
    }
  }

  /**
   * Adds new song node given id and name. Only newly created Valid Song Ids get
   * passed here
   * 
   * @param songId   id of the song from mongo
   * @param songName name of the song
   */
  public DbQueryStatus addSong(String songId, String songName) {
    String queryStr;
    try (Session session = driver.session()) {
      queryStr = "MERGE (s:song {songId: $songId, songName: $songName})";
      Map<String, Object> params = new HashMap<>();
      params.put("songId", songId);
      params.put("songName", songName);
      session.writeTransaction((Transaction tx) -> tx.run(queryStr, params));
      session.close();
      return new DbQueryStatus("POST", DbQueryExecResult.QUERY_OK);
    } catch (Exception e) {
      e.printStackTrace();
      return new DbQueryStatus("POST", DbQueryExecResult.QUERY_ERROR_GENERIC);
    }
  }
}
