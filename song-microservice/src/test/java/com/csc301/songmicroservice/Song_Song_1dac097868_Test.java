package com.csc301.songmicroservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Song_Song_1dac097868_Test {

    private Song song;

    @BeforeEach
    public void setup() {
        song = new Song();
        song.setSongName("Test Song");
        song.setSongArtistFullName("Test Artist");
        song.setSongAlbum("Test Album");
    }

    @Test
    public void testSongName() {
        assertEquals("Test Song", song.getSongName(), "Song name should be 'Test Song'");
    }

    @Test
    public void testSongArtistFullName() {
        assertEquals("Test Artist", song.getSongArtistFullName(), "Song artist full name should be 'Test Artist'");
    }

    @Test
    public void testSongAlbum() {
        assertEquals("Test Album", song.getSongAlbum(), "Song album should be 'Test Album'");
    }

    @Test
    public void testSongAmountFavourites() {
        assertEquals(0, song.getSongAmountFavourites(), "Song amount favourites should be 0 at the beginning");
    }

    @Test
    public void testSongNameWithNull() {
        Song songWithNullName = new Song();
        songWithNullName.setSongName(null);
        songWithNullName.setSongArtistFullName("Test Artist");
        songWithNullName.setSongAlbum("Test Album");
        assertEquals(null, songWithNullName.getSongName(), "Song name should be null");
    }

    @Test
    public void testSongArtistFullNameWithNull() {
        Song songWithNullArtist = new Song();
        songWithNullArtist.setSongName("Test Song");
        songWithNullArtist.setSongArtistFullName(null);
        songWithNullArtist.setSongAlbum("Test Album");
        assertEquals(null, songWithNullArtist.getSongArtistFullName(), "Song artist full name should be null");
    }

    @Test
    public void testSongAlbumWithNull() {
        Song songWithNullAlbum = new Song();
        songWithNullAlbum.setSongName("Test Song");
        songWithNullAlbum.setSongArtistFullName("Test Artist");
        songWithNullAlbum.setSongAlbum(null);
        assertEquals(null, songWithNullAlbum.getSongAlbum(), "Song album should be null");
    }
}
