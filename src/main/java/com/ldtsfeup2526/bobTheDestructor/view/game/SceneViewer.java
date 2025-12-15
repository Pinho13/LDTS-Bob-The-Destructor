package com.ldtsfeup2526.bobTheDestructor.view.game;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.Scene;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.view.ElementViewer;
import com.ldtsfeup2526.bobTheDestructor.view.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SceneViewer implements ElementViewer<Scene> {
    private final Map<String, Sprite[]> spriteMap = new HashMap<>();
    private final SpriteLoader spriteLoader;

    public SceneViewer(SpriteLoader spriteLoader) {
        this.spriteLoader = spriteLoader;
    }

    public void retrieveCaves(List<String> cavesPath) throws IOException {
        for (String path : cavesPath) {
            spriteMap.put(path, new Sprite[] {
                    spriteLoader.get(path+"structure.png"),
                    spriteLoader.get(path+"details.png")
            });
        }
    }

    public void draw(Scene model, GUI gui, double deltaTime) {
        Sprite structureSprite = spriteMap.get(model.getCaveFilePath())[0];
        Sprite detailsSprite = spriteMap.get(model.getCaveFilePath())[1];
        structureSprite.draw(new Position(0, 0), gui);
        detailsSprite.draw(new Position(0, 0), gui);
    }

}
