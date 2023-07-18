package com.csc301.songmicroservice;

import com.mongodb.client.result.DeleteResult;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SongDalImpl_updateSongFavouritesCount_9e63eaa28b_Test {

    @Mock
    MongoTemplate db;

    @InjectMocks
    SongDalImpl songDal;

    @BeforeEach
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUpdateSongFavouritesCount_Increment() {
        String songId = "5e63eaa28b";
        Song song = new Song();
        song.setSongAmountFavourites(5);

        when(db.findById(any(ObjectId.class), eq(Song.class))).thenReturn(song);
        when(db.save(any(Song.class))).thenReturn(song);

        DbQueryStatus result = songDal.updateSongFavouritesCount(songId, false);
        assertEquals("PUT", result.getOperation());
        assertEquals("OK", result.getStatus());
        assertEquals(6, song.getSongAmountFavourites());
    }

    @Test
    public void testUpdateSongFavouritesCount_Decrement() {
        String songId = "5e63eaa28b";
        Song song = new Song();
        song.setSongAmountFavourites(5);

        when(db.findById(any(ObjectId.class), eq(Song.class))).thenReturn(song);
        when(db.save(any(Song.class))).thenReturn(song);

        DbQueryStatus result = songDal.updateSongFavouritesCount(songId, true);
        assertEquals("PUT", result.getOperation());
        assertEquals("OK", result.getStatus());
        assertEquals(4, song.getSongAmountFavourites());
    }

    @Test
    public void testUpdateSongFavouritesCount_SongNotFound() {
        String songId = "5e63eaa28b";

        when(db.findById(any(ObjectId.class), eq(Song.class))).thenReturn(null);

        DbQueryStatus result = songDal.updateSongFavouritesCount(songId, true);
        assertEquals("PUT", result.getOperation());
        assertEquals("ERR404", result.getStatus());
    }

    @Test
    public void testUpdateSongFavouritesCount_Exception() {
        String songId = "5e63eaa28b";

        when(db.findById(any(ObjectId.class), eq(Song.class))).thenThrow(new RuntimeException());

        DbQueryStatus result = songDal.updateSongFavouritesCount(songId, true);
        assertEquals("PUT", result.getOperation());
        assertEquals("ERR", result.getStatus());
    }
}
