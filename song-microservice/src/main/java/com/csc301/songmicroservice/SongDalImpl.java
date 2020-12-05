package com.csc301.songmicroservice;

import com.mongodb.client.result.DeleteResult;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SongDalImpl implements SongDal {

  private final MongoTemplate db;

  /**
   * readability
   */
  private final DbQueryExecResult OK = DbQueryExecResult.QUERY_OK;
  private final DbQueryExecResult ERR = DbQueryExecResult.QUERY_ERROR_GENERIC;
  private final DbQueryExecResult ERR404 = DbQueryExecResult.QUERY_ERROR_NOT_FOUND;

  @Autowired
  public SongDalImpl(MongoTemplate mongoTemplate) {
    this.db = mongoTemplate;
  }

  /**
   * Add new song to mongo db
   * 
   * @param songToAdd Song instance to be added
   */
  @Override
  public DbQueryStatus addSong(Song songToAdd) {
    // null check
    boolean valid = songToAdd.getSongName() != null && songToAdd.getSongAlbum() != null
        && songToAdd.getSongArtistFullName() != null;

    Song inserted = valid ? db.insert(songToAdd, "songs") : null;
    DbQueryStatus status = new DbQueryStatus("POST", inserted != null ? this.OK : this.ERR);
    if (inserted != null)
      status.setData(inserted.getJsonRepresentation());
    return status;
  }

  /**
   * find song by id
   * 
   * @param songId id of the song
   */
  @Override
  public DbQueryStatus findSongById(String songId) {
    try {
      ObjectId _id = new ObjectId(songId);
      Song found = db.findById(_id, Song.class);
      DbQueryStatus status = new DbQueryStatus("GET", found != null ? this.OK : this.ERR404);
      status.setData(found.getJsonRepresentation());
      return status;
    } catch (Exception e) {
      return new DbQueryStatus("GET", this.ERR);
    }
  }

  /**
   * find song title by id
   * 
   * @param songId id of the song
   */
  @Override
  public DbQueryStatus getSongTitleById(String songId) {
    try {
      ObjectId _id = new ObjectId(songId);
      Song found = db.findById(_id, Song.class);
      DbQueryStatus status = new DbQueryStatus("GET", found != null ? this.OK : this.ERR404);
      if (found != null)
        status.setData(found.getSongName());
      return status;
    } catch (Exception e) {
      return new DbQueryStatus("GET", this.ERR);
    }
  }

  /**
   * delete song by id
   * 
   * @param songId id of the song to be deleted
   */
  @Override
  public DbQueryStatus deleteSongById(String songId) {
    try {
      ObjectId _id = new ObjectId(songId);
      Song found = db.findById(_id, Song.class);
      DbQueryStatus status = new DbQueryStatus("DELETE", this.ERR404);
      if (found != null) {
        DeleteResult res = db.remove(found, "songs");
        if (res.wasAcknowledged()) // if delete was successful
          status.setdbQueryExecResult(this.OK);
      }
      return status;
    } catch (Exception e) {
      return new DbQueryStatus("DELETE", this.ERR);
    }
  }

  /**
   * update song fav count
   * 
   * @param songId          id of the song
   * @param shouldDecrement if true then count--; else count++
   */
  @Override
  public DbQueryStatus updateSongFavouritesCount(String songId, boolean shouldDecrement) {
    try {
      ObjectId _id = new ObjectId(songId);
      Song found = db.findById(_id, Song.class);
      if (found != null) {
        long count = found.getSongAmountFavourites() + (shouldDecrement ? -1 : 1);
        found.setSongAmountFavourites(count);
        found = db.save(found);
      }
      DbQueryStatus status = new DbQueryStatus("PUT", found == null ? this.ERR404 : this.OK);
      return status;
    } catch (Exception e) {
      return new DbQueryStatus("PUT", this.ERR);
    }
  }
}