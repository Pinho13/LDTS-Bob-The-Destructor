package com.ldtsfeup2526.bobTheDestructor.model.game.scene;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SceneManagerTest {
    private SceneManager sceneManager;
    private Scene scene;

    @BeforeEach
    void setUp() throws IOException {
        scene = mock(Scene.class);
        sceneManager = new SceneManager();
        sceneManager.setScene(scene);
    }

    @Test
    void testGetSetScene() {
        assertEquals(scene, sceneManager.getScene());
        Scene anotherScene = mock(Scene.class);
        sceneManager.setScene(anotherScene);
        assertEquals(anotherScene, sceneManager.getScene());
    }

    @Test
    void testCavesPathChosen() {
        List<String> paths = sceneManager.getCavesPathChosen();
        assertEquals(5, paths.size());
        for (String path : paths) {
            assertTrue(path.startsWith("caves/cave"));
        }
    }

    @Test
    void testChooseCavesUniquenessAndShuffle() throws IOException {
        List<String> paths1 = sceneManager.getCavesPathChosen();
        assertEquals(5, paths1.size());
        assertEquals(5, paths1.stream().distinct().count());
        
        // To test shuffle, we create another SceneManager and check if it's different.
        // There are 10 caves, choose 5. 10C5 = 252. Probability of getting same list (in same order) is very low.
        SceneManager sceneManager2 = new SceneManager();
        List<String> paths2 = sceneManager2.getCavesPathChosen();
        
        assertNotEquals(paths1, paths2, "Shuffling should likely produce different cave lists");
    }

    @Test
    void testChooseCavesBoundary() throws IOException {
        // numberOfCaves is 10, loop for (int i = 0; i < numberOfCaves; i++)
        // We can't easily change numberOfCaves as it's private and hardcoded,
        // but we can verify all 10 possible caves are reachable.
        
        List<String> allPossible = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            SceneManager sm = new SceneManager();
            allPossible.addAll(sm.getCavesPathChosen());
        }
        long distinctCount = allPossible.stream().distinct().count();
        assertEquals(10, distinctCount, "All 10 caves should be reachable over multiple shuffles");
    }

    @Test
    void testGetNextCavePathReturnValue() {
        String path = sceneManager.getNextCavePath();
        assertNotNull(path);
        assertFalse(path.isEmpty(), "getNextCavePath should not return an empty string");
        assertTrue(path.startsWith("caves/cave"));
    }

    @Test
    void testTotalMineralsCollected() {
        when(scene.getCurrentMineralsCollected()).thenReturn(5);
        sceneManager.updateTotalMineralsCollected();
        assertEquals(5, sceneManager.getTotalMineralsCollected());
        
        when(scene.getCurrentMineralsCollected()).thenReturn(3);
        sceneManager.updateTotalMineralsCollected();
        assertEquals(8, sceneManager.getTotalMineralsCollected());
        
        // Mutate addition
        when(scene.getCurrentMineralsCollected()).thenReturn(0);
        sceneManager.updateTotalMineralsCollected();
        assertEquals(8, sceneManager.getTotalMineralsCollected());
    }

    @Test
    void testGetNextCavePathReturnsNullWhenFinished() {
        for (int i = 0; i < 5; i++) {
            assertNotNull(sceneManager.getNextCavePath());
        }
        // Test boundary i >= cavesPathChosen.size()
        assertNull(sceneManager.getNextCavePath());
        assertNull(sceneManager.getNextCavePath());
    }

    @Test
    void testTime() {
        assertEquals(0, sceneManager.getTimePassed());
        sceneManager.updateTime(0.16);
        assertEquals(0.16, sceneManager.getTimePassed(), 0.001);
        
        // Mutate timePassed increment
        sceneManager.updateTime(0.01);
        assertEquals(0.17, sceneManager.getTimePassed(), 0.001);
    }
    @Test
    void testGetCurrentCavePathIndex() {
        // Initially index is 0, but getCurrentCavePathIndex returns currentCavePathIndex - 1
        assertEquals(-1, sceneManager.getCurrentCavePathIndex());
        
        sceneManager.getNextCavePath();
        assertEquals(0, sceneManager.getCurrentCavePathIndex());
        
        sceneManager.getNextCavePath();
        assertEquals(1, sceneManager.getCurrentCavePathIndex());
    }

    @Test
    void testGetNextCavePathIndexAtEnd() {
        for (int i = 0; i < 5; i++) {
            sceneManager.getNextCavePath();
        }
        assertNull(sceneManager.getNextCavePath());
        assertEquals(4, sceneManager.getCurrentCavePathIndex());
    }
}
