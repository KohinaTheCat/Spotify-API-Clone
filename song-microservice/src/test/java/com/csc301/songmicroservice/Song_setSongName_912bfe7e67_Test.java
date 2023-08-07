package com.csc301.songmicroservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Song_setSongName_912bfe7e67_Test {

    private Song song;

    @BeforeEach
    public void setUp() {
        song = new Song();
    }

    @Test
    public void testSetSongName() {
        String expectedSongName = "Test Song";
        song.setSongName(expectedSongName);
        assertEquals(expectedSongName, song.getSongName());
    }

    @Test
    public void testSetSongNameWithEmptyString() {
        String expectedSongName = "";
        song.setSongName(expectedSongName);
        assertEquals(expectedSongName, song.getSongName());
    }

    @Test
    public void testSetSongNameWithNull() {
        String expectedSongName = null;
        song.setSongName(expectedSongName);
        assertEquals(expectedSongName, song.getSongName());
    }
}
