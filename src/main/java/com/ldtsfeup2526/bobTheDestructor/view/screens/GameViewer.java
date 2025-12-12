package com.ldtsfeup2526.bobTheDestructor.view.screens;

import com.googlecode.lanterna.TextColor;
import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.Scene;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.SceneManager;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.elements.PlayerViewer;

import java.io.IOException;

public class GameViewer extends ScreenViewer<SceneManager> {

    private final PlayerViewer playerViewer;

    public GameViewer(SceneManager model, ViewerProvider viewerProvider) {
        super(model);
        this.playerViewer = viewerProvider.getPlayerViewer();
    }

    @Override
    public void draw(GUI gui) throws IOException {

        //gui.clear();
        gui.drawBackground(new TextColor.RGB(30, 30, 46));
        playerViewer.draw(getModel().getScene().getPlayerModel(), gui);


        gui.refresh();
    }
}
