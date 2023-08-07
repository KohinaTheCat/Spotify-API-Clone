package com.csc301.songmicroservice;

import com.mongodb.client.result.DeleteResult;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SongDalImpl_addSong_9dbd652a9d_Test {

    @Autowired
    private SongDal songDal;

    @MockBean
    private MongoTemplate db;

    @Test
    public void testAddSongSuccess() {
        Song song = new Song();
        song.setSongName("SongName");
        song.setSongArtistFullName("SongArtist");
        song.setAlbumName("SongAlbum");
        when(db.insert(song, "songs")).thenReturn(song);

        DbQueryStatus status = songDal.addSong(song);
        assertEquals("POST", status.getOperation());
        assertEquals("OK", status.getStatus());
        assertEquals(song.getJsonRepresentation(), status.getData());
    }

    @Test
    public void testAddSongFailure() {
        Song song = new Song();
        song.setSongName(null);
        song.setSongArtistFullName("SongArtist");
        song.setAlbumName("SongAlbum");
        when(db.insert(song, "songs")).thenReturn(null);

        DbQueryStatus status = songDal.addSong(song);
        assertEquals("POST", status.getOperation());
        assertEquals("ERR", status.getStatus());
        assertEquals(null, status.getData());
    }
}
