package com.ldtsfeup2526.bobTheDestructor.model.game.scene;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;

import static org.junit.jupiter.api.Assertions.*;

public class GameSoundtrackTest {
    @Test
    void testConstructor() {
        try (var mockedAudioSystem = mockStatic(javax.sound.sampled.AudioSystem.class)) {
            mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getClip()).thenReturn(mock(Clip.class));
            mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getAudioInputStream(any(java.net.URL.class))).thenReturn(mock(AudioInputStream.class));
            
            GameSoundtrack soundtrack = new GameSoundtrack();
            assertNotNull(soundtrack.getAudioInput());
            assertNotNull(soundtrack.getSoundtrackClip());
        }
    }

    @Test
    void testConstructorResourceNotFound() {
        try (var mockedAudioSystem = mockStatic(javax.sound.sampled.AudioSystem.class)) {
            GameSoundtrack soundtrack = new GameSoundtrack("non-existent.wav");
            assertNull(soundtrack.getAudioInput());
            assertNull(soundtrack.getSoundtrackClip());
        }
    }

    @Test
    void testConstructorException() {
        try (var mockedAudioSystem = mockStatic(javax.sound.sampled.AudioSystem.class)) {
            mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getClip()).thenThrow(new RuntimeException("Test"));
            GameSoundtrack soundtrack = new GameSoundtrack();
            assertNull(soundtrack.getAudioInput());
            assertNull(soundtrack.getSoundtrackClip());
        }
    }
}
