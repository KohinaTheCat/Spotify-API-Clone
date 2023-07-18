package com.csc301.songmicroservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SongController_SongController_22246af099_Test {

    @Mock
    private SongDal songDal;

    @InjectMocks
    private SongController songController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSongControllerConstructor_positive() {
        Assertions.assertNotNull(songController);
    }

    @Test
    public void testSongControllerConstructor_null() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            SongController nullSongController = null;
            Assertions.assertNotNull(nullSongController);
        });
    }
}
