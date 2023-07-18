package com.csc301.songmicroservice;

import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Utils_getUrl_782cbb2644_Test {

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetUrlWithoutQueryString() {
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/test"));
        when(request.getQueryString()).thenReturn(null);

        String result = Utils.getUrl(request);
        assertEquals("http://localhost:8080/test", result);
    }

    @Test
    public void testGetUrlWithQueryString() {
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/test"));
        when(request.getQueryString()).thenReturn("param1=value1&param2=value2");

        String result = Utils.getUrl(request);
        assertEquals("http://localhost:8080/test?param1=value1&param2=value2", result);
    }
}
