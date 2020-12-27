# Spotify API Clone

Developed in a team of 2, we made a REST API for a Spotify-like music application that utilizes the Java Spring Boot framework to create the API and microservices supported by MongoDB and Neo4j. 

This API has two microservices: `Profile` and `Song`.

## Technologies Used

- Java
- Spring Boot
- [MongoDB](https://www.mongodb.com/)
- [Neo4j](https://neo4j.com/)

The MongoDB database is used for storing song information, while the Neo4j graph database is used for storing profile information, their relationship between other users and their liked songs. 

## Usage

### Song Microservice

this service runs on port `3001`

> GET  `/getSongTitleById/{songId}`

- retrieves the song title given the `songId`
- **URL Parameters**
  - `songId` is the _id of a specific song
- **Expected Response**
  - `OK`, if the title was retrieved successfully
  - `<string>`, any other status if the song was not retrieved successfully

> DELETE  `/deleteSongById/{songId}`

- deletes the song from MongoDB and all Profiles that have added it to their favorites list
- **URL Parameters**
  - `songId` is the _id of a specific song
- **Expected Response**
  - `OK`, if the song was deleted successfully
  - `<string>`, any other status if the song was not deleted successfully

> POST  `/addSong`

- adds a song to the database
- **Query Parameters**
  - `songName` : the name of the song
  - `songArtistFullName` : the name of the song artist
  - `songAlbum` : the album of the song
- **Expected Response**
  - `status`
    - `OK`, if the song was deleted successfully
    - `<string>`, any other status if the song was not deleted successfully
  - `data`
    - song object

> PUT `/updateSongFavouritesCount/{songId}?shouldDecrement=`

- updates the song's favorites count
- **URL Parameters**
  -  `songId` is the _id of a specific song
- **Query Parameters**
  - `shouldDecrement` : string values of `true` or `false`
    - states where the song count should be incremented or decremented by 1

- **Expected Response**
  - `status`
    - `OK`, if the song was updated successfully
    - `<string>`, any other status if the song was not updated successfully

### Profile Microservice

this service runs on port `3002`

> POST `/profile`

- Adds a profile to the database and creates a liked songs playlist

- **Query Parameters**
  - `userName` : name of the profile
  - `fullName` : full name of the User
  - `password` : password of the profile

- **Expected Response**
  - `status`
    - `OK`, if the profile was created successfully
    - `<string>`, any other status if the profile was not created successfully

> PUT  `/followFriend/{username}/{friendUserName}`

- allows a profile to follow another profile
- **URL Parameters**
  - `userName`  : the username of the profile that will follow `friendUserName`
  - `friendUserName` : the username of the profile that will be followed

- **Expected Response**
  - `status`
    - `OK`, if the the user was able to follow successfully
    - `<string>`, any other status if the the user was not able to follow successfully

> PUT  `/unfollowFriend/{username}/{friendUserName}`

- allows a profile to unfollow another profile
- **URL Parameters**
  - `userName`  : the username of the profile that will unfollow `friendUserName`
  - `friendUserName` : the username of the profile that will be unfollowed

- **Expected Response**
  - `status`
    - `OK`, if the the user was able to unfollow successfully
    - `<string>`, any other status if the the user was not able to unfollow successfully

> PUT  `/likeSong/{userName}/{songId}`

- allows a profile to like a song and add it to their liked songs playlist
- **URL Parameters**
  - `userName`  : the username of the profile that will like the song
  -  `songId` is the _id of a specific song

- **Expected Response**
  - `status`
    - `OK`, if the the user was able to like the song successfully
    - `<string>`, any other status if the the user was not able to like the song successfully

> PUT  `/unlikeSong/{userName}/{songId}`

- allows a profile to unlike a song and add it to their liked songs playlist
- **URL Parameters**
  - `userName`  : the username of the profile that will unlike the song
  -  `songId` is the _id of a specific song

- **Expected Response**
  - `status`
    - `OK`, if the the user was able to unlike the song successfully
    - `<string>`, any other status if the the user was not able to unlike the song successfully

> GET  `getAllFriendFavouriteSongTitles/{userName}`

- returns all the song names of the songs that the user's friends have liked
- **URL Parameters**
  - `userName` : the username of the profile whose friends songs we will retrieve
  - `songId` is the _id of a specific song

- **Expected Response**
  - `status`
    - `OK`, if the names of songs were retrieved successfully
    - `<string>`, any other status if the names of songs were not retrieved successfully
  - `data`
    - list of songs
