package com.ldtsfeup2526.bobTheDestructor.view.elements;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.Position;
import com.ldtsfeup2526.bobTheDestructor.view.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BlocksViewer implements ElementViewer{
    private final Map<String, Sprite> spriteMap;

    public BlocksViewer(SpriteLoader spriteLoader) throws IOException {
        spriteMap = new HashMap<>();
        spriteMap.put("sprites/tiles/tile1.png", spriteLoader.get("sprites/tiles/tile1.png"));
    }
    public void draw(Position position, GUI gui) {
        Sprite sprite = spriteMap.get("sprites/tiles/tile1.png");
        sprite.draw(gui, position);
    }
}
