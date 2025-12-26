package com.ldtsfeup2526.bobTheDestructor.controller.game;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.IdleState;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerState;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralState;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.RigidBody;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.Scene;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.SceneBuilder;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.SceneManager;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundPlayer;
import com.ldtsfeup2526.bobTheDestructor.states.MainMenuState;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
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

    @BeforeEach
    void setUp() throws IOException {
        sceneManager = mock(SceneManager.class);
        sceneBuilder = mock(SceneBuilder.class);
        scene = mock(Scene.class);
        player = mock(PlayerModel.class);
        game = mock(Game.class);
    
        com.ldtsfeup2526.bobTheDestructor.model.game.physics.RigidBody rb = mock(com.ldtsfeup2526.bobTheDestructor.model.game.physics.RigidBody.class);
        when(player.getRigidBody()).thenReturn(rb);
        when(rb.getPosition()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(0.0f, 0.0f));
        when(player.getState()).thenReturn(new IdleState(player));

        when(sceneManager.getScene()).thenReturn(scene);
        when(sceneManager.getNextCavePath()).thenReturn("caves/cave0/");
        when(sceneManager.getCavesPathChosen()).thenReturn(List.of("caves/cave0/"));
        when(scene.getPlayerModel()).thenReturn(player);
        when(scene.getMineralModels()).thenReturn(new ArrayList<>());
        when(scene.getCaveFilePath()).thenReturn("caves/cave0/");

        SoundPlayer soundPlayer = mock(SoundPlayer.class);
        Clip mockClip = mock(Clip.class);
        FloatControl mockControl = mock(FloatControl.class);
        
        when(scene.getSoundPlayer()).thenReturn(soundPlayer);
        when(soundPlayer.getSound()).thenReturn(mockClip);
        when(mockClip.isControlSupported(FloatControl.Type.MASTER_GAIN)).thenReturn(true);
        when(mockClip.getControl(FloatControl.Type.MASTER_GAIN)).thenReturn(mockControl);

        when(sceneBuilder.createScene(any(), any())).thenReturn(scene);

        when(sceneManager.getScene()).thenReturn(scene);
        when(scene.getPlayerModel()).thenReturn(player);
        when(player.getPosition()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Position(0, 0));

        SpriteLoader spriteLoader = mock(SpriteLoader.class);
        when(game.getSpriteLoader()).thenReturn(spriteLoader);
        com.ldtsfeup2526.bobTheDestructor.view.Sprite sprite = mock(com.ldtsfeup2526.bobTheDestructor.view.Sprite.class);
        when(spriteLoader.get(anyString())).thenReturn(sprite);
        when(sprite.getSize()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Size(1, 1));
        
        controller = new SceneController(sceneManager, sceneBuilder);
    }

    @Test
    void testUpdateQuit() throws IOException {
        SoundPlayer soundPlayer = mock(SoundPlayer.class);
        when(scene.getSoundPlayer()).thenReturn(soundPlayer);
        when(soundPlayer.getSound()).thenReturn(mock(javax.sound.sampled.Clip.class));

        controller.update(game, List.of(Action.QUIT));
        verify(game).setState(any(MainMenuState.class));
        verify(soundPlayer).stop();
    }

    @Test
    void testUpdateRegular() throws IOException {
        controller.update(game, List.of(Action.LEFT));
        
        verify(scene, atLeastOnce()).getMineralModels();
        verify(scene).cleanupMinerals();
        verify(player).moveLeft();
        verify(player).physicsUpdate(scene);
    }

    @Test
    void testConstructorWithSound() throws IOException {
        SceneManager sm = mock(SceneManager.class);
        Scene s = mock(Scene.class);
        SoundPlayer sp = mock(SoundPlayer.class);
        Clip clip = mock(Clip.class);
        FloatControl control = mock(FloatControl.class);
        PlayerModel pm = mock(PlayerModel.class);
        RigidBody rb = mock(RigidBody.class);

        when(sm.getScene()).thenReturn(s);
        when(s.getSoundPlayer()).thenReturn(sp);
        when(sp.getSound()).thenReturn(clip);
        when(clip.isControlSupported(FloatControl.Type.MASTER_GAIN)).thenReturn(true);
        when(clip.getControl(FloatControl.Type.MASTER_GAIN)).thenReturn(control);
        when(s.getPlayerModel()).thenReturn(pm);
        when(pm.getRigidBody()).thenReturn(rb);
        when(rb.getPosition()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(0,0));
        when(pm.getState()).thenReturn(new IdleState(pm));
        
        com.ldtsfeup2526.bobTheDestructor.model.GameSettings.getInstance().setMasterGain(10.0f);

        SceneBuilder sb = mock(SceneBuilder.class);
        when(sb.createScene(any(), any())).thenReturn(s);

        new SceneController(sm, sb);
        
        verify(control).setValue(5.0f);
        verify(sp).start();
        verify(pm).addMiningListener(any());
    }

    @Test
    void testUpdateMining() {
        MineralModel mineral = mock(MineralModel.class);
        when(mineral.getState()).thenReturn(MineralState.SELECTED);
        List<MineralModel> minerals = new ArrayList<>();
        minerals.add(mineral);
        when(scene.getMineralModels()).thenReturn(minerals);
        
        controller.updateMining();
        verify(scene).cleanupMinerals();
        verify(player).updateSelectedMineral(minerals);
        verify(scene).unselectAllMinerals();
    }

    @Test
    void testOnMiningFinishedWithStateMineral() {
        PlayerState state = mock(PlayerState.class);
        MineralModel mineral = mock(MineralModel.class);
        when(player.getState()).thenReturn(state);
        when(state.getMineral()).thenReturn(mineral);
        
        controller.onMiningFinished(player);
        
        verify(mineral).setState(MineralState.DESTROYED);
        verify(scene).incrementCurrentMineralsCollected();
    }

    @Test
    void testOnMiningFinishedWithSelectedMineral() {
        PlayerState state = mock(PlayerState.class);
        MineralModel mineral = mock(MineralModel.class);
        when(player.getState()).thenReturn(state);
        when(state.getMineral()).thenReturn(null);
        when(player.getMineralSelected()).thenReturn(mineral);
        
        controller.onMiningFinished(player);
        
        verify(mineral).setState(MineralState.DESTROYED);
        verify(scene).incrementCurrentMineralsCollected();
    }

    @Test
    void testUpdateMiningWithSelected() {
        MineralModel mineral = mock(MineralModel.class);
        when(mineral.getState()).thenReturn(MineralState.UNSELECTED);

        when(player.getMineralSelected()).thenReturn(mineral);
        
        controller.updateMining();
        
        verify(mineral).setState(MineralState.SELECTED);
    }

    @Test
    void testUpdateSceneStateNextCave() throws IOException {
        when(player.getPosition()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Position(0, Game.resolution.height() + 1));
        when(sceneManager.getNextCavePath()).thenReturn("caves/cave1/");
        
        clearInvocations(sceneManager);
        
        controller.updateSceneState(game, new ArrayList<>());
        
        verify(sceneManager).updateTotalMineralsCollected();
        verify(sceneManager).setScene(any(Scene.class));
    }

    @Test
    void testUpdateSceneStateBoundary() throws IOException {
        when(player.getPosition()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Position(0, Game.resolution.height()));
        controller.updateSceneState(game, new ArrayList<>());
        verify(sceneManager, never()).updateTotalMineralsCollected();

        when(player.getPosition()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Position(0, Game.resolution.height() + 1));
        when(sceneManager.getNextCavePath()).thenReturn("caves/cave1/");
        controller.updateSceneState(game, new ArrayList<>());
        verify(sceneManager).updateTotalMineralsCollected();
    }

    @Test
    void testUpdateQuitNoSound() throws IOException {
        when(scene.getSoundPlayer()).thenReturn(null);

        controller.updateSceneState(game, List.of(Action.QUIT));
        verify(game).setState(any(MainMenuState.class));
    }

    @Test
    void testUpdateQuitNoClip() throws IOException {
        SoundPlayer sp = mock(SoundPlayer.class);
        when(scene.getSoundPlayer()).thenReturn(sp);
        when(sp.getSound()).thenReturn(null);
        
        controller.updateSceneState(game, List.of(Action.QUIT));
        verify(game).setState(any(MainMenuState.class));
        verify(sp, never()).stop();
    }

    @Test
    void testUpdateSceneStateNoMoreCaves() throws IOException {
        when(player.getPosition()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Position(0, Game.resolution.height() + 1));
        when(sceneManager.getNextCavePath()).thenReturn(null);
        
        controller.updateSceneState(game, new ArrayList<>());
        
        verify(game).setState(any(MainMenuState.class));
    }

    @Test
    void testUpdateSceneStateNotBeyondHeight() throws IOException {
        when(player.getPosition()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Position(0, Game.resolution.height()));
        
        controller.updateSceneState(game, new ArrayList<>());
        
        verify(sceneManager, never()).updateTotalMineralsCollected();
    }

    @Test
    void testConstructorWithSoundNoControl() throws IOException {
        SceneManager sm = mock(SceneManager.class);
        Scene s = mock(Scene.class);
        SoundPlayer sp = mock(SoundPlayer.class);
        Clip clip = mock(Clip.class);
        PlayerModel pm = mock(PlayerModel.class);
        RigidBody rb = mock(RigidBody.class);

        when(sm.getScene()).thenReturn(s);
        when(s.getSoundPlayer()).thenReturn(sp);
        when(sp.getSound()).thenReturn(clip);
        when(clip.isControlSupported(FloatControl.Type.MASTER_GAIN)).thenReturn(false);
        when(s.getPlayerModel()).thenReturn(pm);
        when(pm.getRigidBody()).thenReturn(rb);
        when(rb.getPosition()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(0,0));
        when(pm.getState()).thenReturn(new IdleState(pm));

        SceneBuilder sb = mock(SceneBuilder.class);
        when(sb.createScene(any(), any())).thenReturn(s);

        new SceneController(sm, sb);
        
        verify(sp).start();
    }
    @Test
    void testOnMiningFinishedSelected() {
        PlayerModel player = mock(PlayerModel.class);
        com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel mineral = mock(com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel.class);
        com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerState state = mock(com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerState.class);
        when(player.getState()).thenReturn(state);
        when(state.getMineral()).thenReturn(null);
        when(player.getMineralSelected()).thenReturn(mineral);
        
        controller.onMiningFinished(player);
        verify(mineral).setState(MineralState.DESTROYED);
    }

    @Test
    void testOnMiningFinishedBothNull() {
        PlayerModel player = mock(PlayerModel.class);
        com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerState state = mock(com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerState.class);
        when(player.getState()).thenReturn(state);
        when(state.getMineral()).thenReturn(null);
        when(player.getMineralSelected()).thenReturn(null);
        
        controller.onMiningFinished(player);

        verify(scene, never()).incrementCurrentMineralsCollected();
    }

    @Test
    void testConstructorNoSoundPlayer() throws IOException {
        SceneManager sm = mock(SceneManager.class);
        Scene s = mock(Scene.class);
        when(sm.getScene()).thenReturn(s);
        when(s.getSoundPlayer()).thenReturn(null);
        
        PlayerModel pm = mock(PlayerModel.class);
        RigidBody rb = mock(RigidBody.class);
        when(s.getPlayerModel()).thenReturn(pm);
        when(pm.getRigidBody()).thenReturn(rb);
        when(rb.getPosition()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(0,0));
        when(pm.getState()).thenReturn(new IdleState(pm));

        SceneBuilder sb = mock(SceneBuilder.class);
        when(sb.createScene(any(), any())).thenReturn(s);

        new SceneController(sm, sb);
    }

    @Test
    void testConstructorNoClip() throws IOException {
        SceneManager sm = mock(SceneManager.class);
        Scene s = mock(Scene.class);
        when(sm.getScene()).thenReturn(s);
        SoundPlayer sp = mock(SoundPlayer.class);
        when(s.getSoundPlayer()).thenReturn(sp);
        when(sp.getSound()).thenReturn(null);

        PlayerModel pm = mock(PlayerModel.class);
        RigidBody rb = mock(RigidBody.class);
        when(s.getPlayerModel()).thenReturn(pm);
        when(pm.getRigidBody()).thenReturn(rb);
        when(rb.getPosition()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(0,0));
        when(pm.getState()).thenReturn(new IdleState(pm));

        SceneBuilder sb = mock(SceneBuilder.class);
        when(sb.createScene(any(), any())).thenReturn(s);

        new SceneController(sm, sb);
    }
}
