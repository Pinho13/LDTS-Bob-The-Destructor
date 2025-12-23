package com.ldtsfeup2526.bobTheDestructor.sounds;

import javax.sound.sampled.Clip;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SoundPlayerTest {
    @Test
    void testInterface() {
        SoundPlayer player = new SoundPlayer() {
            @Override public void start() {}
            @Override public void stop() {}
            @Override public void setSound(Clip sound) {}
            @Override public Clip getSound() { return null; }
        };
        assertNotNull(player);
    }
}
