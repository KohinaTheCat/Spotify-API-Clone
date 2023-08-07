package com.csc301.songmicroservice;

import com.mongodb.client.result.DeleteResult;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@Repository
public class SongDalImpl_getSongTitleById_be3a0f0c15_Test {

    private SongDalImpl songDal;

    @Mock
    private MongoTemplate db;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        songDal = new SongDalImpl(db);
    }

    @Test
    public void testGetSongTitleById_Success() {
        Song song = new Song();
        song.setSongName("testSong");
        song.setArtistFullName("testArtist");
        song.setAlbumName("testAlbum");
        song.setSongUrl("testUrl");
        ObjectId _id = new ObjectId();
        song.setId(_id);
        when(db.findById(_id, Song.class)).thenReturn(song);

        DbQueryStatus result = songDal.getSongTitleById(_id.toHexString());
        assertEquals("GET", result.getOperation());
        assertEquals(songDal.OK, result.getStatus());
        assertEquals(song.getSongName(), result.getData());
    }

    @Test
    public void testGetSongTitleById_NotFound() {
        ObjectId _id = new ObjectId();
        when(db.findById(_id, Song.class)).thenReturn(null);

        DbQueryStatus result = songDal.getSongTitleById(_id.toHexString());
        assertEquals("GET", result.getOperation());
        assertEquals(songDal.ERR404, result.getStatus());
        assertEquals(null, result.getData());
    }

    @Test
    public void testGetSongTitleById_Exception() {
        DbQueryStatus result = songDal.getSongTitleById("invalidId");
        assertEquals("GET", result.getOperation());
        assertEquals(songDal.ERR, result.getStatus());
    }
}
