package com.ldtsfeup2526.bobTheDestructor.view.screens;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.stats.Stats;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.menu.TitleViewer;
import com.ldtsfeup2526.bobTheDestructor.view.menu.WallpaperViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class EndViewerTest {
    private EndViewer viewer;
    private Stats stats;
    private ViewerProvider viewerProvider;
    private TitleViewer titleViewer;
    private WallpaperViewer wallpaperViewer;

    @BeforeEach
    void setUp() {
        stats = mock(Stats.class);
        when(stats.getMinutes()).thenReturn(1);
        when(stats.getSeconds()).thenReturn(30);
        when(stats.getMineralsCollected()).thenReturn(10);
        
        titleViewer = mock(TitleViewer.class);
        wallpaperViewer = mock(WallpaperViewer.class);
        viewerProvider = mock(ViewerProvider.class);
        
        when(viewerProvider.getTitleViewer()).thenReturn(titleViewer);
        when(viewerProvider.getWallpaperViewer()).thenReturn(wallpaperViewer);
        
        viewer = new EndViewer(stats, viewerProvider);
    }

    @Test
    void testDrawVerifiably() throws IOException {
        GUI gui = mock(GUI.class);
        when(stats.getMinutes()).thenReturn(5);
        when(stats.getSeconds()).thenReturn(9);
        when(stats.getMineralsCollected()).thenReturn(42);
        
        viewer.draw(gui, 0.1);
        
        verify(gui).drawBackground(argThat(color -> color.getRed() == 57 && color.getGreen() == 53 && color.getBlue() == 74));
        verify(wallpaperViewer).draw(gui);
        
        // Time text: "Time - 5:09"
        verify(titleViewer).draw(argThat(p -> p.getX() == 83 && p.getY() == 45 - 5), eq("Time - 5:09"), eq(gui));
        
        // Minerals text: "Minerals Collected - 42"
        verify(titleViewer).draw(argThat(p -> p.getX() == 83 && p.getY() == 45 + 5), eq("Minerals Collected - 42"), eq(gui));
        
        verify(titleViewer).draw(argThat(p -> p.getX() == 83 && p.getY() == 10), eq("Thank you for playing!"), eq(gui));
        
        verify(gui).refresh();
    }
}
