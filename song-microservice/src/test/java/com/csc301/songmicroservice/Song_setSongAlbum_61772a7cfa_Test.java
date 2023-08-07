package com.csc301.songmicroservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class Song_setSongAlbum_61772a7cfa_Test {

    @InjectMocks
    private Song song;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        song = new Song();
    }

    @Test
    public void testSetSongAlbum_validAlbum() {
        String validAlbum = "Valid Album";
        song.setSongAlbum(validAlbum);
        Assertions.assertEquals(validAlbum, song.getSongAlbum(), "The album name should be set correctly");
    }

    @Test
    public void testSetSongAlbum_emptyAlbum() {
        String emptyAlbum = "";
        song.setSongAlbum(emptyAlbum);
        Assertions.assertEquals(emptyAlbum, song.getSongAlbum(), "The album name should be set even if it is empty");
    }

    @Test
    public void testSetSongAlbum_nullAlbum() {
        String nullAlbum = null;
        song.setSongAlbum(nullAlbum);
        Assertions.assertNull(song.getSongAlbum(), "The album name should be null if null is passed in");
    }
}
