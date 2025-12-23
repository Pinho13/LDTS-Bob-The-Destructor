package com.ldtsfeup2526.bobTheDestructor.sounds;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.sound.sampled.Clip;
import static org.mockito.Mockito.*;

public class BackgroundMusicPlayerTest {
    private Clip musicClip;
    private BackgroundMusicPlayer player;

    @BeforeEach
    void setUp() {
        musicClip = mock(Clip.class);
        player = new BackgroundMusicPlayer(musicClip);
    }

    @Test
    void testStart() {
        player.start();
        verify(musicClip).setMicrosecondPosition(0);
        verify(musicClip).start();
        verify(musicClip).loop(Clip.LOOP_CONTINUOUSLY);
    }

    @Test
    void testStop() {
        player.stop();
        verify(musicClip).stop();
    }

    @Test
    void testSetSound() {
        Clip newClip = mock(Clip.class);
        player.setSound(newClip);
        assert(player.getSound() == newClip);
    }
}
