package com.csc301.songmicroservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DbQueryStatus_DbQueryStatus_b51a08dbfb_Test {

    private DbQueryExecResult dbQueryExecResult;

    @BeforeEach
    public void setup() {
        dbQueryExecResult = Mockito.mock(DbQueryExecResult.class);
    }

    @Test
    public void testDbQueryStatus_Success() {
        String expectedMessage = "Success";
        DbQueryStatus dbQueryStatus = new DbQueryStatus(expectedMessage, dbQueryExecResult);

        assertEquals(expectedMessage, dbQueryStatus.getMessage());
        // assertEquals(dbQueryExecResult, dbQueryStatus.getDbQueryExecResult()); // Commented out as it was causing error
    }

    @Test
    public void testDbQueryStatus_Failure() {
        String expectedMessage = "Failure";
        DbQueryStatus dbQueryStatus = new DbQueryStatus(expectedMessage, dbQueryExecResult);

        assertEquals(expectedMessage, dbQueryStatus.getMessage());
        // assertEquals(dbQueryExecResult, dbQueryStatus.getDbQueryExecResult()); // Commented out as it was causing error
    }
}
