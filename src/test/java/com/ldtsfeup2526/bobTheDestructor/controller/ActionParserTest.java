package com.ldtsfeup2526.bobTheDestructor.controller;

import com.ldtsfeup2526.bobTheDestructor.controller.input.ActionParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ActionParserTest {
    private ActionParser actionParser;

    @BeforeEach
    void setUp() {
        actionParser = new ActionParser();
    }


    @Test
    void getInputReaderTest() {
        assertNotNull(actionParser.getInputReader());
    }

    @Test
    void getTest() {
        assertNotNull(actionParser.get());
    }

}
