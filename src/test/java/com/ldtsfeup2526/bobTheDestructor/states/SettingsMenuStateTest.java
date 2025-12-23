package com.ldtsfeup2526.bobTheDestructor.states;

import com.ldtsfeup2526.bobTheDestructor.model.menu.SettingsMenu;
import com.ldtsfeup2526.bobTheDestructor.view.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class SettingsMenuStateTest {
    @Test
    void testConstructor() throws IOException {
        SettingsMenu model = mock(SettingsMenu.class);
        when(model.getSoundPlayer()).thenReturn(mock(com.ldtsfeup2526.bobTheDestructor.sounds.SoundPlayer.class));
        
        SpriteLoader spriteLoader = mock(SpriteLoader.class);
        Sprite sprite = mock(Sprite.class);
        when(spriteLoader.get(anyString())).thenReturn(sprite);
        when(sprite.getSize()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Size(1, 1));
        
        SettingsMenuState state = new SettingsMenuState(model, spriteLoader);
        assertNotNull(state.createController());
        assertNotNull(state.createScreenViewer(new com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider(spriteLoader)));
    }
}
