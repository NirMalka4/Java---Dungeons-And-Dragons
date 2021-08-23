package Enemy;

import Board.Board;
import Player.Mage;
import Player.Player;
import Reader.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class MonsterTest {
    private String path = "levels";
    private File file = new File(path);
    private String absolutePath = file.getAbsolutePath();
    private Reader reader = new Reader(path);
    private Board b = new Board(reader, 3);

    private Enemy m1 = new Monster('c', (char)9, (char)9, "Wright", 600, 30, 15, 100, 3, b);
    private Player p1 = new Mage('c', (char)7, (char)7, "Melisandre", 100, 5, 1, 300, 30, 15, 5, 6, b);

    private Enemy m2 = new Monster('c', (char)9, (char)9, "Wright", 600, 30, 15, 100, 3, b);
    private Player p2 = new Mage('c', (char)5, (char)5, "Melisandre", 100, 5, 1, 300, 30, 15, 5, 6, b);

    @BeforeEach
    void setUp() {
        assertTrue(absolutePath.endsWith("levels"));
    }

    @Test
    void inRange() {
        assertTrue(m1.InRange(p1));
        assertFalse(m2.InRange(p2));
    }

    @Test
    void getStatus() {
        assertEquals("""
                Wright\t\t\tHealth: 600/600\t\t\tAttack: 30\t\t\tDefense: 15\t\t\tExperience: 100
                \t\t\t   Vision Range: 3
                """, m1.getStatus());
        assertEquals("""
                        Melisandre\t\t\tHealth: 100/100\t\t\tAttack: 5\t\t\tDefense: 1\t\t\tLevel: 1
                        \t\t\tExperience: 0/50\t\t\tMana: 75/300\t\t\tSpell Power: 15
                        """
                , p1.getStatus());
    }
}