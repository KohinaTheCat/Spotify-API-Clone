package com.csc301.songmicroservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class Song_setSongArtistFullName_c71a202363_Test {

    @InjectMocks
    private Song song;

    @BeforeEach
    public void setUp() {
        song = new Song();
    }

    @Test
    public void testSetSongArtistFullName_ValidName() {
        String validName = "John Doe";
        song.setSongArtistFullName(validName);
        assertEquals(validName, song.getSongArtistFullName());
    }

    @Test
    public void testSetSongArtistFullName_EmptyName() {
        String emptyName = "";
        song.setSongArtistFullName(emptyName);
        assertEquals(emptyName, song.getSongArtistFullName());
    }
}
