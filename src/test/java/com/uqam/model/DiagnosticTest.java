package com.uqam.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DiagnosticTest {

    @Test
    void testValidDesignation() throws AppException {
        Diagnostic diagnostic = new Diagnostic("Covid19");
        Assertions.assertTrue(diagnostic.validDesignation(diagnostic.getDesignation()));
    }

    @Test
    void testValidDesignation2() {
        Diagnostic diagnostic = new Diagnostic("5");
        Assertions.assertThrows(AppException.class, () -> {
            diagnostic.validDesignation(diagnostic.getDesignation());
        });
    }

    @Test
    void testClone() throws CloneNotSupportedException {
        Diagnostic diagnostic   = new Diagnostic("diabete 2");
        Diagnostic diagnostic2 = diagnostic.clone();
        Assertions.assertEquals(diagnostic.getDesignation(),diagnostic2.getDesignation());
    }

    @Test
    void testToString(){
        Diagnostic diagnostic   = new Diagnostic("diabete 2");
        Assertions.assertEquals("Diagnostic{designation='diabete 2'}",diagnostic.toString());
    }



}
