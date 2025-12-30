package com.ldtsfeup2526.bobTheDestructor;

import com.ldtsfeup2526.bobTheDestructor.controller.input.ActionParser;
import com.ldtsfeup2526.bobTheDestructor.gui.GUILanterna;
import com.ldtsfeup2526.bobTheDestructor.states.State;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameTest {

    @Test
    void testSetStateAndGetSpriteLoader() throws Exception {
        try (var mockedGui = mockConstruction(GUILanterna.class);
             var mockedAudioSystem = mockStatic(javax.sound.sampled.AudioSystem.class)) {
             mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getClip()).thenReturn(mock(javax.sound.sampled.Clip.class));
             mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getAudioInputStream(any(java.net.URL.class)))
                     .thenReturn(mock(javax.sound.sampled.AudioInputStream.class));

             Game game = new Game();
             assertNotNull(game.getSpriteLoader());
             assertNotNull(game.getSoundManager());

             State<?> state1 = mock(State.class);
             game.setState(state1);
             verify(state1).onEnter(game);

             State<?> state2 = mock(State.class);
             game.setState(state2);
             verify(state1).onExit(game);
             verify(state2).onEnter(game);

             game.setState(null);
             verify(state2).onExit(game);
        }
    }

    @Test
    void testRun() throws Exception {
        try (var mockedGui = mockConstruction(GUILanterna.class);
             var mockedAudioSystem = mockStatic(javax.sound.sampled.AudioSystem.class)) {
            mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getClip()).thenReturn(mock(javax.sound.sampled.Clip.class));
            mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getAudioInputStream(any(java.net.URL.class)))
                    .thenReturn(mock(javax.sound.sampled.AudioInputStream.class));

            Game game = new Game();
            State<?> mockState = mock(State.class);
            game.setState(mockState);
            
            final int[] count = {0};
            doAnswer(invocation -> {
                if (count[0]++ > 0) game.setState(null);
                return null;
            }).when(mockState).update(any(), any(), any(), anyDouble());

            game.run();
            
            verify(mockState, atLeastOnce()).update(eq(game), any(), any(), anyDouble());
            verify(mockedGui.constructed().get(0)).close();
        }
    }

    @Test
    void testMain() throws IOException, InterruptedException {
        try (var mockedGame = mockConstruction(Game.class, (mock, context) -> {
            doNothing().when(mock).run();
        })) {
            Game.main(new String[]{});
            assertEquals(1, mockedGame.constructed().size());
            verify(mockedGame.constructed().get(0)).run();
        }
    }
    @Test
    void testRunSlow() throws Exception {
        try (var mockedGui = mockConstruction(GUILanterna.class);
             var mockedAudioSystem = mockStatic(javax.sound.sampled.AudioSystem.class)) {
            mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getClip()).thenReturn(mock(javax.sound.sampled.Clip.class));
            mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getAudioInputStream(any(java.net.URL.class)))
                    .thenReturn(mock(javax.sound.sampled.AudioInputStream.class));

            Game game = new Game();
            State<?> mockState = mock(State.class);
            game.setState(mockState);
            
            doAnswer(invocation -> {
                game.setState(null);
                return null;
            }).when(mockState).update(any(), any(), any(), anyDouble());

            game.run();
            verify(mockState).update(eq(game), any(), any(), anyDouble());
        }
    }
    @Test
    void testMainException() throws IOException, InterruptedException {
        try (var mockedGame = mockConstruction(Game.class, (mock, context) -> {
            doAnswer(inv -> {
                throw new RuntimeException("Test Exception");
            }).when(mock).run();
        })) {
            Game.main(new String[]{});
            assertEquals(1, mockedGame.constructed().size());
        }
    }

    @Test
    void testRunStateNull() throws Exception {
        try (var mockedGui = mockConstruction(GUILanterna.class);
             var mockedAudioSystem = mockStatic(javax.sound.sampled.AudioSystem.class)) {
            mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getClip()).thenReturn(mock(javax.sound.sampled.Clip.class));
            mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getAudioInputStream(any(java.net.URL.class)))
                    .thenReturn(mock(javax.sound.sampled.AudioInputStream.class));

            Game game = new Game();
            game.setState(null);
            game.run();
            verify(mockedGui.constructed().get(0)).close();
        }
    }

    @Test
    void testRunNoSleep() throws Exception {
        try (var mockedGui = mockConstruction(GUILanterna.class);
             var mockedAudioSystem = mockStatic(javax.sound.sampled.AudioSystem.class)) {
            mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getClip()).thenReturn(mock(javax.sound.sampled.Clip.class));
            mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getAudioInputStream(any(java.net.URL.class)))
                    .thenReturn(mock(javax.sound.sampled.AudioInputStream.class));

            Game game = new Game();
            State<?> mockState = mock(State.class);
            game.setState(mockState);

            doAnswer(invocation -> {
                Thread.sleep(20);
                game.setState(null);
                return null;
            }).when(mockState).update(any(), any(), any(), anyDouble());

            game.run();
            verify(mockState).update(eq(game), any(), any(), anyDouble());
        }
    }
}
