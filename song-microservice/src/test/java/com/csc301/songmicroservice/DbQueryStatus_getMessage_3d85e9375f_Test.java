package com.csc301.songmicroservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class DbQueryStatus_getMessage_3d85e9375f_Test {

    @Test
    public void testGetMessage_Success() {
        // Arrange
        DbQueryStatus dbQueryStatus = Mockito.mock(DbQueryStatus.class);
        Mockito.when(dbQueryStatus.getMessage()).thenReturn("Success");

        // Act
        String result = dbQueryStatus.getMessage();

        // Assert
        Assertions.assertEquals("Success", result, "Expected and actual messages do not match.");
    }

    @Test
    public void testGetMessage_Null() {
        // Arrange
        DbQueryStatus dbQueryStatus = Mockito.mock(DbQueryStatus.class);
        Mockito.when(dbQueryStatus.getMessage()).thenReturn(null);

        // Act
        String result = dbQueryStatus.getMessage();

        // Assert
        Assertions.assertNull(result, "Expected null but got a message.");
    }
}
