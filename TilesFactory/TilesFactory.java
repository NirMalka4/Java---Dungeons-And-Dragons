package TilesFactory;
import Board.*;
import Player.Player;
import Tile.*;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import Player.*;
import Enemy.*;

// Factory for Dungeons and Dragons characters
// Implemented as singelton
public class TilesFactory
{

    private final char PLAYER_SYMBOL = '@';
    private static final Printer printer = PrinterImpl.getInstance();
    private Map<Integer, Supplier<Player>> players;
    private Map<Character, Supplier<Empty>> empties;
    private Map<Character, Supplier<Wall>> walls;
    private Map<Character, Supplier<Enemy>> enemies;


    private static class TileFactoryHolder
    {
        private static TilesFactory instance = new TilesFactory();
    }


    private TilesFactory()
    {

        /*
         * Each predefined symbol will be used as a key at its compatible map.
         * Its value will hold a reference to a Supplier of the entity this symbol represents.
         * for example: 's' represent Lannister Soldier entity.
         */

        //Players
        players = new TreeMap<>();
        players.put(1, (c, i, j, b)-> new Warrior(c, i, j, "Jon Snow", 300, 30, 4, 3, b));
        players.put(2, (c, i, j, b)-> new Warrior(c, i, j, "The Hound", 400, 20, 6, 5, b));
        players.put(3, (c, i, j, b)-> new Mage(c, i, j, "Melisandre", 100, 5, 1, 300, 30, 15, 5, 6, b));
        players.put(4, (c, i, j, b)-> new Mage(c, i, j, "Thoros of Myr", 250, 25, 4, 150, 20, 20, 3, 4, b));
        players.put(5, (c, i, j, b)-> new Rogue(c, i, j, "Arya Stark", 150, 40, 2, 20, b));
        players.put(6, (c, i, j, b)-> new Rogue (c, i, j, "Bronn", 250, 35, 3, 50, b));
        //Empty
        empties = new HashMap<>();
        empties.put('.', (c, i, j, b)-> new Empty(c, i, j, b));
        //Wall
        walls = new HashMap<>();
        walls.put('#', (c, i, j, b)-> new Wall(c, i, j, b));
        //Monsters
        enemies = new HashMap<>();
        enemies.put('s', (c, i, j, b)-> new Monster(c, i, j, "Lannister Soldier", 80, 8, 3, 25, 3, b));
        enemies.put('k', (c, i, j, b)-> new Monster (c, i, j, "Lannister Knight", 200, 14, 8, 50, 4, b));
        enemies.put('q', (c, i, j, b)-> new Monster(c, i, j, "Queen Guard", 400, 20, 15, 100, 5, b));
        enemies.put('z', (c, i, j, b)-> new Monster (c, i, j, "Wright", 600, 30, 15, 100, 3, b));
        enemies.put('b', (c, i, j, b)-> new Monster (c, i, j, "Bear Wright", 1000, 75, 30, 250, 4, b));
        enemies.put('g', (c, i, j, b)-> new Monster (c, i, j, "Giant Wright", 1500, 100, 40, 500, 5, b));
        enemies.put('w', (c, i, j, b)-> new Monster (c, i, j, "White Walker", 2000, 150, 50, 1000, 6, b));
        enemies.put('M', (c, i, j, b)-> new Monster (c, i, j, "The Mountain", 1000, 60, 25, 500, 6, b));
        enemies.put('C', (c, i, j, b)-> new Monster (c, i, j, "Queen Cersei", 100, 10, 10, 1000, 1, b));
        enemies.put('K', (c, i, j, b)-> new Monster (c, i, j, "Night King", 5000, 300, 150, 5000, 8, b));
        //Traps
        enemies.put('B',(c, i, j, b)-> new Trap(c, i, j, "Bonus Trap", 1, 1, 1, 250, 1, 5, b));
        enemies.put('Q',(c, i, j, b)-> new Trap(c, i, j, "QueenTrap", 250, 50, 10, 100, 3, 7, b));
        enemies.put('D',(c, i, j, b)-> new Trap(c, i, j, "DeathTrap", 500, 100, 20, 250, 1, 10, b));

    }

    public static TilesFactory getInstance(){return TileFactoryHolder.instance;}

    public Empty createEmpty(char c, int i, int j, Board b)
    {
        return empties.get(c).get(c, i, j, b);
    }

    public Wall createWall(char c, int i, int j, Board b)
    {
        return walls.get(c).get(c, i, j, b);
    }

    public Player createPlayer(int c, int i, int j, Board b)
    {
        return players.get(c).get(PLAYER_SYMBOL, i, j, b);
    }

    public Enemy createEnemy(char c, int i, int j, Board b)
    {
        return enemies.get(c).get(c, i, j, b);
    }


    // Return a callback which print all available players.
    public MessageCallback showPlayers()
    {
        return ()->
        {
            int i = 1;
            for (Supplier<Player> sp : players.values())
                printer.print(String.format("%d) %s\n", i++, sp.get('@', 1, 1, null).getStatus()));
        };
    }

}
