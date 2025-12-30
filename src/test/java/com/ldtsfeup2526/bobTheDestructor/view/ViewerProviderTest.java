package com.ldtsfeup2526.bobTheDestructor.view;

import com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ViewerProviderTest {
    @Test
    void testGetViewers() throws IOException {
        SpriteLoader spriteLoader = mock(SpriteLoader.class);
        Sprite mockSprite = mock(Sprite.class);
        when(spriteLoader.get(anyString())).thenReturn(mockSprite);
        when(mockSprite.getSize()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Size(1, 1));
        
        ViewerProvider provider = new ViewerProvider(spriteLoader);
        
        assertNotNull(provider.getPlayerViewer());
        assertNotNull(provider.getButtonViewer());
        assertNotNull(provider.getWallpaperViewer());
        assertNotNull(provider.getSceneViewer());
        assertNotNull(provider.getMineralViewer());
        assertNotNull(provider.getOverlayViewer());
        assertNotNull(provider.getTitleViewer());
        assertNotNull(provider.getSliderViewer());
    }
}
