package Board;

import Player.Mage;
import Player.Player;
import Reader.Reader;
import Tile.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private String path = "levels";
    private File file = new File(path);
    private String absolutePath = file.getAbsolutePath();
    private Reader reader = new Reader(path);
    private Board b = new Board(reader, 3);
    private Player t1 = new Mage('@', 6, 6, "Melisandre", 100, 5, 1, 300, 30, 15, 5, 6, b);

    @BeforeEach
    void setUp() {
        assertTrue(absolutePath.endsWith("levels"));
    }

    @Test
    void cont() {
        assertTrue(b.getEnemies().size() > 0);
        assertFalse(b.getEnemies().isEmpty());
    }

    @Test
    void swap() {
        Tile t2 = new Empty('.', 5, 6, b);
        b.swap(t1, t2);
        assertEquals(6, t1.getRow());
    }

    @Test
    void playerIsAlive() {
        t1.isAlive();
        assertTrue(b.playerIsAlive());
    }

    @Test
    void getTile() {
        assertEquals("#", b.getTile(18, 7).toString());
        assertEquals(".", b.getTile(16, 7).toString());
    }
}
