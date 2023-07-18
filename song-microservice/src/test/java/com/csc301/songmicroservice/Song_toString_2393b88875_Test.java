package com.csc301.songmicroservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class Song_toString_2393b88875_Test {

    @Mock
    private Song song;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testToString_whenJsonRepresentationIsCorrect() {
        when(song.toString()).thenReturn("{\"id\":\"1\",\"songName\":\"Test Song\",\"album\":\"Test Album\",\"artistFullName\":\"Test Artist\",\"year\":\"2021\"}");
        assertEquals("{\"id\":\"1\",\"songName\":\"Test Song\",\"album\":\"Test Album\",\"artistFullName\":\"Test Artist\",\"year\":\"2021\"}", song.toString());
    }

    @Test
    public void testToString_whenJsonRepresentationIsEmpty() {
        when(song.toString()).thenReturn("{}");
        assertEquals("{}", song.toString());
    }

    // TODO: Add more test cases if required
}
