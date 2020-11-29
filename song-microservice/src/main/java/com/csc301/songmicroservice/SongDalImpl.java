package com.csc301.songmicroservice;

import java.util.Map;

import com.mongodb.client.result.DeleteResult;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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

	@Override
	public DbQueryStatus addSong(Song songToAdd) {
		boolean valid = songToAdd.getSongName() != null && songToAdd.getSongAlbum() != null
				&& songToAdd.getSongArtistFullName() != null;
		Song inserted = valid ? db.insert(songToAdd, "songs") : null;
		DbQueryStatus status = new DbQueryStatus("POST", inserted != null ? this.OK : this.ERR);
		if (inserted != null)
			status.setData(inserted.getJsonRepresentation());
		return status;
	}

	@Override
	public DbQueryStatus findSongById(String songId) {
		try {
			ObjectId _id = new ObjectId(songId);
			Song found = db.findById(_id, Song.class);
			DbQueryStatus status = new DbQueryStatus("GET", found != null ? this.OK : this.ERR404);
			status.setData(found.getJsonRepresentation());
			return status;
		} catch (IllegalArgumentException e) {
			return new DbQueryStatus("GET", this.ERR);
		}
	}

	@Override
	public DbQueryStatus getSongTitleById(String songId) {
		try {
			ObjectId _id = new ObjectId(songId);
			Song found = db.findById(_id, Song.class);
			DbQueryStatus status = new DbQueryStatus("GET", found != null ? this.OK : this.ERR404);
			if (found != null)
				status.setData(found.getSongName());
			return status;
		} catch (IllegalArgumentException e) {
			return new DbQueryStatus("GET", this.ERR);
		}
	}

	@Override
	public DbQueryStatus deleteSongById(String songId) {
		try {
			ObjectId _id = new ObjectId(songId);
			Song found = db.findById(_id, Song.class);
			DbQueryStatus status = new DbQueryStatus("DELETE", this.ERR404);
			if (found != null) {
				DeleteResult res = db.remove(found, "songs");
				if (res.wasAcknowledged())
					status.setdbQueryExecResult(this.OK);
			}
			return status;
		} catch (IllegalArgumentException e) {
			return new DbQueryStatus("GET", this.ERR);
		}
	}

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
		} catch (IllegalArgumentException e) {
			return new DbQueryStatus("GET", this.ERR);
		}
	}
}