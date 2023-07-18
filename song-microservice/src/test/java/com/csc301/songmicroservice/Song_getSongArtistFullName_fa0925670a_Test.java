package com.csc301.songmicroservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class Song_getSongArtistFullName_fa0925670a_Test {

    private Song song;
    private String songArtistFullName;

    @BeforeEach
    public void setup() {
        song = Mockito.mock(Song.class);
        songArtistFullName = "John Doe";
        when(song.getSongArtistFullName()).thenReturn(songArtistFullName);
    }

    @Test
    public void testGetSongArtistFullNameSuccess() {
        String result = song.getSongArtistFullName();
        assertEquals(songArtistFullName, result, "Expected and actual song artist full name do not match");
    }

    @Test
    public void testGetSongArtistFullNameFailure() {
        String wrongArtistName = "Jane Doe";
        String result = song.getSongArtistFullName();
        assertEquals(songArtistFullName, result, "Expected and actual song artist full name do not match");
    }
}
