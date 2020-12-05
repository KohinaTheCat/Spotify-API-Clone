package com.csc301.profilemicroservice;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@RestController
@RequestMapping("/")
public class ProfileController {
  public static final String KEY_USER_NAME = "userName";
  public static final String KEY_USER_FULLNAME = "fullName";
  public static final String KEY_USER_PASSWORD = "password";

  @Autowired
  private final ProfileDriverImpl profileDriver;

  @Autowired
  private final PlaylistDriverImpl playlistDriver;

  OkHttpClient client = new OkHttpClient();

  public ProfileController(ProfileDriverImpl profileDriver, PlaylistDriverImpl playlistDriver) {
    this.profileDriver = profileDriver;
    this.playlistDriver = playlistDriver;
  }

  /**
   * POST /profile. Adds a profile to the Neo4j database
   * 
   * @param params  username, full name, and password
   * @param request HTTP Request
   * @return Response body
   */
  @RequestMapping(value = "/profile", method = RequestMethod.POST)
  public @ResponseBody Map<String, Object> addProfile(@RequestParam Map<String, String> params,
      HttpServletRequest request) {

    Map<String, Object> response = new HashMap<String, Object>();
    response.put("path", String.format("POST %s", Utils.getUrl(request)));

    DbQueryStatus dbQueryStatus = profileDriver.createUserProfile(params.get(KEY_USER_NAME),
        params.get(KEY_USER_FULLNAME), params.get(KEY_USER_PASSWORD));

    response.put("message", dbQueryStatus.getMessage());
    response = Utils.setResponseStatus(response, dbQueryStatus.getdbQueryExecResult(), dbQueryStatus.getData());

    return response;
  }

  /**
   * PUT /followFriend/{userName}/{friendUserName}. Allows a Profile to follow
   * another Profile and become a friend
   * 
   * @param userName       the user who will be doing the following
   * @param friendUserName the user who will be followed
   * @param request        HTTP Request
   * @return Response Body
   */
  @RequestMapping(value = "/followFriend/{userName}/{friendUserName}", method = RequestMethod.PUT)
  public @ResponseBody Map<String, Object> followFriend(@PathVariable("userName") String userName,
      @PathVariable("friendUserName") String friendUserName, HttpServletRequest request) {

    Map<String, Object> response = new HashMap<String, Object>();
    response.put("path", String.format("PUT %s", Utils.getUrl(request)));

    DbQueryStatus dbQueryStatus = profileDriver.followFriend(userName, friendUserName);

    response.put("message", dbQueryStatus.getMessage());
    response = Utils.setResponseStatus(response, dbQueryStatus.getdbQueryExecResult(), dbQueryStatus.getData());

    return response;
  }

  /**
   * GET /getAllFriendFavouriteSongTitles/{userName}. Returns the Song names of
   * all of the Songs that the User’s friends have liked
   * 
   * @param userName username of user
   * @param request  HTTP Request
   * @return Response body
   * 
   */
  @RequestMapping(value = "/getAllFriendFavouriteSongTitles/{userName}", method = RequestMethod.GET)
  public @ResponseBody Map<String, Object> getAllFriendFavouriteSongTitles(@PathVariable("userName") String userName,
      HttpServletRequest request) {

    Map<String, Object> response = new HashMap<String, Object>();
    response.put("path", String.format("GET %s", Utils.getUrl(request)));

    DbQueryStatus dbQueryStatus = profileDriver.getAllSongFriendsLike(userName);

    response.put("message", dbQueryStatus.getMessage());
    response = Utils.setResponseStatus(response, dbQueryStatus.getdbQueryExecResult(), dbQueryStatus.getData());

    return response;
  }

  /**
   * PUT /unfollowFriend/{userName}/{friendUserName}. Allows a Profile to unfollow
   * another Profile and no longer be “friends” with them
   * 
   * @param userName       the user who will be doing the un-following
   * @param friendUserName the user who will be un-followed
   * @param request        HTTP Request
   * @return Response Body
   * 
   */
  @RequestMapping(value = "/unfollowFriend/{userName}/{friendUserName}", method = RequestMethod.PUT)
  public @ResponseBody Map<String, Object> unfollowFriend(@PathVariable("userName") String userName,
      @PathVariable("friendUserName") String friendUserName, HttpServletRequest request) {

    Map<String, Object> response = new HashMap<String, Object>();
    response.put("path", String.format("PUT %s", Utils.getUrl(request)));

    DbQueryStatus dbQueryStatus = profileDriver.unfollowFriend(userName, friendUserName);

    response.put("message", dbQueryStatus.getMessage());
    response = Utils.setResponseStatus(response, dbQueryStatus.getdbQueryExecResult(), dbQueryStatus.getData());

    return response;
  }

  /**
   * PUT /likeSong/{userName}/{songId}. Allows a Profile to like a song and add it
   * to their favourites. You can like the same song twice
   * 
   * @param userName username of the liker
   * @param songId   id of the to-be-liked song
   * @param request  HTTP Request
   * @return Response body
   */
  @RequestMapping(value = "/likeSong/{userName}/{songId}", method = RequestMethod.PUT)
  public @ResponseBody Map<String, Object> likeSong(@PathVariable("userName") String userName,
      @PathVariable("songId") String songId, HttpServletRequest request) {

    Map<String, Object> response = new HashMap<String, Object>();
    response.put("path", String.format("PUT %s", Utils.getUrl(request)));

    // Endpoint from song microservice
    String url = "http://localhost:3001/updateSongFavouritesCount/" + songId + "?shouldDecrement=false";
    RequestBody formBody = new FormBody.Builder().build();
    Request req = new Request.Builder().url(url).put(formBody).build();

    DbQueryStatus dbQueryStatus = playlistDriver.likeSong(userName, songId);

    try {
      if (dbQueryStatus.getdbQueryExecResult().equals(DbQueryExecResult.QUERY_OK)) {
        Response res = client.newCall(req).execute();
        JSONObject body = new JSONObject(res.body().string());
        if (!body.get("status").toString().equals("OK"))
          dbQueryStatus.setdbQueryExecResult(DbQueryExecResult.QUERY_ERROR_GENERIC);
      } else if (dbQueryStatus.getdbQueryExecResult().equals(DbQueryExecResult.QUERY_ERROR_NOT_FOUND))
        dbQueryStatus.setdbQueryExecResult(DbQueryExecResult.QUERY_OK); // CASE: dup. like song Piazza @447
    } catch (Exception e) {
      dbQueryStatus.setdbQueryExecResult(DbQueryExecResult.QUERY_ERROR_GENERIC);
      e.printStackTrace();
    }

    response.put("message", dbQueryStatus.getMessage());
    response = Utils.setResponseStatus(response, dbQueryStatus.getdbQueryExecResult(), dbQueryStatus.getData());

    return response;
  }

  /**
   * PUT /unlikeSong/{userName}/{songId}. : Allows a Profile to unlike a song and
   * remove it from their favourites. You cannot unlike the same song twice.
   * 
   * @param userName username of the un-liker
   * @param songId   id of the to-be-unliked song
   * @param request  HTTP Request
   * @return Response Body
   */
  @RequestMapping(value = "/unlikeSong/{userName}/{songId}", method = RequestMethod.PUT)
  public @ResponseBody Map<String, Object> unlikeSong(@PathVariable("userName") String userName,
      @PathVariable("songId") String songId, HttpServletRequest request) {

    Map<String, Object> response = new HashMap<String, Object>();
    response.put("path", String.format("PUT %s", Utils.getUrl(request)));

    // Endpoint from song microservice
    String url = "http://localhost:3001/updateSongFavouritesCount/" + songId + "?shouldDecrement=true";
    RequestBody formBody = new FormBody.Builder().build();
    Request req = new Request.Builder().url(url).put(formBody).build();

    DbQueryStatus dbQueryStatus = playlistDriver.unlikeSong(userName, songId);

    try {
      if (dbQueryStatus.getdbQueryExecResult().equals(DbQueryExecResult.QUERY_OK)) {
        Response res = client.newCall(req).execute();
        JSONObject body = new JSONObject(res.body().string());
        if (!body.get("status").toString().equals("OK"))
          dbQueryStatus.setdbQueryExecResult(DbQueryExecResult.QUERY_ERROR_GENERIC);
      }
    } catch (Exception e) {
      dbQueryStatus.setdbQueryExecResult(DbQueryExecResult.QUERY_ERROR_GENERIC);
      e.printStackTrace();
    }

    response.put("message", dbQueryStatus.getMessage());
    response = Utils.setResponseStatus(response, dbQueryStatus.getdbQueryExecResult(), dbQueryStatus.getData());

    return response;
  }

  /**
   * Delete song node given songId (INTERNAL) - From our implementation, there is
   * only one node for each valid song stored in mongo
   * 
   * @param songId  id of the song to be deleted
   * @param request HTTP Request
   * @return Response body
   */
  @RequestMapping(value = "/deleteAllSongsFromDb/{songId}", method = RequestMethod.PUT)
  public @ResponseBody Map<String, Object> deleteAllSongsFromDb(@PathVariable("songId") String songId,
      HttpServletRequest request) {

    Map<String, Object> response = new HashMap<String, Object>();
    response.put("path", String.format("PUT %s", Utils.getUrl(request)));

    DbQueryStatus dbQueryStatus = playlistDriver.deleteSongFromDb(songId);

    response.put("message", dbQueryStatus.getMessage());
    response = Utils.setResponseStatus(response, dbQueryStatus.getdbQueryExecResult(), dbQueryStatus.getData());

    return response;
  }

  /**
   * Add new song node (INTERNAL) - From our implementation, every time a new song
   * is created in mongo, it is then created here
   * 
   * @param songId   id of song (from mongo)
   * @param songName name of song
   * @param request  HTTP Request
   * @return Response Body
   */
  @RequestMapping(value = "/addSong/{songId}/{songName}", method = RequestMethod.POST)
  public @ResponseBody Map<String, Object> addProfile(@PathVariable("songId") String songId,
      @PathVariable("songName") String songName, HttpServletRequest request) {
    Map<String, Object> response = new HashMap<String, Object>();
    response.put("path", String.format("POST %s", Utils.getUrl(request)));

    DbQueryStatus dbQueryStatus = playlistDriver.addSong(songId, songName);

    response.put("message", dbQueryStatus.getMessage());
    response = Utils.setResponseStatus(response, dbQueryStatus.getdbQueryExecResult(), dbQueryStatus.getData());

    return response;
  }
}