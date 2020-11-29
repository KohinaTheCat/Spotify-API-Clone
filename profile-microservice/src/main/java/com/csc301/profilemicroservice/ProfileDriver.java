package com.csc301.profilemicroservice;

public interface ProfileDriver {
	DbQueryStatus createUserProfile(String userName, String fullName, String password);
	DbQueryStatus followFriend(String userName, String frndUserName);
	DbQueryStatus unfollowFriend(String userName, String frndUserName );
	DbQueryStatus getAllSongFriendsLike(String userName);
} 