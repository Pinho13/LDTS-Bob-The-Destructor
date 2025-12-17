package com.ldtsfeup2526.bobTheDestructor.view.screens;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.googlecode.lanterna.TextColor;
import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.Scene;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.SceneManager;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.game.MineralViewer;
import com.ldtsfeup2526.bobTheDestructor.view.game.PlayerViewer;
import com.ldtsfeup2526.bobTheDestructor.view.game.SceneViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GameViewerTest {
    private GameViewer gameViewer;
    private SceneManager sceneManager;
    private Scene scene;

    private ViewerProvider viewerProvider;
    private PlayerViewer playerViewer;
    private SceneViewer sceneViewer;
    private MineralViewer mineralViewer;

    @BeforeEach
    void setUp() throws IOException {
        sceneManager = mock(SceneManager.class);
        scene = mock(Scene.class);

        viewerProvider = mock(ViewerProvider.class);
        playerViewer = mock(PlayerViewer.class);
        sceneViewer = mock(SceneViewer.class);
        mineralViewer = mock(MineralViewer.class);

        when(viewerProvider.getPlayerViewer()).thenReturn(playerViewer);
        when(viewerProvider.getSceneViewer()).thenReturn(sceneViewer);
        when(viewerProvider.getMineralViewer()).thenReturn(mineralViewer);

        when(sceneManager.getCavesPathChosen()).thenReturn(List.of("caves/cave0/"));
        doNothing().when(sceneViewer).retrieveCaves(any());

        when(sceneManager.getScene()).thenReturn(scene);
        when(scene.getMineralModels()).thenReturn(List.of());
        when(scene.getPlayerModel()).thenReturn(mock(PlayerModel.class));

        gameViewer = new GameViewer(sceneManager, viewerProvider);
    }

    @Test
    void drawTest() throws IOException {
        GUI gui = mock(GUI.class);
        double deltaTime = 0.016;

        gameViewer.draw(gui, deltaTime);

        verify(gui, times(1)).drawBackground(any(TextColor.class));
        verify(sceneViewer, times(1)).draw(eq(scene), eq(gui), eq(deltaTime));
        verify(playerViewer, times(1)).draw(eq(scene.getPlayerModel()), eq(gui), eq(deltaTime));
        verify(gui, times(1)).refresh();
        verify(gui, never()).clear();
    }
}
