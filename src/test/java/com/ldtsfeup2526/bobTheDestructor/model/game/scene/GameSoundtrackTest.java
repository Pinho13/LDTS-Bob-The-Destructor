package com.ldtsfeup2526.bobTheDestructor.model.game.scene;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;

import static org.junit.jupiter.api.Assertions.*;

public class GameSoundtrackTest {
    @Test
    void testConstructor() {
        GameSoundtrack soundtrack = new GameSoundtrack();
        try {
            AudioInputStream input = soundtrack.getAudioInput();
            Clip clip = soundtrack.getSoundtrackClip();
        } catch (Exception e) {
            fail("Getters should not throw exception");
        }
    }

    @Test
    void testConstructorResourceNotFound() {
        GameSoundtrack soundtrack = new GameSoundtrack("non-existent.wav");
        assertNull(soundtrack.getAudioInput());
        assertNull(soundtrack.getSoundtrackClip());
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
