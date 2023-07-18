package com.csc301.songmicroservice;

import com.mongodb.client.result.DeleteResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

public class SongDalImpl_SongDalImpl_e1fe120c3d_Test {

    private MongoTemplate mongoTemplate;

    @Test
    public void testSongDalImpl_WithValidMongoTemplate() {
        MongoTemplate validMongoTemplate = Mockito.mock(MongoTemplate.class);
        SongDalImpl songDal = new SongDalImpl();
        songDal.setMongoTemplate(validMongoTemplate);
        Assertions.assertNotNull(songDal);
    }

    @Test
    public void testSongDalImpl_WithNullMongoTemplate() {
        MongoTemplate nullMongoTemplate = null;
        Assertions.assertThrows(NullPointerException.class, () -> {
            SongDalImpl songDal = new SongDalImpl();
            songDal.setMongoTemplate(nullMongoTemplate);
        });
    }
}
