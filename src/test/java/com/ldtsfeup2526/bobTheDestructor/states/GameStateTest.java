package com.ldtsfeup2526.bobTheDestructor.states;

import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.IdleState;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.Scene;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.SceneManager;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundManager;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class GameStateTest {

    @Test
    void testConstructor_doesNotExplode() throws IOException {
        SceneManager model = mock(SceneManager.class);
        SpriteLoader spriteLoader = mock(SpriteLoader.class);
        SoundManager soundManager = mock(SoundManager.class);
        
        // Mocking behavior for State constructor and internal creations
        com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite sprite = mock(com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite.class);
        when(spriteLoader.get(anyString())).thenReturn(sprite);
        when(sprite.getSize()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Size(1, 1));
        
        // SceneBuilder needs buffered images
        java.awt.image.BufferedImage mockImage = new java.awt.image.BufferedImage(8, 8, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        when(spriteLoader.getBufferedImage(anyString())).thenReturn(mockImage);
        
        // SceneManager needs to return a valid next cave path
        when(model.getNextCavePath()).thenReturn("caves/cave0/");
        
        // Mock deep dependencies
        Scene mockScene = mock(Scene.class);
        PlayerModel mockPlayer = mock(PlayerModel.class);
        when(model.getScene()).thenReturn(mockScene);
        when(mockScene.getPlayerModel()).thenReturn(mockPlayer);
        when(mockPlayer.getCollider()).thenReturn(mock(com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider.class));
        when(mockPlayer.getRigidBody()).thenReturn(mock(com.ldtsfeup2526.bobTheDestructor.model.game.physics.RigidBody.class));
        when(mockPlayer.getState()).thenReturn(new IdleState(mockPlayer));

        try (var mockedSceneController = mockConstruction(com.ldtsfeup2526.bobTheDestructor.controller.game.SceneController.class)) {
            GameState state = new GameState(model, spriteLoader, soundManager);
            assertNotNull(state.getModel());
        }
    }
    @Test
    void testCreateControllerAndViewer() throws IOException {
        SceneManager model = mock(SceneManager.class);
        SpriteLoader spriteLoader = mock(SpriteLoader.class);
        SoundManager soundManager = mock(SoundManager.class);
        
        com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite sprite = mock(com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite.class);
        when(spriteLoader.get(anyString())).thenReturn(sprite);
        when(sprite.getSize()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Size(1, 1));
        java.awt.image.BufferedImage mockImage = new java.awt.image.BufferedImage(8, 8, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        when(spriteLoader.getBufferedImage(anyString())).thenReturn(mockImage);
        when(model.getNextCavePath()).thenReturn("caves/cave0/");
        PlayerModel playerModel = mock(PlayerModel.class);
        Scene scene = mock(Scene.class);
        when(model.getScene()).thenReturn(scene);
        when(scene.getPlayerModel()).thenReturn(playerModel);
        when(playerModel.getRigidBody()).thenReturn(mock(com.ldtsfeup2526.bobTheDestructor.model.game.physics.RigidBody.class));

        GameState state = new GameState(model, spriteLoader, soundManager);
        assertNotNull(state.createController());
        assertNotNull(state.createScreenViewer(new com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider(spriteLoader)));
    }

    @Test
    void testEnterExit() throws IOException {
        SceneManager model = mock(SceneManager.class);
        SpriteLoader spriteLoader = mock(SpriteLoader.class);
        SoundManager soundManager = mock(SoundManager.class);
        com.ldtsfeup2526.bobTheDestructor.Game game = mock(com.ldtsfeup2526.bobTheDestructor.Game.class);
        when(game.getSoundManager()).thenReturn(soundManager);
        
        com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite sprite = mock(com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite.class);
        when(spriteLoader.get(anyString())).thenReturn(sprite);
        when(sprite.getSize()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Size(1, 1));

        java.awt.image.BufferedImage mockImage = new java.awt.image.BufferedImage(8, 8, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        when(spriteLoader.getBufferedImage(anyString())).thenReturn(mockImage);
        
        when(model.getNextCavePath()).thenReturn("caves/cave0/");
        PlayerModel playerModel = mock(PlayerModel.class);
        Scene scene = mock(Scene.class);
        when(model.getScene()).thenReturn(scene);
        when(scene.getPlayerModel()).thenReturn(playerModel);
        when(playerModel.getRigidBody()).thenReturn(mock(com.ldtsfeup2526.bobTheDestructor.model.game.physics.RigidBody.class));

        GameState state = new GameState(model, spriteLoader, soundManager);
        state.onEnter(game);
        verify(soundManager).playMusic("sounds/gameSoundtrack.wav");
        
        state.onExit(game);
        verify(soundManager).stopMusic();
    }
}
