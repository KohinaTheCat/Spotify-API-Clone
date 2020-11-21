package com.csc301.songmicroservice;

import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection="songs")
public class Song {
	@Id
	@JsonIgnore
	public ObjectId _id;
	
	private String songName;
	private String songArtistFullName;
	private String songAlbum;
	private long songAmountFavourites;
	
	public static final String KEY_SONG_NAME = "songName";
	public static final String KEY_SONG_ARTIST_FULL_NAME = "songArtistFullName";
	public static final String KEY_SONG_ALBUM = "songAlbum";	


	public Song(String songName, String songArtistFullName, String songAlbum) {
		this.songName = songName;
		this.songArtistFullName = songArtistFullName;
		this.songAlbum = songAlbum;
		this.songAmountFavourites = 0;
	}

	public String getSongName() {
		return songName;
	}

	public void setSongName(String songName) {
		this.songName = songName;
	}

	public String getSongArtistFullName() {
		return songArtistFullName;
	}

	public void setSongArtistFullName(String songArtistFullName) {
		this.songArtistFullName = songArtistFullName;
	}

	public String getSongAlbum() {
		return songAlbum;
	}

	public void setSongAlbum(String songAlbum) {
		this.songAlbum = songAlbum;
	}

	public long getSongAmountFavourites() {
		return songAmountFavourites;
	}

	public void setSongAmountFavourites(long songAmountFavourites) {
		this.songAmountFavourites = songAmountFavourites;
	}

	// ObjectId needs to be converted to string
	public String getId() {
		return _id.toHexString();
	}

	public void setId(ObjectId _id) {
		this._id = _id;
	}
	
	@Override
	public String toString() {
		return this.getJsonRepresentation().toString();
	}
	
	@JsonIgnore
	public Map<String, String> getJsonRepresentation() {
		HashMap<String, String> jsonRepresentation = new HashMap<String, String>();
		jsonRepresentation.put("id", this.getId());
		jsonRepresentation.put("songName", this.songName);
		jsonRepresentation.put("songArtistFullName", this.songArtistFullName);
		jsonRepresentation.put("songAlbum", this.songAlbum);
		jsonRepresentation.put("songAmountFavourites", String.valueOf(this.songAmountFavourites));
		
		return jsonRepresentation;
	}
}