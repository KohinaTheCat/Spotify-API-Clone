package com.csc301.songmicroservice;

import com.mongodb.client.result.DeleteResult;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SongDalImpl_deleteSongById_c793e3d702_Test {

    @Autowired
    private SongDalImpl songDalImpl;

    @MockBean
    private MongoTemplate db;

    private Song song;
    private String songId;

    @BeforeEach
    public void setup() {
        songId = new ObjectId().toString();
        song = new Song();
        song.setId(songId);
    }

    @Test
    public void testDeleteSongById_Success() {
        when(db.findById(new ObjectId(songId), Song.class)).thenReturn(song);
        DeleteResult deleteResult = Mockito.mock(DeleteResult.class);
        when(deleteResult.wasAcknowledged()).thenReturn(true);
        when(db.remove(song)).thenReturn(deleteResult);

        DbQueryStatus result = songDalImpl.deleteSongById(songId);

        assertEquals("DELETE", result.getDbQueryMessage());
        assertEquals(DbQueryExecResult.QUERY_OK, result.getDbQueryExecResult());
    }

    @Test
    public void testDeleteSongById_Failure() {
        when(db.findById(new ObjectId(songId), Song.class)).thenReturn(null);

        DbQueryStatus result = songDalImpl.deleteSongById(songId);

        assertEquals("DELETE", result.getDbQueryMessage());
        assertEquals(DbQueryExecResult.QUERY_ERROR_GENERIC, result.getDbQueryExecResult());
    }

    @Test
    public void testDeleteSongById_Exception() {
        when(db.findById(new ObjectId(songId), Song.class)).thenThrow(new RuntimeException());

        DbQueryStatus result = songDalImpl.deleteSongById(songId);

        assertEquals("DELETE", result.getDbQueryMessage());
        assertEquals(DbQueryExecResult.QUERY_ERROR_GENERIC, result.getDbQueryExecResult());
    }
}
