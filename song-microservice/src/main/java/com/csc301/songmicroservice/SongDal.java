package com.csc301.songmicroservice;

public interface SongDal {
	DbQueryStatus addSong(Song songToAdd);
	DbQueryStatus findSongById(String songId);
	DbQueryStatus getSongTitleById(String songId);
	DbQueryStatus deleteSongById(String songId);	
	DbQueryStatus updateSongFavouritesCount(String songId, boolean shouldDecrement);
}
