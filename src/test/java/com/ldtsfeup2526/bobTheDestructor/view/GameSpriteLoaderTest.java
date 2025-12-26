package com.ldtsfeup2526.bobTheDestructor.view;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class GameSpriteLoaderTest {
    @Test
    void testGetSpriteNotFound() {
        GameSpriteLoader loader = new GameSpriteLoader();
        assertThrows(NullPointerException.class, () -> loader.get("non-existent.png"));
    }

    @Test
    void testGetAndCache() throws IOException {
        GameSpriteLoader loader = new GameSpriteLoader();
        String path = "background/bg1.png";
        Sprite sprite1 = loader.get(path);
        assertNotNull(sprite1);
        
        Sprite sprite2 = loader.get(path);
        assertSame(sprite1, sprite2, "Should return cached sprite");
    }

    @Test
    void testGetBufferedImageAndCache() throws IOException {
        GameSpriteLoader loader = new GameSpriteLoader();
        String path = "background/bg1.png";
        BufferedImage img1 = loader.getBufferedImage(path);
        assertNotNull(img1);

        BufferedImage img2 = loader.getBufferedImage(path);
        assertSame(img1, img2, "Should return cached image");

        Sprite sprite = loader.get(path);
        assertSame(img1, sprite.getImage(), "Sprite should use the same cached image");
    }
}
