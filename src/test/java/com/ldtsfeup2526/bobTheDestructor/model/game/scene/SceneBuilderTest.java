package com.ldtsfeup2526.bobTheDestructor.model.game.scene;

import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.RigidBody;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

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
    void testCreateMineralsProbability() throws IOException {
        String path = "caves/cave0/";
        BufferedImage ores = new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB);
        ores.setRGB(0, 0, 0xFFFFFFFF);
        
        when(spriteLoader.getBufferedImage(path + "ores.png")).thenReturn(ores);
        when(spriteLoader.getBufferedImage(path + "structure.png")).thenReturn(new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB));
        when(spriteLoader.getBufferedImage(path + "enter.png")).thenReturn(new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB));

        // We want to test both branches of "if (random.nextFloat() > probabilityOfMineralSpawn)"
        // probabilityOfMineralSpawn is 0.25
        
        boolean sawSpawn = false;
        boolean sawNoSpawn = false;
        
        for (int i = 0; i < 100; i++) {
            Scene scene = builder.createScene(path, playerModel);
            if (scene.getMineralModels().size() == 1) sawSpawn = true;
            else sawNoSpawn = true;
            if (sawSpawn && sawNoSpawn) break;
        }
        assertTrue(sawSpawn);
        assertTrue(sawNoSpawn);
    }
}
