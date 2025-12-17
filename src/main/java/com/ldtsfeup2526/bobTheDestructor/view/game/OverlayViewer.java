package com.ldtsfeup2526.bobTheDestructor.view.game;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.SceneManager;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.view.ElementViewer;
import com.ldtsfeup2526.bobTheDestructor.view.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;

import java.io.IOException;

public class OverlayViewer implements ElementViewer<SceneManager> {
    private final Sprite overlay;
    private final Sprite marker;

    public OverlayViewer(SpriteLoader spriteLoader) throws IOException {
        overlay = spriteLoader.get("sprites/ui_overlay/ui_overlay.png");
        marker = spriteLoader.get("sprites/ui_overlay/marker.png");
    }

    @Override
    public void draw(SceneManager model, GUI gui, double deltaTime) {
        overlay.draw(new Position(0, 0), gui);
        //System.out.println(model.getCurrentCavePathIndex());
        marker.draw(new Position(157, 20 + model.getCurrentCavePathIndex() * 15), gui);
    }
}
