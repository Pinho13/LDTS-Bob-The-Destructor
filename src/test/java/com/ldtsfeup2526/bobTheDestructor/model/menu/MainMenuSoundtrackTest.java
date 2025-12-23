package com.ldtsfeup2526.bobTheDestructor.model.menu;

import org.junit.jupiter.api.Test;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;

import static org.junit.jupiter.api.Assertions.fail;

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
}
