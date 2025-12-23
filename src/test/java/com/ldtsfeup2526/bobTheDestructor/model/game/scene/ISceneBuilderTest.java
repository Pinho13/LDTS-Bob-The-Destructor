package com.ldtsfeup2526.bobTheDestructor.model.game.scene;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ISceneBuilderTest {
    @Test
    void testInterface() {
        ISceneBuilder builder = (caveFilePath, playerModel) -> null;
        assertNotNull(builder);
    }
}
