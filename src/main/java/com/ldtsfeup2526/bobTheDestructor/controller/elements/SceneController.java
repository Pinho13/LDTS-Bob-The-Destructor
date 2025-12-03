package com.ldtsfeup2526.bobTheDestructor.controller.elements;

import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.game.Scene;

import java.util.List;

public class SceneController extends Controller<Scene> {
    private final PlayerController playerController;
    private final BlocksController blocksController;

    public SceneController(Scene scene, PlayerController playerController, BlocksController blocksController) {
        super(scene);
        this.playerController = playerController;
        this.blocksController = blocksController;
    }

    @Override
    public void update(List<Action> actions) {
        playerController.update(actions);
        blocksController.update(actions);
    }
}
