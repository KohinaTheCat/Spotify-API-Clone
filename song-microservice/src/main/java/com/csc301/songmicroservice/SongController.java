package com.csc301.songmicroservice;

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
public class SongController {

  @Autowired
  private final SongDal songDal;

  private OkHttpClient client = new OkHttpClient();

  public SongController(SongDal songDal) {
    this.songDal = songDal;
  }

  /**
   * Get song by id
   * 
   * @param songId  id of the song
   * @param request HTTP Request
   * @return Response Body
   */
  @RequestMapping(value = "/getSongById/{songId}", method = RequestMethod.GET)
  public @ResponseBody Map<String, Object> getSongById(@PathVariable("songId") String songId,
      HttpServletRequest request) {

    Map<String, Object> response = new HashMap<String, Object>();
    response.put("path", String.format("GET %s", Utils.getUrl(request)));

    DbQueryStatus dbQueryStatus = songDal.findSongById(songId);

    response.put("message", dbQueryStatus.getMessage());
    response = Utils.setResponseStatus(response, dbQueryStatus.getdbQueryExecResult(), dbQueryStatus.getData());

    return response;
  }

  /**
   * Get Song Title By Id
   * 
   * @param songId  id of the song
   * @param request HTTP Request
   * @return Response Body
   */
  @RequestMapping(value = "/getSongTitleById/{songId}", method = RequestMethod.GET)
  public @ResponseBody Map<String, Object> getSongTitleById(@PathVariable("songId") String songId,
      HttpServletRequest request) {

    Map<String, Object> response = new HashMap<String, Object>();
    response.put("path", String.format("GET %s", Utils.getUrl(request)));

    DbQueryStatus dbQueryStatus = songDal.getSongTitleById(songId);

    response.put("message", dbQueryStatus.getMessage());
    response = Utils.setResponseStatus(response, dbQueryStatus.getdbQueryExecResult(), dbQueryStatus.getData());

    return response;
  }

  /**
   * Delete Song By Id
   * 
   * @param songId  id of the song
   * @param request HTTP Request
   * @return Response Body
   */
  @RequestMapping(value = "/deleteSongById/{songId}", method = RequestMethod.DELETE)
  public @ResponseBody Map<String, Object> deleteSongById(@PathVariable("songId") String songId,
      HttpServletRequest request) {

    Map<String, Object> response = new HashMap<String, Object>();
    response.put("path", String.format("DELETE %s", Utils.getUrl(request)));

    // mongo driver delete
    DbQueryStatus dbQueryStatus = songDal.deleteSongById(songId);

    if (dbQueryStatus.getdbQueryExecResult().equals(DbQueryExecResult.QUERY_OK)) {
      // Internal endpoint from profile microservice
      String url = "http://localhost:3002/deleteAllSongsFromDb/" + songId;
      RequestBody formBody = new FormBody.Builder().build();
      Request req = new Request.Builder().url(url).put(formBody).build();

      try {
        Response res = client.newCall(req).execute();
        JSONObject body = new JSONObject(res.body().string());
        if (!body.get("status").toString().equals("OK"))
          dbQueryStatus.setdbQueryExecResult(DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
      } catch (Exception e) {
        dbQueryStatus.setdbQueryExecResult(DbQueryExecResult.QUERY_ERROR_GENERIC);
        e.printStackTrace();
      }
    }

    response.put("message", dbQueryStatus.getMessage());
    response = Utils.setResponseStatus(response, dbQueryStatus.getdbQueryExecResult(), dbQueryStatus.getData());

    return response;
  }

  /**
   * Creates a new song
   * 
   * @param params  song name, artist full name, album name
   * @param request HTTP Request
   * @return Response Body
   */
  @RequestMapping(value = "/addSong", method = RequestMethod.POST)
  public @ResponseBody Map<String, Object> addSong(@RequestParam Map<String, String> params,
      HttpServletRequest request) {

    Map<String, Object> response = new HashMap<String, Object>();
    response.put("path", String.format("POST %s", Utils.getUrl(request)));

    // New Song instance + mongo driver add
    Song songToAdd = new Song(params.get(Song.KEY_SONG_NAME), params.get(Song.KEY_SONG_ARTIST_FULL_NAME),
        params.get(Song.KEY_SONG_ALBUM));
    DbQueryStatus dbQueryStatus = songDal.addSong(songToAdd);

    if (dbQueryStatus.getdbQueryExecResult().equals(DbQueryExecResult.QUERY_OK)) {
      @SuppressWarnings("unchecked") // unchecked cast supression from getting dbquery data
      Map<String, String> song = (Map<String, String>) dbQueryStatus.getData();

      // internal add from profile microservice
      String url = "http://localhost:3002/addSong/" + song.get("id") + "/" + song.get("songName");
      RequestBody formBody = new FormBody.Builder().build();
      Request req = new Request.Builder().url(url).post(formBody).build();

      try {
        Response res = client.newCall(req).execute();
        JSONObject body = new JSONObject(res.body().string());
        if (!body.get("status").toString().equals("OK"))
          dbQueryStatus.setdbQueryExecResult(DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
      } catch (Exception e) {
        dbQueryStatus.setdbQueryExecResult(DbQueryExecResult.QUERY_ERROR_GENERIC);
        e.printStackTrace();
      }
    }

    response.put("message", dbQueryStatus.getMessage());
    response = Utils.setResponseStatus(response, dbQueryStatus.getdbQueryExecResult(), dbQueryStatus.getData());

    return response;
  }

  /**
   * Update song fav count
   * 
   * @param songId          id of the song
   * @param shouldDecrement if true then count--; else count++
   * @param request         HTTP Request
   * @return Response Body
   */
  @RequestMapping(value = "/updateSongFavouritesCount/{songId}", method = RequestMethod.PUT)
  public @ResponseBody Map<String, Object> updateFavouritesCount(@PathVariable("songId") String songId,
      @RequestParam("shouldDecrement") String shouldDecrement, HttpServletRequest request) {

    Map<String, Object> response = new HashMap<String, Object>();
    response.put("path", String.format("PUT %s", Utils.getUrl(request)));

    DbQueryStatus dbQueryStatus = songDal.updateSongFavouritesCount(songId, shouldDecrement.equals("true"));

    response.put("message", dbQueryStatus.getMessage());
    response = Utils.setResponseStatus(response, dbQueryStatus.getdbQueryExecResult(), dbQueryStatus.getData());

    return response;

  }
}