package com.ldtsfeup2526.bobTheDestructor.model.game.scene;

import org.junit.jupiter.api.Test;

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
}
