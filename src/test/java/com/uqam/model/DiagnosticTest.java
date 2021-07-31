package com.uqam.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DiagnosticTest {

    @Test
    void testValidDesignation(){
        Diagnostic diagnostic = new Diagnostic("Covid19");
        Assertions.assertTrue(diagnostic.validDesignation(diagnostic.getDesignation()));
    }

    @Test
    void testValidDesignation2(){
        Diagnostic diagnostic = new Diagnostic("5");
        Assertions.assertFalse(diagnostic.validDesignation(diagnostic.getDesignation()));
    }

}
