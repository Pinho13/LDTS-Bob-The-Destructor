package com.ldtsfeup2526.bobTheDestructor.view.game;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.Scene;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.view.ElementViewer;
import com.ldtsfeup2526.bobTheDestructor.view.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneViewer implements ElementViewer<Scene> {
    private final Map<String, Sprite[]> spriteMap = new HashMap<>();

    public SceneViewer(SpriteLoader spriteLoader) throws IOException {
        spriteMap.put("caves/", new Sprite[] {
                spriteLoader.get("caves/structure.png")
        });
    }

    public void draw(Scene model, GUI gui) {
        Sprite sprite = spriteMap.get(model.getCaveFilePath())[0];
        sprite.draw(new Position(0, 0), gui);
    }

}
