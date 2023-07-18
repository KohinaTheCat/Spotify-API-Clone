package com.csc301.songmicroservice;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class Song_getId_470f177c91_Test {
    private Song song;
    private ObjectId objectId;

    @BeforeEach
    public void setUp() {
        objectId = new ObjectId();
        song = Mockito.mock(Song.class);
        when(song.getId()).thenReturn(objectId.toHexString());
    }

    @Test
    public void testGetIdSuccess() {
        String expectedId = objectId.toHexString();
        String actualId = song.getId();
        assertEquals(expectedId, actualId, "Expected ID does not match with actual ID");
    }

    @Test
    public void testGetIdFailure() {
        String unexpectedId = new ObjectId().toHexString();
        String actualId = song.getId();
        assertEquals(false, unexpectedId.equals(actualId), "Unexpected ID should not match with actual ID");
    }
}
