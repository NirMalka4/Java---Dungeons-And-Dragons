package Player;

import Board.Board;
import Reader.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class RogueTest {

    private String path = "levels";
    private File file = new File(path);
    private String absolutePath = file.getAbsolutePath();
    private Reader reader = new Reader(path);
    private Board b = new Board(reader, 5);
    private Rogue p1 = new Rogue('c', (char)5, (char)5, "Arya Stark", 150, 40, 2, 20, b);

    @BeforeEach
    void setUp() {
        assertTrue(absolutePath.endsWith("levels"));
    }

    @Test
    void canApplyCast() {
        assertTrue(p1.canApplyCast());
        for (int i = 1; i < 15; i++)
            p1.applyCast();
        assertFalse(p1.canApplyCast());
    }

    @Test
    void tick() {
        assertEquals(100, p1.getCurrentEnergy());
        p1.applyCast();
        assertEquals(80, p1.getCurrentEnergy());
        p1.Tick();
        assertEquals(90, p1.getCurrentEnergy());
        p1.Tick();
        assertEquals(100, p1.getCurrentEnergy());
    }

    @Test
    void getEnemyExp() {
        p1.getEnemyExp(20);
        assertEquals(20, p1.experience);
    }

    @Test
    void getStatus() {
        assertEquals("""
                Arya Stark\t\t\tHealth: 150/150\t\t\tAttack: 40\t\t\tDefense: 2\t\t\tLevel: 1
                \t\t\tExperience: 0/50\t\t\tCurrent Energy: 100/100
                """, p1.getStatus());
    }
}