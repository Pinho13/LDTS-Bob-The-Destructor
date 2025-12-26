package com.ldtsfeup2526.bobTheDestructor.view.screens;

import com.googlecode.lanterna.TextColor;
import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.SceneManager;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.game.MineralViewer;
import com.ldtsfeup2526.bobTheDestructor.view.game.OverlayViewer;
import com.ldtsfeup2526.bobTheDestructor.view.game.PlayerViewer;
import com.ldtsfeup2526.bobTheDestructor.view.game.SceneViewer;

import java.io.IOException;

public class GameViewer extends ScreenViewer<SceneManager> {

    private final PlayerViewer playerViewer;
    private final SceneViewer sceneViewer;
    private final MineralViewer mineralViewer;
    private final OverlayViewer overlayViewer;

    public GameViewer(SceneManager model, ViewerProvider viewerProvider) throws IOException {
        super(model);
        this.playerViewer = viewerProvider.getPlayerViewer();
        this.sceneViewer = viewerProvider.getSceneViewer();
        this.mineralViewer = viewerProvider.getMineralViewer();
        this.overlayViewer = viewerProvider.getOverlayViewer();
        this.sceneViewer.retrieveCaves(model.getCavesPathChosen());
    }

    @Override
    public void draw(GUI gui, double deltaTime) throws IOException {
        gui.drawBackground(new TextColor.RGB(70, 60, 94));
        sceneViewer.draw(getModel().getScene(), gui, deltaTime);
        drawElements(gui, getModel().getScene().getMineralModels(), mineralViewer, deltaTime);
        playerViewer.draw(getModel().getScene().getPlayerModel(), gui, deltaTime);
        overlayViewer.draw(getModel(), gui, deltaTime);

        gui.refresh();
    }
}
