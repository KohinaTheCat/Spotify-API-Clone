package com.csc301.profilemicroservice;

public interface PlaylistDriver {
	DbQueryStatus likeSong(String userName, String songId);
	DbQueryStatus unlikeSong(String userName, String songId);
	DbQueryStatus deleteSongFromDb(String songId);
}