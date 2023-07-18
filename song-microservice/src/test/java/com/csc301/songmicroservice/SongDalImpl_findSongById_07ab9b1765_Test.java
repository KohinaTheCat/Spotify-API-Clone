package com.csc301.songmicroservice;

import com.mongodb.client.result.DeleteResult;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SongDalImpl_findSongById_07ab9b1765_Test {

    @Mock
    private MongoTemplate db;

    @InjectMocks
    private SongDalImpl songDal;

    private Song song;
    private String songId;

    @BeforeEach
    void setUp() {
        songId = new ObjectId().toString();
        song = new Song();
        song.setSongName("Test Song");
    }

    @Test
    public void testFindSongById_Success() {
        when(db.findById(songId, Song.class)).thenReturn(song);

        DbQueryStatus result = songDal.findSongById(songId);

        assertEquals("GET", result.getOperation());
        assertEquals(songDal.OK, result.getStatus());
        assertEquals(song.getJsonRepresentation(), result.getData());
    }

    @Test
    public void testFindSongById_SongNotFound() {
        when(db.findById(songId, Song.class)).thenReturn(null);

        DbQueryStatus result = songDal.findSongById(songId);

        assertEquals("GET", result.getOperation());
        assertEquals(songDal.ERR404, result.getStatus());
        assertEquals(null, result.getData());
    }

    @Test
    public void testFindSongById_Exception() {
        when(db.findById(songId, Song.class)).thenThrow(new RuntimeException());

        DbQueryStatus result = songDal.findSongById(songId);

        assertEquals("GET", result.getOperation());
        assertEquals(songDal.ERR, result.getStatus());
    }
}
