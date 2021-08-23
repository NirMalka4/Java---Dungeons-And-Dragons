package Player;

import Board.Board;
import GameManager.GameManager;
import Reader.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class WarriorTest {

    private String path = "levels";
    private GameManager g = new GameManager(path);
    private File file = new File(path);
    private String absolutePath = file.getAbsolutePath();
    private Reader reader = new Reader(path);
    private Board b = new Board(reader, 1);
    private Warrior p1 = new Warrior('c', (char)5, (char)5, "Jon Snow", 300, 30, 4, 3, b);

    @BeforeEach
    void setUp() {
            assertTrue(absolutePath.endsWith("levels"));
    }

    @Test
    void canApplyCast() {
        assertTrue(p1.canApplyCast());
        p1.applyCast();
        assertFalse(p1.canApplyCast());
    }

    @Test
    void tick() {
        assertEquals(0, p1.getRemainingCooldown());
        p1.applyCast();
        assertEquals(4, p1.getRemainingCooldown());
        p1.Tick();
        assertEquals(3, p1.getRemainingCooldown());
        p1.Tick();
        assertEquals(2, p1.getRemainingCooldown());
        p1.Tick();
        assertEquals(1, p1.getRemainingCooldown());
    }

    @Test
    void getEnemyExp() {
        p1.getEnemyExp(20);
        assertEquals(20, p1.experience);
    }

    @Test
    void getStatus() {
        assertEquals("Jon Snow\t\t\tHealth: 300/300\t\t\tAttack: 30\t\t\tDefense: 4\t\t\tLevel: 1\n" +
                "\t\t\tExperience: 0/50\t\t\tCooldown: 0/3\n", p1.getStatus());
    }
}