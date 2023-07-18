package com.csc301.songmicroservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Song_getJsonRepresentation_484feade38_Test {

    private Song song;

    @BeforeEach
    public void setup() {
        song = new Song();
        song.setId(new ObjectId().toString());
        song.setSongName("Test Song");
        song.setSongArtistFullName("Test Artist");
        song.setSongAlbum("Test Album");
        song.setSongAmountFavourites(10);
    }

    @Test
    public void testGetJsonRepresentation() {
        Map<String, Object> jsonRepresentation = song.getJsonRepresentation();

        assertEquals(song.getId(), jsonRepresentation.get("id"));
        assertEquals(song.getSongName(), jsonRepresentation.get("songName"));
        assertEquals(song.getSongArtistFullName(), jsonRepresentation.get("songArtistFullName"));
        assertEquals(song.getSongAlbum(), jsonRepresentation.get("songAlbum"));
        assertEquals(String.valueOf(song.getSongAmountFavourites()), jsonRepresentation.get("songAmountFavourites").toString());
    }

    @Test
    public void testGetJsonRepresentationWithEmptySong() {
        song = new Song();
        song.setId("");
        song.setSongName("");
        song.setSongArtistFullName("");
        song.setSongAlbum("");
        song.setSongAmountFavourites(0);

        Map<String, Object> jsonRepresentation = song.getJsonRepresentation();

        assertEquals("", jsonRepresentation.get("id"));
        assertEquals("", jsonRepresentation.get("songName"));
        assertEquals("", jsonRepresentation.get("songArtistFullName"));
        assertEquals("", jsonRepresentation.get("songAlbum"));
        assertEquals("0", jsonRepresentation.get("songAmountFavourites").toString());
    }
}
