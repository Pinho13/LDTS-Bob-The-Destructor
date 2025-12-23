package com.ldtsfeup2526.bobTheDestructor.sounds;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNull;

public class NullSoundPlayerTest {
    @Test
    void testMethods() {
        NullSoundPlayer player = new NullSoundPlayer();
        player.start();
        player.stop();
        player.setSound(null);
        assertNull(player.getSound());
    }
}
