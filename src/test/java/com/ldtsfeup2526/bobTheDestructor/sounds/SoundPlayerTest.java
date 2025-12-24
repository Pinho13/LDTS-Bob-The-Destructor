package com.ldtsfeup2526.bobTheDestructor.sounds;

import org.junit.jupiter.api.Test;
import javax.sound.sampled.Clip;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SoundPlayerTest {
    @Test
    void testBackgroundMusicPlayer() {
        Clip clip = mock(Clip.class);
        BackgroundMusicPlayer player = new BackgroundMusicPlayer(clip);
        
        assertEquals(clip, player.getSound());
        
        player.start();
        verify(clip).setMicrosecondPosition(0);
        verify(clip).start();
        verify(clip).loop(Clip.LOOP_CONTINUOUSLY);
        
        player.stop();
        verify(clip).stop();
        
        Clip clip2 = mock(Clip.class);
        player.setSound(clip2);
        assertEquals(clip2, player.getSound());
    }

    @Test
    void testNullSoundPlayer() {
        NullSoundPlayer player = new NullSoundPlayer();
        player.start();
        player.stop();
        player.setSound(mock(Clip.class));
        assertNull(player.getSound());
    }
}
