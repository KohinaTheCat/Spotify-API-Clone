package com.csc301.songmicroservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class Song_setSongAmountFavourites_76d9a28394_Test {

    @Mock
    private Song song;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.doNothing().when(song).setSongAmountFavourites(Mockito.anyLong());
    }

    @Test
    public void testSetSongAmountFavourites_PositiveValue() {
        long songAmountFavourites = 10L;
        Mockito.when(song.getSongAmountFavourites()).thenReturn(songAmountFavourites);
        song.setSongAmountFavourites(songAmountFavourites);
        assertEquals(songAmountFavourites, song.getSongAmountFavourites(), "The value set should match the expected value");
    }

    @Test
    public void testSetSongAmountFavourites_ZeroValue() {
        long songAmountFavourites = 0L;
        Mockito.when(song.getSongAmountFavourites()).thenReturn(songAmountFavourites);
        song.setSongAmountFavourites(songAmountFavourites);
        assertEquals(songAmountFavourites, song.getSongAmountFavourites(), "The value set should match the expected value");
    }

    @Test
    public void testSetSongAmountFavourites_NegativeValue() {
        long songAmountFavourites = -5L;
        Mockito.when(song.getSongAmountFavourites()).thenReturn(songAmountFavourites);
        song.setSongAmountFavourites(songAmountFavourites);
        assertEquals(songAmountFavourites, song.getSongAmountFavourites(), "The value set should match the expected value");
    }
}
