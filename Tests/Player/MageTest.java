package Player;

import Board.Board;
import Reader.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class MageTest {
    private String path = "levels";
    private File file = new File(path);
    private String absolutePath = file.getAbsolutePath();
    private Reader reader = new Reader(path);
    private Board b = new Board(reader, 3);
    private Mage p1 = new Mage('c', (char)5, (char)5, "Melisandre", 100, 5, 1, 300, 30, 15, 5, 6, b);

    @BeforeEach
    void setUp() {
        assertTrue(absolutePath.endsWith("levels"));
    }

    @Test
    void canApplyCast() {
        assertTrue(p1.canApplyCast());
        p1.applyCast();
        p1.applyCast();
        p1.applyCast();
        assertFalse(p1.canApplyCast());
    }

    @Test
    void tick() {
        p1.Tick();
        assertEquals(76, p1.getManaAmount());
        for (int i = 0; i < 10; i++)
            p1.Tick();
        assertEquals(86, p1.getManaAmount());
    }

    @Test
    void getStatus() {
        // Tested in MonsterTest
    }

    @Test
    void levelUp() {
        p1.levelUp();
        assertEquals(2, p1.level);
        for (int i = 0; i < 10; i++)
            p1.levelUp();
        assertEquals(12, p1.level);
    }

    @Test
    void getEnemyExp() {
        p1.getEnemyExp(20);
        assertEquals(20, p1.experience);
    }
}