package com.csc301.songmicroservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Song_getSongAmountFavourites_596a4286ec_Test {

    private Song song;

    @BeforeEach
    public void setup() {
        song = new Song();
    }

    @Test
    public void testGetSongAmountFavourites_WhenSongHasNoFavourites() {
        long expectedFavourites = 0;
        song.setSongAmountFavourites(expectedFavourites);
        long actualFavourites = song.getSongAmountFavourites();
        assertEquals(expectedFavourites, actualFavourites);
    }

    @Test
    public void testGetSongAmountFavourites_WhenSongHasFavourites() {
        long expectedFavourites = 5;
        song.setSongAmountFavourites(expectedFavourites);
        long actualFavourites = song.getSongAmountFavourites();
        assertEquals(expectedFavourites, actualFavourites);
    }
}
