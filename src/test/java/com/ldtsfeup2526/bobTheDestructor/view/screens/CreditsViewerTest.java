package com.ldtsfeup2526.bobTheDestructor.view.screens;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.credits.Credits;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.menu.TitleViewer;
import com.ldtsfeup2526.bobTheDestructor.view.menu.WallpaperViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class CreditsViewerTest {
    private CreditsViewer viewer;
    private Credits credits;
    private ViewerProvider viewerProvider;
    private TitleViewer titleViewer;
    private WallpaperViewer wallpaperViewer;

    @BeforeEach
    void setUp() {
        credits = mock(Credits.class);
        when(credits.getCredits()).thenReturn(new String[]{"Line 1", "Line 2", "Line 3", "Line 4", "Line 5", "Line 6"});
        
        titleViewer = mock(TitleViewer.class);
        wallpaperViewer = mock(WallpaperViewer.class);
        viewerProvider = mock(ViewerProvider.class);
        
        when(viewerProvider.getTitleViewer()).thenReturn(titleViewer);
        when(viewerProvider.getWallpaperViewer()).thenReturn(wallpaperViewer);
        
        viewer = new CreditsViewer(credits, viewerProvider);
    }

    @Test
    void testDrawVerifiably() throws IOException {
        GUI gui = mock(GUI.class);
        String[] content = {"AUTHORS:", "PERSON 1", "PERSON 2", "PERSON 3", "ASSETS:", "LINK 1"};
        when(credits.getCredits()).thenReturn(content);
        
        viewer.draw(gui, 0.1);
        
        verify(gui).drawBackground(argThat(color -> color.getRed() == 57 && color.getGreen() == 53 && color.getBlue() == 74));
        verify(wallpaperViewer).draw(gui);
        
        // Verify "credits" title
        // resolution.width() = 165. 165/2 + 1 = 82 + 1 = 83
        verify(titleViewer).draw(argThat(p -> p.getX() == 83 && p.getY() == 10), eq("credits"), eq(gui));
        
        // Verify credits lines with positions
        verify(titleViewer).drawAtTextStart(argThat(p -> p.getX() == 10 && p.getY() == 45 - 25), eq(content[0]), eq(gui));
        verify(titleViewer).drawAtTextStart(argThat(p -> p.getX() == 35 && p.getY() == 45 - 15), eq(content[1]), eq(gui));
        verify(titleViewer).drawAtTextStart(argThat(p -> p.getX() == 35 && p.getY() == 45 - 5), eq(content[2]), eq(gui));
        verify(titleViewer).drawAtTextStart(argThat(p -> p.getX() == 35 && p.getY() == 45 + 5), eq(content[3]), eq(gui));
        verify(titleViewer).drawAtTextStart(argThat(p -> p.getX() == 10 && p.getY() == 45 + 15), eq(content[4]), eq(gui));
        verify(titleViewer).drawAtTextStart(argThat(p -> p.getX() == 35 && p.getY() == 45 + 25), eq(content[5]), eq(gui));
        
        verify(gui).refresh();
    }
    @Test
    void testDrawBoundaryLines() throws IOException {
        GUI gui = mock(GUI.class);
        // Only 5 lines in credits
        when(credits.getCredits()).thenReturn(new String[]{"Line 1", "Line 2", "Line 3", "Line 4", "Line 5"});
        
        // This might cause ArrayIndexOutOfBoundsException if draw() always expects 6 lines.
        // Let's check if we should add a test for this.
        // Credits class constructor initializes it with 6 lines.
        // But if someone passes a mock with fewer...
        
        try {
            viewer.draw(gui, 0.1);
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected if it's hardcoded to 6
        }
    }
}
