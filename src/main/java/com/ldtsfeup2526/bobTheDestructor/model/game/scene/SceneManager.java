package com.ldtsfeup2526.bobTheDestructor.model.game.scene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.sun.tools.attach.VirtualMachine.list;

public class SceneManager {
    private final SceneBuilder sceneBuilder;
    private Scene scene;
    private int numberOfCaves = 10;
    private List<String> cavesPathChosen;
    private int currentCavePathIndex;

    public SceneManager (SceneBuilder sceneBuilder) throws IOException {
        cavesPathChosen = chooseCaves();
        this.sceneBuilder = sceneBuilder;
        this.scene = sceneBuilder.createScene(getNextCavePath());
    }

    public Scene getScene() {
        return scene;
    }

    public SceneBuilder getSceneBuilder() {
        return sceneBuilder;
    }

    private List<String> chooseCaves() {
        List<String> caveList = new ArrayList<>();
        for (int i = 0; i < numberOfCaves; i++) {
            caveList.add("caves/cave" + String.valueOf(i) + "/");
        }

        Collections.shuffle(caveList);
        caveList.subList(5, caveList.size()).clear();

        return caveList;
    }

    private String getNextCavePath() {
        if (currentCavePathIndex >= cavesPathChosen.size()) {
            return "";
        }

        String path = cavesPathChosen.get(currentCavePathIndex);
        currentCavePathIndex++;

        return path;
    }

    public List<String> getCavesPathChosen() {
        return cavesPathChosen;
    }
}
