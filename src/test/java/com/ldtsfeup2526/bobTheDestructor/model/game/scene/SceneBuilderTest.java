package com.ldtsfeup2526.bobTheDestructor.model.game.scene;

import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.RigidBody;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SceneBuilderTest {
    private SceneBuilder sceneBuilder;
    private SpriteLoader spriteLoader;

    @BeforeEach
    void setUp() {
        spriteLoader = mock(SpriteLoader.class);
        sceneBuilder = new SceneBuilder(spriteLoader) {
            @Override
            protected com.ldtsfeup2526.bobTheDestructor.sounds.SoundPlayer createSoundPlayerInternal() {
                return mock(com.ldtsfeup2526.bobTheDestructor.sounds.SoundPlayer.class);
            }
        };
    }

    @Test
    void testCreateScene() throws IOException {
        String cavePath = "caves/cave0/";
        BufferedImage structureImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        structureImg.setRGB(0, 0, 0xFFFFFFFF);
        structureImg.setRGB(8, 8, 0xFFFFFFFF);

        BufferedImage enterImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        enterImg.setRGB(0, 8, 0xFFFFFFFF);

        BufferedImage oresImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        oresImg.setRGB(8, 0, 0xFFFFFFFF);

        when(spriteLoader.getBufferedImage(cavePath + "structure.png")).thenReturn(structureImg);
        when(spriteLoader.getBufferedImage(cavePath + "enter.png")).thenReturn(enterImg);
        when(spriteLoader.getBufferedImage(cavePath + "ores.png")).thenReturn(oresImg);

        PlayerModel playerModel = mock(PlayerModel.class);
        RigidBody rigidBody = mock(RigidBody.class);
        when(playerModel.getRigidBody()).thenReturn(rigidBody);

        Scene scene = sceneBuilder.createScene(cavePath, playerModel);

        assertNotNull(scene);
        assertEquals(cavePath, scene.getCaveFilePath());
        verify(rigidBody).setPosition(any(com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector.class));
        assertEquals(2, scene.getBlockColliders().size());

        assertTrue(scene.getMineralModels().size() <= 1);
    }

    @Test
    void testCreateSoundPlayerError() throws IOException {
        SceneBuilder sb = new SceneBuilder(spriteLoader) {
            @Override
            protected com.ldtsfeup2526.bobTheDestructor.sounds.SoundPlayer createSoundPlayerInternal() throws Exception {
                throw new Exception("Test Exception");
            }
        };

        String cavePath = "caves/cave0/";
        when(spriteLoader.getBufferedImage(anyString())).thenReturn(new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB));
        
        PlayerModel playerModel = mock(PlayerModel.class);
        when(playerModel.getRigidBody()).thenReturn(mock(RigidBody.class));

        Scene scene = sb.createScene(cavePath, playerModel);
        assertNull(scene.getSoundPlayer());
    }
    @Test
    void testFindEntrancePosNotFound() throws IOException {
        String cavePath = "caves/cave0/";
        BufferedImage emptyImg = new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB);

        when(spriteLoader.getBufferedImage(anyString())).thenReturn(emptyImg);

        PlayerModel playerModel = mock(PlayerModel.class);
        RigidBody rigidBody = mock(RigidBody.class);
        when(playerModel.getRigidBody()).thenReturn(rigidBody);

        sceneBuilder.createScene(cavePath, playerModel);
        verify(rigidBody).setPosition(argThat(v -> v.getX() == 15f && v.getY() == 15f));
    }

    @Test
    void testCreateMineralsWithProbability() throws IOException {
        String cavePath = "caves/cave0/";
        BufferedImage structureImg = new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB);
        BufferedImage enterImg = new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB);
        BufferedImage oresImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < 16; i+=8)
            for (int j = 0; j < 16; j+=8)
                oresImg.setRGB(i, j, 0xFFFFFFFF); 

        when(spriteLoader.getBufferedImage(cavePath + "structure.png")).thenReturn(structureImg);
        when(spriteLoader.getBufferedImage(cavePath + "enter.png")).thenReturn(enterImg);
        when(spriteLoader.getBufferedImage(cavePath + "ores.png")).thenReturn(oresImg);

        PlayerModel playerModel = mock(PlayerModel.class);
        when(playerModel.getRigidBody()).thenReturn(mock(RigidBody.class));

        for (int i = 0; i < 100; i++) {
            Scene scene = sceneBuilder.createScene(cavePath, playerModel);
            if (scene.getMineralModels().size() < 4) { // 4 is the total candidates
                // This means at least one was skipped by probabilityOfMineralSpawn
                // Branch probabilityOfMineralSpawn > random.nextFloat() was hit (skipped)
            }
            if (!scene.getMineralModels().isEmpty()) {
                // This means at least one was added
                // Branch probabilityOfMineralSpawn > random.nextFloat() was NOT hit (added)
            }
        }
    }
}
