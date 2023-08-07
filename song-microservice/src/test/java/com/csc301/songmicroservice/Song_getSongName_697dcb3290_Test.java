package com.csc301.songmicroservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Song_getSongName_697dcb3290_Test {

    @InjectMocks
    private Song song;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        song = new Song();
        song.setSongName("Test Song");
    }

    @Test
    public void testGetSongName() {
        String songName = song.getSongName();
        assertEquals("Test Song", songName, "Song name should be 'Test Song'");
    }

    @Test
    public void testGetSongNameWhenNoSongNameSet() {
        song.setSongName(null);
        String songName = song.getSongName();
        assertEquals(null, songName, "Song name should be null");
    }
}
