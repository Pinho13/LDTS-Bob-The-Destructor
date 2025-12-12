package com.ldtsfeup2526.bobTheDestructor.model.game.scene;

public class SceneManager {
    private final SceneBuilder sceneBuilder;
    private Scene scene;
    public SceneManager (SceneBuilder sceneBuilder) {
        this.sceneBuilder = sceneBuilder;
        this.scene = sceneBuilder.createScene();
    }

    public Scene getScene() {
        return scene;
    }
}
