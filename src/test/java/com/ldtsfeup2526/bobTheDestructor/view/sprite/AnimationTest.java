package com.ldtsfeup2526.bobTheDestructor.view.sprite;

import com.ldtsfeup2526.bobTheDestructor.view.Animation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AnimationTest {
    @Test
    void testUpdateLoop() {
        Sprite s1 = mock(Sprite.class);
        Sprite s2 = mock(Sprite.class);
        Animation anim = new Animation("test", new Sprite[]{s1, s2}, 0.1, true);
        
        assertEquals(0, anim.getFrame());
        anim.update(0.05);
        assertEquals(0, anim.getFrame());
        anim.update(0.06);
        assertEquals(1, anim.getFrame());
        anim.update(0.1);
        assertEquals(0, anim.getFrame());
    }

    @Test
    void testUpdateNoLoopBoundary() {
        Sprite s1 = mock(Sprite.class);
        Sprite s2 = mock(Sprite.class);
        Animation anim = new Animation("test", new Sprite[]{s1, s2}, 0.1, false);
        
        anim.update(0.1);
        assertEquals(1, anim.getFrame());
        assertTrue(anim.isFinished());

        anim.update(10.0);
        assertEquals(1, anim.getFrame());
        assertTrue(anim.isFinished());
    }

    @Test
    void testCooldownLoop() {
        Sprite s1 = mock(Sprite.class);
        Sprite s2 = mock(Sprite.class);
        Animation anim = new Animation("test", new Sprite[]{s1, s2}, 0.1, true);
        anim.setCooldownTime(0.5);
        
        anim.update(0.3);
        assertEquals(0, anim.getFrame());
        
        anim.update(0.2);
        assertEquals(0, anim.getFrame());
        
        anim.update(0.11);
        assertEquals(1, anim.getFrame());
        
        anim.update(0.1);
        assertEquals(0, anim.getFrame());
        
        anim.update(0.4);
        assertEquals(0, anim.getFrame());
    }

    @Test
    void testCopyAndStop() {
        Sprite s1 = mock(Sprite.class);
        Animation anim = new Animation("test", new Sprite[]{s1}, 0.1, true);
        anim.setCooldownTime(0.5);
        anim.update(0.2);
        anim.stop();
        assertEquals(0, anim.getFrame());
        
        Animation copy = anim.copy();
        assertEquals("test", copy.getAnimName());
        assertEquals(1, copy.getSprites().length);
        assertEquals(0.5, copy.getCooldownTime());
    }

    @Test
    void testUpdateNoLoopFinished() {
        Sprite s1 = mock(Sprite.class);
        Sprite s2 = mock(Sprite.class);
        Animation anim = new Animation("test", new Sprite[]{s1, s2}, 0.1, false);
        
        anim.update(0.1);
        assertEquals(1, anim.getFrame());
        assertTrue(anim.isFinished());

        anim.update(0.1);
        assertEquals(1, anim.getFrame());
        assertTrue(anim.isFinished());
    }

    @Test
    void testUpdateWithDifferentDelta() {
        Sprite s1 = mock(Sprite.class);
        Sprite s2 = mock(Sprite.class);
        Animation anim = new Animation("test", new Sprite[]{s1, s2}, 0.1, true);
        
        anim.update(0.25);
        assertEquals(0, anim.getFrame());
        
        anim.update(0.1);
        assertEquals(1, anim.getFrame());
    }
    @Test
    void testUpdateWithLargeDelta() {
        Sprite s1 = mock(Sprite.class);
        Sprite s2 = mock(Sprite.class);
        Animation anim = new Animation("test", new Sprite[]{s1, s2}, 0.1, false);
        
        anim.update(1.0); // Way beyond end
        assertEquals(1, anim.getFrame());
        assertTrue(anim.isFinished());
    }
    @Test
    void testUpdateLoopExact() {
        Sprite s1 = mock(Sprite.class);
        Sprite s2 = mock(Sprite.class);
        Animation anim = new Animation("test", new Sprite[]{s1, s2}, 0.1, true);
        
        anim.update(0.2); // elapsedTime becomes 0.2, currentFrame becomes 2. 2 >= 2. loop is true.
        // elapsedTime = 0, currentFrame = 0, currentCooldownTime = 0.
        assertEquals(0, anim.getFrame());
        assertEquals(0, anim.getCooldownTime());
    }
}
