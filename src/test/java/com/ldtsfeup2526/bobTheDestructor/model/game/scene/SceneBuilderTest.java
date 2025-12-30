package com.ldtsfeup2526.bobTheDestructor.model.game.scene;

import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.PointingDirection;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.RigidBody;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SceneBuilderTest {
    private SpriteLoader spriteLoader;
    private SceneBuilder builder;
    private PlayerModel playerModel;
    private RigidBody rigidBody;

    @BeforeEach
    void setUp() {
        spriteLoader = mock(SpriteLoader.class);
        builder = new SceneBuilder(spriteLoader);
        playerModel = mock(PlayerModel.class);
        rigidBody = mock(RigidBody.class);
        when(playerModel.getRigidBody()).thenReturn(rigidBody);
    }

    @Test
    void testCreateScene() throws IOException {
        String path = "caves/cave0/";
        BufferedImage structure = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        structure.setRGB(0, 0, 0xFF000000); // Solid
        structure.setRGB(8, 0, 0x00000000); // Transparent

        BufferedImage enter = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        enter.setRGB(8, 8, 0xFF000000); // Entrance at (8,8)

        BufferedImage ores = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        ores.setRGB(0, 8, 0xFFFDFE89); // Mineral at (0,8)

        when(spriteLoader.getBufferedImage(path + "structure.png")).thenReturn(structure);
        when(spriteLoader.getBufferedImage(path + "enter.png")).thenReturn(enter);
        when(spriteLoader.getBufferedImage(path + "ores.png")).thenReturn(ores);

        Scene scene = builder.createScene(path, playerModel);

        assertNotNull(scene);
        assertEquals(path, scene.getCaveFilePath());
        verify(rigidBody).setPosition(argThat(v -> v.getX() == 8f && v.getY() == 8f));
        
        List<Collider> colliders = scene.getBlockColliders();
        // (0,0) is solid, (8,0), (0,8), (8,8) are transparent in structure image
        assertEquals(1, colliders.size());
        assertEquals(0, colliders.get(0).getPosition().getX());
        assertEquals(0, colliders.get(0).getPosition().getY());

        // Minerals are probabilistic, but we can check if the list exists
        assertNotNull(scene.getMineralModels());
    }

    @Test
    void testCreateMineralsEdgeCases() throws IOException {
        String path = "caves/cave0/";
        BufferedImage structure = new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB);
        BufferedImage enter = new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB);
        BufferedImage ores = new BufferedImage(160, 160, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < 160; y += 8) {
            for (int x = 0; x < 160; x += 8) {
                ores.setRGB(x, y, 0xFFFFFFFF);
            }
        }

        when(spriteLoader.getBufferedImage(path + "structure.png")).thenReturn(structure);
        when(spriteLoader.getBufferedImage(path + "enter.png")).thenReturn(enter);
        when(spriteLoader.getBufferedImage(path + "ores.png")).thenReturn(ores);

        // Try multiple times to increase chance of hitting both branches
        boolean sawSpawn = false;
        boolean sawSkip = false;
        for (int i = 0; i < 100; i++) {
            Scene scene = builder.createScene(path, playerModel);
            List<MineralModel> minerals = scene.getMineralModels();
            if (minerals.size() > 0) sawSpawn = true;
            if (minerals.size() < 400) sawSkip = true;
            if (sawSpawn && sawSkip) break;
        }
        assertTrue(sawSpawn);
        assertTrue(sawSkip);
    }
    @Test
    void testFindEntranceDefault() throws IOException {
        String path = "caves/cave0/";
        BufferedImage empty = new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB); // All transparent
        
        when(spriteLoader.getBufferedImage(anyString())).thenReturn(empty);
        
        Scene scene = builder.createScene(path, playerModel);
        verify(rigidBody).setPosition(argThat(v -> v.getX() == 15f && v.getY() == 15f));
    }

    @Test
    void testCreateCollidersWithLargeImage() throws IOException {
        String path = "caves/cave0/";
        BufferedImage structure = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        structure.setRGB(0, 0, 0xFF000000);
        structure.setRGB(16, 16, 0xFF000000);
        
        when(spriteLoader.getBufferedImage(path + "structure.png")).thenReturn(structure);
        when(spriteLoader.getBufferedImage(path + "enter.png")).thenReturn(new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB));
        when(spriteLoader.getBufferedImage(path + "ores.png")).thenReturn(new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB));
        
        Scene scene = builder.createScene(path, playerModel);
        assertEquals(2, scene.getBlockColliders().size());
    }
    @Test
    void testCreateCollidersExactSize() throws IOException {
        String path = "caves/cave0/";
        BufferedImage structure = new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB);
        structure.setRGB(0, 0, 0xFF000000);
        
        when(spriteLoader.getBufferedImage(path + "structure.png")).thenReturn(structure);
        when(spriteLoader.getBufferedImage(path + "enter.png")).thenReturn(new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB));
        when(spriteLoader.getBufferedImage(path + "ores.png")).thenReturn(new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB));
        
        Scene scene = builder.createScene(path, playerModel);
        assertEquals(1, scene.getBlockColliders().size());
    }
    @Test
    void testCreateMineralsProbabilityBoundary() throws IOException {
        String path = "caves/cave0/";
        BufferedImage ores = new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB);
        ores.setRGB(0, 0, 0xFFFFFFFF); // Solid at (0,0)
        
        when(spriteLoader.getBufferedImage(anyString())).thenReturn(new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB));
        when(spriteLoader.getBufferedImage(path + "ores.png")).thenReturn(ores);

        Random mockRandom = mock(Random.class);
        // probabilityOfMineralSpawn = 0.25f;
        // if (random.nextFloat() > probabilityOfMineralSpawn) { continue; }
        
        // Boundary test: nextFloat() returns exactly 0.25f. 
        // 0.25f > 0.25f is false, so it should NOT skip.
        when(mockRandom.nextFloat()).thenReturn(0.25f);
        when(mockRandom.nextInt(anyInt(), anyInt())).thenReturn(0);
        
        SceneBuilder localBuilder = new SceneBuilder(spriteLoader, mockRandom);
        Scene scene = localBuilder.createScene(path, playerModel);
        assertFalse(scene.getMineralModels().isEmpty(), "Should spawn mineral when nextFloat is exactly 0.25");

        // Test skip boundary: nextFloat() returns 0.250001f
        // 0.250001f > 0.25f is true, so it should skip.
        reset(mockRandom);
        when(mockRandom.nextFloat()).thenReturn(0.250001f);
        scene = localBuilder.createScene(path, playerModel);
        assertTrue(scene.getMineralModels().isEmpty(), "Should skip mineral when nextFloat is > 0.25");
    }

    @Test
    void testCreateMineralsShiftAndProbability() throws IOException {
        String path = "caves/cave0/";
        BufferedImage ores = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        // Alpha must be non-zero for (image.getRGB(x, y) >> 24) != 0
        // We use a color that has a negative value when interpreted as int,
        // to test the signed vs unsigned shift if PIT mutates it.
        // 0xFFFDFE89 is -131447 (negative)
        int colorSolid = 0xFFFDFE89; 
        ores.setRGB(0, 0, colorSolid);
        
        when(spriteLoader.getBufferedImage(anyString())).thenReturn(new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB));
        when(spriteLoader.getBufferedImage(path + "ores.png")).thenReturn(ores);

        boolean sawSpawn = false;
        boolean sawNoSpawn = false;
        
        for (int i = 0; i < 500; i++) {
            Scene scene = builder.createScene(path, playerModel);
            if (!scene.getMineralModels().isEmpty()) {
                MineralModel m = scene.getMineralModels().get(0);
                assertEquals(com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.PointingDirection.DOWN, m.getDirection());
                sawSpawn = true;
            } else {
                sawNoSpawn = true;
            }
            if (sawSpawn && sawNoSpawn) break;
        }
        assertTrue(sawSpawn, "Should have spawned at least once in 500 tries (p=0.25)");
        assertTrue(sawNoSpawn, "Should have skipped spawning at least once in 500 tries (p=0.75)");

        // To kill "Replaced Shift Right with Shift Left" for (image.getRGB(x, y) >> 24) != 0
        // If it was << 24, 0xFFFDFE89 << 24 would be 0x89000000, which is still != 0.
        // We need a color where (C >> 24) != 0 but (C << 24) == 0.
        // This happens if the lowest byte is 0.
        // Example: 0xFFFDFE00. (0xFFFDFE00 >> 24) is 0xFF (non-zero).
        // (0xFFFDFE00 << 24) is 0x00000000 (zero).
        BufferedImage ores2 = new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB);
        ores2.setRGB(0, 0, 0xFFFDFE00);
        when(spriteLoader.getBufferedImage(path + "ores.png")).thenReturn(ores2);
        
        sawSpawn = false;
        for (int i = 0; i < 500; i++) {
            Scene scene = builder.createScene(path, playerModel);
            if (!scene.getMineralModels().isEmpty()) {
                sawSpawn = true;
                break;
            }
        }
        assertTrue(sawSpawn, "Should spawn when alpha is non-zero even if lowest bits are zero");
    }
}
