package com.ldtsfeup2526.bobTheDestructor.model.game.scene;

import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;

public class SceneBuilder implements ISceneBuilder{
    private final SpriteLoader spriteLoader;

    public SceneBuilder(SpriteLoader spriteLoader) {
        this.spriteLoader = spriteLoader;
    }

    public Scene createScene() {
        return new Scene();
    }

}
