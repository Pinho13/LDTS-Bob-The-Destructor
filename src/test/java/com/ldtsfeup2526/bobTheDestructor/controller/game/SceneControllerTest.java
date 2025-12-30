package com.ldtsfeup2526.bobTheDestructor.controller.game;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.IdleState;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerState;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralState;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.Scene;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.SceneBuilder;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.SceneManager;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundManager;
import com.ldtsfeup2526.bobTheDestructor.states.EndState;
import com.ldtsfeup2526.bobTheDestructor.states.MainMenuState;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class SceneControllerTest {
    private SceneManager sceneManager;
    private SceneBuilder sceneBuilder;
    private Scene scene;
    private PlayerModel player;
    private SceneController controller;
    private Game game;
    private SoundManager soundManager;

    @BeforeEach
    void setUp() throws IOException {
        sceneManager = mock(SceneManager.class);
        sceneBuilder = mock(SceneBuilder.class);
        scene = mock(Scene.class);
        player = mock(PlayerModel.class);
        game = mock(Game.class);
        soundManager = mock(SoundManager.class);

        when(sceneManager.getScene()).thenReturn(scene);
        when(sceneManager.getNextCavePath()).thenReturn("caves/cave0/");
        when(scene.getPlayerModel()).thenReturn(player);
        when(scene.getMineralModels()).thenReturn(new ArrayList<>());
        
        when(player.getPosition()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Position(0, 0));
        when(player.getState()).thenReturn(new IdleState(player));
        when(player.getCollider()).thenReturn(mock(com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider.class));
        when(player.getRigidBody()).thenReturn(mock(com.ldtsfeup2526.bobTheDestructor.model.game.physics.RigidBody.class));

        when(sceneBuilder.createScene(anyString(), any(PlayerModel.class))).thenReturn(scene);

        when(game.getSoundManager()).thenReturn(soundManager);
        SpriteLoader spriteLoader = mock(SpriteLoader.class);
        when(game.getSpriteLoader()).thenReturn(spriteLoader);
        Sprite mockSprite = mock(Sprite.class);
        when(spriteLoader.get(anyString())).thenReturn(mockSprite);
        when(mockSprite.getSize()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Size(1, 1));
        
        try (var mockedPlayerController = Mockito.mockConstruction(PlayerController.class, (mock, context) -> {
            Mockito.when(mock.getModel()).thenReturn(player);
        })) {
            controller = new SceneController(sceneManager, sceneBuilder, soundManager);
        }
    }

    @Test
    void testUpdateQuit() throws IOException {
        controller.update(game, List.of(Action.QUIT), 0.016);
        verify(game).setState(any(MainMenuState.class));
    }

    @Test
    void testUpdateRegular() throws IOException {
        controller.update(game, List.of(Action.LEFT), 0.016);
        
        verify(scene, atLeastOnce()).getMineralModels();
        verify(scene).cleanupMinerals();
        verify(sceneManager).updateTime(0.016);
        verify(player).updateSelectedMineral(any());
        verify(scene).unselectAllMinerals();
    }

    @Test
    void testOnPickaxeHit() {
        MineralModel mineral = mock(MineralModel.class);
        when(player.getMineralSelected()).thenReturn(mineral);
        PlayerState state = mock(PlayerState.class);
        when(player.getState()).thenReturn(state);
        
        controller.onPickaxeHit(player);
        
        verify(mineral).setState(MineralState.DESTROYED);
        verify(scene).incrementCurrentMineralsCollected();
        verify(soundManager).playSFX("sounds/soundEffects/mining.wav");
    }

    @Test
    void testUpdateSceneStateNextCave() throws IOException {
        when(player.getPosition()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Position(0, 100)); // Beyond height
        when(sceneManager.getNextCavePath()).thenReturn("caves/cave1/");
        
        controller.update(game, new ArrayList<>(), 0.016);
        
        verify(sceneManager, atLeastOnce()).updateTotalMineralsCollected();
        verify(sceneManager, atLeastOnce()).setScene(any(Scene.class));
    }

    @Test
    void testUpdateSceneStateEnd() throws IOException {
        when(player.getPosition()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Position(0, 100)); // Beyond height
        when(sceneManager.getNextCavePath()).thenReturn(null); // No more caves
        
        controller.update(game, new ArrayList<>(), 0.016);
        
        verify(game).setState(any(EndState.class));
    }
    @Test
    void testOnPickaxeHitFromState() {
        MineralModel mineral = mock(MineralModel.class);
        PlayerState state = mock(PlayerState.class);
        when(state.getMineral()).thenReturn(mineral);
        when(player.getState()).thenReturn(state);

        controller.onPickaxeHit(player);

        verify(mineral).setState(MineralState.DESTROYED);
        verify(scene).incrementCurrentMineralsCollected();
    }

    @Test
    void testUpdateMiningSelected() {
        MineralModel mineral = mock(MineralModel.class);
        when(player.getMineralSelected()).thenReturn(mineral);
        
        controller.updateMining();
        
        verify(mineral).setState(MineralState.SELECTED);
    }
    @Test
    void testUpdateFallBelowScreen() throws IOException {
        when(player.getPosition()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Position(0, 200)); // Below screen
        when(sceneManager.getNextCavePath()).thenReturn("caves/cave1/");
        
        controller.update(game, new ArrayList<>(), 0.016);
        
        verify(sceneManager).updateTotalMineralsCollected();
        verify(sceneManager, atLeastOnce()).getNextCavePath();
    }
    @Test
    void testOnPickaxeHitNoMineral() {
        when(player.getMineralSelected()).thenReturn(null);
        PlayerState state = mock(PlayerState.class);
        when(state.getMineral()).thenReturn(null);
        when(player.getState()).thenReturn(state);

        controller.onPickaxeHit(player);

        verify(scene, never()).incrementCurrentMineralsCollected();
        verify(soundManager).playSFX(anyString());
    }

    @Test
    void testUpdateMiningNoSelected() {
        when(player.getMineralSelected()).thenReturn(null);
        controller.updateMining();
        // Should unselect all minerals but not set any as selected
        verify(scene).unselectAllMinerals();
    }

    @Test
    void testUpdateSceneStateNoQuitNoFall() throws IOException {
        when(player.getPosition()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Position(0, 0));
        controller.updateSceneState(game, List.of());
        verify(game, never()).setState(any());
    }
}
