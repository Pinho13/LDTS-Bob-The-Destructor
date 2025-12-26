package com.ldtsfeup2526.bobTheDestructor.model.menu;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;

import static org.junit.jupiter.api.Assertions.*;

public class MainMenuSoundtrackTest {
    @Test
    void testConstructor() {
        MainMenuSoundtrack soundtrack = new MainMenuSoundtrack();
        try {
            AudioInputStream input = soundtrack.getAudioInput();
            Clip clip = soundtrack.getSoundtrackClip();
        } catch (Exception e) {
            fail("Getters should not throw exception");
        }
    }

    @Test
    void testConstructorException() {
        try (var mockedAudioSystem = mockStatic(javax.sound.sampled.AudioSystem.class)) {
            mockedAudioSystem.when(() -> javax.sound.sampled.AudioSystem.getClip()).thenThrow(new RuntimeException("Test"));
            MainMenuSoundtrack soundtrack = new MainMenuSoundtrack();
            assertNull(soundtrack.getAudioInput());
            assertNull(soundtrack.getSoundtrackClip());
        }
    }
    @Test
    void testConstructorResourceNotFound() {
        MainMenuSoundtrack soundtrack = new MainMenuSoundtrack("non-existent.wav");
        assertNull(soundtrack.getAudioInput());
        assertNull(soundtrack.getSoundtrackClip());
    }
}
