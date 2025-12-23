package com.ldtsfeup2526.bobTheDestructor.view;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameSpriteLoaderTest {
    @Test
    void testGetSpriteNotFound() {
        GameSpriteLoader loader = new GameSpriteLoader();
        assertThrows(NullPointerException.class, () -> loader.get("non-existent.png"));
    }
 }
