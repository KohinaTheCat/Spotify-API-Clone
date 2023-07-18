package com.csc301.songmicroservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Utils_setResponseStatus_d74fe01b5e_Test {

    private Map<String, Object> response;

    @BeforeEach
    public void setup() {
        response = new HashMap<>();
    }

    @Test
    public void testSetResponseStatus_QueryOkWithData() {
        Object data = new Object();
        Map<String, Object> result = Utils.setResponseStatus(response, DbQueryExecResult.QUERY_OK, data);

        assertEquals(HttpStatus.OK, result.get("status"));
        assertEquals(data, result.get("data"));
    }

    @Test
    public void testSetResponseStatus_QueryOkWithoutData() {
        Map<String, Object> result = Utils.setResponseStatus(response, DbQueryExecResult.QUERY_OK, null);

        assertEquals(HttpStatus.OK, result.get("status"));
        assertEquals(null, result.get("data"));
    }

    @Test
    public void testSetResponseStatus_QueryErrorNotFound() {
        Map<String, Object> result = Utils.setResponseStatus(response, DbQueryExecResult.QUERY_ERROR_NOT_FOUND, null);

        assertEquals(HttpStatus.NOT_FOUND, result.get("status"));
    }

    @Test
    public void testSetResponseStatus_QueryErrorGeneric() {
        Map<String, Object> result = Utils.setResponseStatus(response, DbQueryExecResult.QUERY_ERROR_GENERIC, null);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.get("status"));
    }
}
