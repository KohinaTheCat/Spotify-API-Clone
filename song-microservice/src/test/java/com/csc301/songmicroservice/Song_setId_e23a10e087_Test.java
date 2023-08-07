package com.csc301.songmicroservice;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class Song_setId_e23a10e087_Test {

    @InjectMocks
    private Song song;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        song = new Song();
    }

    @Test
    public void setId_withValidObjectId() {
        ObjectId objectId = new ObjectId();
        song.setId(objectId);

        assertEquals(objectId, song.getId(), "The object ID set in song object should match the one provided");
    }

    @Test
    public void setId_withNullObjectId() {
        song.setId(null);

        assertNull(song.getId(), "The object ID should be null as null was set");
    }
}
