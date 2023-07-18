package com.csc301.songmicroservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SongController_addSong_86b90c586b_Test {

    @InjectMocks
    SongController songController;

    @Mock
    SongDal songDal;

    @Mock
    OkHttpClient client;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddSongSuccess() {
        Map<String, String> params = new HashMap<>();
        params.put(Song.KEY_SONG_NAME, "Test Song");
        params.put(Song.KEY_SONG_ARTIST_FULL_NAME, "Test Artist");
        params.put(Song.KEY_SONG_ALBUM, "Test Album");

        HttpServletRequest request = mock(HttpServletRequest.class);

        Song song = new Song();
        song.setSongName(params.get(Song.KEY_SONG_NAME));
        song.setArtistFullName(params.get(Song.KEY_SONG_ARTIST_FULL_NAME));
        song.setAlbum(params.get(Song.KEY_SONG_ALBUM));

        DbQueryStatus dbQueryStatus = new DbQueryStatus("OK", DbQueryExecResult.QUERY_OK);
        when(songDal.addSong(song)).thenReturn(dbQueryStatus);

        Map<String, Object> response = songController.addSong(params, request);
        assertEquals("OK", response.get("message"));
    }

    @Test
    public void testAddSongFailure() {
        Map<String, String> params = new HashMap<>();
        params.put(Song.KEY_SONG_NAME, "Test Song");
        params.put(Song.KEY_SONG_ARTIST_FULL_NAME, "Test Artist");
        params.put(Song.KEY_SONG_ALBUM, "Test Album");

        HttpServletRequest request = mock(HttpServletRequest.class);

        Song song = new Song();
        song.setSongName(params.get(Song.KEY_SONG_NAME));
        song.setArtistFullName(params.get(Song.KEY_SONG_ARTIST_FULL_NAME));
        song.setAlbum(params.get(Song.KEY_SONG_ALBUM));

        DbQueryStatus dbQueryStatus = new DbQueryStatus("NOT FOUND", DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
        when(songDal.addSong(song)).thenReturn(dbQueryStatus);

        Map<String, Object> response = songController.addSong(params, request);
        assertEquals("NOT FOUND", response.get("message"));
    }
}
