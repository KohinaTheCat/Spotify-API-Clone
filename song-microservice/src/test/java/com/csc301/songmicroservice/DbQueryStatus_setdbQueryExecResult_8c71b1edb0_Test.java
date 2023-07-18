package com.csc301.songmicroservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DbQueryStatus_setdbQueryExecResult_8c71b1edb0_Test {

    private DbQueryStatus dbQueryStatus;
    private DbQueryExecResult dbQueryExecResult;

    @BeforeEach
    public void setUp() {
        dbQueryStatus = new DbQueryStatus();
        dbQueryExecResult = Mockito.mock(DbQueryExecResult.class);
    }

    @Test
    public void testSetDbQueryExecResultSuccess() {
        dbQueryStatus.setDbQueryExecResult(dbQueryExecResult);
        assertEquals(dbQueryExecResult, dbQueryStatus.getDbQueryExecResult());
    }

    @Test
    public void testSetDbQueryExecResultNull() {
        dbQueryStatus.setDbQueryExecResult(null);
        assertEquals(null, dbQueryStatus.getDbQueryExecResult());
    }
}
