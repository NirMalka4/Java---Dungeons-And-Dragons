package Unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HealthTest {

    private Health h;
    @BeforeEach
    void setUp() {
        h = new Health(10);
    }

    @Test
    void heal() {
        h.setDamage(5);
        assertEquals(5, h.getAmount());
        h.heal(5);
        assertEquals(10, h.getAmount());
    }

    @Test
    void setDamage() {
        h.setDamage(5);
        assertEquals(5, h.getAmount());
    }

    @Test
    void isAlive() {
        assertTrue(h.isAlive());
        h.setDamage(10);
        assertFalse(h.isAlive());
    }

    @Test
    void isDead() {
        assertFalse(h.isDead());
        h.setDamage(10);
        assertTrue(h.isDead());
    }

    @Test
    void levelUp() {
        h.levelUp(2);
        assertEquals(30, h.getPool());

    }

    @Test
    void increasePool() {
        h.increasePool(10);
        assertEquals(20, h.getPool());
    }
}