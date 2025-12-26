package com.ldtsfeup2526.bobTheDestructor.states;

import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.IdleState;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.RigidBody;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.Scene;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.SceneManager;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Size;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundPlayer;
import com.ldtsfeup2526.bobTheDestructor.view.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.screens.ScreenViewer;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class GameStateTest {

    @Test
    void testConstructor_doesNotExplode() throws IOException {
        SceneManager model = mock(SceneManager.class);
        Scene scene = mock(Scene.class);
        PlayerModel playerModel = mock(PlayerModel.class);
        RigidBody rb = mock(RigidBody.class);

        when(model.getScene()).thenReturn(scene);
        when(model.getCavesPathChosen()).thenReturn(List.of("caves/cave0/"));
        when(model.getCurrentCavePathIndex()).thenReturn(0);
        when(model.getNextCavePath()).thenReturn("caves/cave0/");
        when(scene.getPlayerModel()).thenReturn(playerModel);
        when(playerModel.getRigidBody()).thenReturn(rb);
        when(playerModel.getState()).thenReturn(new IdleState(playerModel));
        when(rb.getPosition()).thenReturn(new Vector(0f, 0f));
        when(scene.getMineralModels()).thenReturn(Collections.emptyList());
        when(scene.getCaveFilePath()).thenReturn("caves/cave0/");

        SoundPlayer soundPlayer = mock(SoundPlayer.class);
        Clip clip = mock(Clip.class);
        when(scene.getSoundPlayer()).thenReturn(soundPlayer);
        when(soundPlayer.getSound()).thenReturn(clip);
        doNothing().when(clip).start();
        doNothing().when(clip).loop(anyInt());


        SpriteLoader spriteLoader = mock(SpriteLoader.class);
        Sprite sprite = mock(Sprite.class);
        when(spriteLoader.get(anyString())).thenReturn(sprite);
        when(sprite.getSize()).thenReturn(new Size(1, 1));
        when(spriteLoader.getBufferedImage(anyString()))
                .thenReturn(new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB));

        // Create a subclass to avoid real object instantiation that causes OOM
        GameState state = new GameState(model, spriteLoader) {
            @Override
            public ScreenViewer<SceneManager> createScreenViewer(ViewerProvider viewerProvider) {
                return mock(ScreenViewer.class);
            }

            @Override
            public Controller<SceneManager> createController() {
                return mock(Controller.class);
            }
        };

        assertNotNull(state.getModel());
        assertNotNull(state.createController());
    }

}
