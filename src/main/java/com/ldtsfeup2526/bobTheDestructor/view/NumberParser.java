package com.ldtsfeup2526.bobTheDestructor.view;

import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NumberParser {
    private final Map<Character, Sprite> spriteMap = new HashMap<>();
    private final int distanceBetweenChars;

    public NumberParser(SpriteLoader spriteLoader, String font) throws IOException {
        this(spriteLoader, font, 5);
    }

    public NumberParser(SpriteLoader spriteLoader, String font, int distanceBetweenChars) throws IOException {
        this.distanceBetweenChars = distanceBetweenChars;
        for (int i = 0; i < 10; i++) {
            spriteMap.put(String.valueOf(i).charAt(0), spriteLoader.get(font + "num" + String.valueOf(i) + ".png"));
        }
    }

    public List<Sprite> get(String string) {
        List<Sprite> sprites = new ArrayList<>();
        for(int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            Sprite sprite = spriteMap.get(c);
            sprite.setOffset(new Position(distanceBetweenChars * i, 0));
            sprites.add(sprite);
        }
        return sprites;
    }
}
