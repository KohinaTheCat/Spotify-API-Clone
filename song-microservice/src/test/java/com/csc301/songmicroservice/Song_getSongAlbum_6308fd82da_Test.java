package com.csc301.songmicroservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class Song_getSongAlbum_6308fd82da_Test {

    @Mock
    private Song song;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetSongAlbum_WithValidAlbum() {
        String expectedAlbum = "Test Album";
        Mockito.when(song.getSongAlbum()).thenReturn(expectedAlbum);
        String actualAlbum = song.getSongAlbum();
        Assertions.assertEquals(expectedAlbum, actualAlbum, "The expected album does not match the actual album.");
    }

    @Test
    public void testGetSongAlbum_WithEmptyAlbum() {
        String expectedAlbum = "";
        Mockito.when(song.getSongAlbum()).thenReturn(expectedAlbum);
        String actualAlbum = song.getSongAlbum();
        Assertions.assertEquals(expectedAlbum, actualAlbum, "The expected album does not match the actual album.");
    }

    @Test
    public void testGetSongAlbum_WithNullAlbum() {
        Mockito.when(song.getSongAlbum()).thenReturn(null);
        String actualAlbum = song.getSongAlbum();
        Assertions.assertNull(actualAlbum, "The album should be null.");
    }
}
