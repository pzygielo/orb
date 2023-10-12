package com.sun.corba.ee.spi.logging;

import org.junit.Test;

public class UtilSystemExceptionTest {
    @Test
    public void testSelf() {
        UtilSystemException wrapper = UtilSystemException.self;
        RuntimeException e = wrapper.classNotFound("abcd");
        throw e;
    }
}

