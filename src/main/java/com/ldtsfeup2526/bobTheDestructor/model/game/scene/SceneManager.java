package com.ldtsfeup2526.bobTheDestructor.model.game.scene;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Random;

import static com.sun.tools.attach.VirtualMachine.list;

public class SceneManager {
    private final SceneBuilder sceneBuilder;
    private Scene scene;
    private int numberOfCaves = 2;
    private final Random random = new Random();
    public SceneManager (SceneBuilder sceneBuilder) throws IOException {
        this.sceneBuilder = sceneBuilder;
        this.scene = sceneBuilder.createScene();
    }

    public Scene getScene() {
        return scene;
    }

    public SceneBuilder getSceneBuilder() {
        return sceneBuilder;
    }

    public String getNextCavePath() {
        int number = random.nextInt(1, numberOfCaves);
        return "caves/cave" + String.valueOf(number);
    }
}
