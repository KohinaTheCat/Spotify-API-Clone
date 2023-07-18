package com.csc301.songmicroservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class DbQueryStatus_setData_12e936db44_Test {

    @Test
    public void testSetData_ValidObject() {
        DbQueryStatus dbQueryStatus = new DbQueryStatus();
        Object obj = new Object();
        dbQueryStatus.setData(obj);

        Assertions.assertEquals(obj, dbQueryStatus.getData(), "The data set in the object does not match the expected data");
    }

    @Test
    public void testSetData_NullObject() {
        DbQueryStatus dbQueryStatus = new DbQueryStatus();
        dbQueryStatus.setData(null);

        Assertions.assertNull(dbQueryStatus.getData(), "The data set in the object is not null as expected");
    }
}
