package Board;
import Enemy.Enemy;
import Reader.Reader;
import Tile.*;
import TilesFactory.*;
import Player.*;
import java.util.ArrayList;
import java.util.List;

// This class is responsible to handle all tiles and transfer a report to the Service module at each game session.
// Implement the Observer pattern -> it is used as the subject of all Tiles (Observables).
// As such, at each game session it notify each Tile to apply its act.
public class Board
{
    private Tile[][] matrix;
    private int n, m; // n = row size, m = column size
    private final TilesFactory factory = TilesFactory.getInstance();
    private final Printer printer = PrinterImpl.getInstance();
    private Player player = null;
    private final int selectedPlayer;
    private Reader reader;

    private List<Enemy> enemies = new ArrayList<Enemy>();

    public List<Enemy> getEnemies() {
        return enemies;
    }

    private boolean isEmptySymbol(char c)
    {
        return c=='.';
    }

    private boolean isWallSymbol(char c)
    {
        return c=='#';
    }

    private boolean isPlayerSymbol(char c)
    {
        return c=='@';
    }

    private Tile setPlayer(int selectedPlayer, int i, int j)
    {
        if(player==null)
            player = factory.createPlayer(selectedPlayer, i, j, this);
        else
            player.setLocation(i,j);
        return player;
    }

    private Tile setEnemy(char c, int i, int j)
    {
        Enemy enemy = factory.createEnemy(c, i, j, this);
        enemies.add(enemy);
        return enemy;
    }

    // Remove param enemy from enemies list.
    // Replace param enemy at matrix with Empty instance.
    public void removeEnemy(Enemy enemy)
    {
        enemies.remove(enemy);
        int i = enemy.getRow(), j = enemy.getColumn();
        matrix[i][j]= factory.createEmpty('.', i, j, this );
    }

    /* for each row in input level<i>, create compatible row at matrix field. */
    public Board (Reader reader, int selectedPlayer)
    {
        this.reader = reader;
        this.selectedPlayer = selectedPlayer;
        proceedNextLevel();
    }

    public void proceedNextLevel()
    {
        boolean firstIter = true;
        int i = 0;
        while(reader.hasNext())
        {
            String line = reader.Next();
            if (firstIter) // get the level<i> dimensions - expected to be rectangle.
            {
                n = line.length();
                m = (int)(Math.floorDiv(reader.size(), n)+1);
                matrix = new Tile[m][n];
                firstIter = false;
            }
            for (int j = 0; j < n; j++)
            {
                char c = line.charAt(j);
                matrix[i][j] =  isEmptySymbol(c) ? factory.createEmpty(c, i, j, this) :
                                isWallSymbol(c) ? factory.createWall(c, i, j, this) :
                                isPlayerSymbol(c) ? setPlayer(selectedPlayer, i, j) :
                                setEnemy(c, i, j);
            }
            i++; // proceed to next row
        }
    }

    // verify whether game should be continued
    public boolean cont()
    {
        return player!= null && player.isAlive() & enemies!=null && !enemies.isEmpty();
    }

    public MessageCallback describe()
    {
        var builder = new StringBuilder();
        for(Tile[] row: matrix)
        {
            for(Tile elem: row)
            {
                if (elem == null)
                    break;
                builder.append(elem.toString());
            }
            builder.append('\n');
        }
        builder.append("\n");
        builder.append(player.getStatus());
        return ()->printer.print(builder.toString());
    }

    public void swap(Tile dst, Tile src)
    {
        matrix[dst.getRow()][dst.getColumn()]= dst;
        matrix[src.getRow()][src.getColumn()]= src;
    }

    public boolean playerIsAlive()
    {
        return player.isAlive();
    }
    public Tile getTile(int i, int j)
    {
        return matrix[i][j];
    }

    public List<Enemy> enemiesInRange(int rangeOf)
    {
        List<Enemy> playerEnemies = new ArrayList<>();
        for(Enemy e: enemies){
            if(player.range(e) < rangeOf)
                playerEnemies.add(e);
        }
        return playerEnemies;
    }

    // return session report includes all participates: player and enemies.
    public MessageCallback gameTick(String move)
    {
        var session = new ArrayList<MessageCallback>();
        MessageCallback callback = player.act(move);
        session.add(callback);
        if (player.isAlive())
        {
            for (Enemy enemy : enemies)
            {
                callback = enemy.act(player);
                session.add(callback);
            }
        }
        return ()->
        {
            for(MessageCallback report : session)
                report.execute();
        };
    }
}
