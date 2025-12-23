package com.ldtsfeup2526.bobTheDestructor.sounds;

import org.junit.jupiter.api.Test;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class SoundLoaderTest {
    @Test
    void testLoadSoundNull() {
        SoundLoader loader = new SoundLoader();
        assertThrows(IllegalArgumentException.class, () -> loader.loadSound(null, mock(Clip.class)));
        assertThrows(IllegalArgumentException.class, () -> loader.loadSound(mock(AudioInputStream.class), null));
    }
}
