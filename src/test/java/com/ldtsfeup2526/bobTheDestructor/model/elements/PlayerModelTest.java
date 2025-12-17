package com.ldtsfeup2526.bobTheDestructor.model.elements;

import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.Scene;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerModelTest {
    private PlayerModel player;

    @BeforeEach
    void setUp() {
        player = new PlayerModel(new Position(1, 2));
        Scene scene = new Scene("caves/cave0/", player, List.of());
        scene.setBlockColliders(List.of());
    }

    @Test
    void playerGetPositionTest() {
        assertEquals(1, player.getPosition().getX());
        assertEquals(2, player.getPosition().getY());
    }

    @Test
    void playerSetPositionTest() {
        player.setPosition(new Position(3, 4));
        assertEquals(3, player.getPosition().getX());
        assertEquals(4, player.getPosition().getY());
    }

    @Test
    void moveRightEventuallyChangesXAfterUpdatesTest() {
        int startX = player.getPosition().getX();

        player.moveRight();
        for (int i = 0; i < 6; i++) {
            player.update();
        }

        assertTrue(player.getPosition().getX() > startX);
    }

    @Test
    void moveLeftEventuallyChangesXAfterUpdatesTest() {
        int startX = player.getPosition().getX();

        player.moveLeft();
        for (int i = 0; i < 6; i++) {
            player.update();
        }

        assertTrue(player.getPosition().getX() < startX);
    }
}
