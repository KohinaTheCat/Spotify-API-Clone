package com.csc301.songmicroservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DbQueryStatus_getdbQueryExecResult_f073ae8dd0_Test {

    @InjectMocks
    private DbQueryStatus dbQueryStatus;

    @Mock
    private DbQueryExecResult dbQueryExecResult;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        dbQueryStatus.setDbQueryExecResult(dbQueryExecResult);
    }

    @Test
    public void testGetDbQueryExecResult_Success() {
        DbQueryExecResult result = dbQueryStatus.getdbQueryExecResult();
        Assertions.assertEquals(dbQueryExecResult, result);
    }

    @Test
    public void testGetDbQueryExecResult_Null() {
        dbQueryStatus.setDbQueryExecResult(null);
        DbQueryExecResult result = dbQueryStatus.getdbQueryExecResult();
        Assertions.assertNull(result);
    }
}
