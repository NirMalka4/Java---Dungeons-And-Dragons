package Player;
import Board.*;
import Tile.*;
import Unit.*;
import Enemy.*;
import java.util.List;



public abstract class Player extends Unit
{
    protected int level = 1, experience = 0;
    protected final int levelUpFactor = 50, apFactor = 4;

    public Player(char c, int i, int j, String name, int healthPool, int attackPoints, int defensePoints, Board b)
    {
        super(c, i, j, name, healthPool, attackPoints, defensePoints, b);
    }

    // Cast player special ability
    // return the battle report
    protected abstract MessageCallback cast();

    protected abstract boolean canApplyCast();

    protected abstract MessageCallback applyCast();

    protected abstract MessageCallback denyCast();

    // Update relevant field at each extended class, according to the assignment description.
    public abstract void Tick();

    protected boolean isLevelUp()
    {
        return experience >= levelUpFactor*level;
    }

    protected void levelUp()
    {
        experience = 0;
        level++;
        _health.levelUp(level);
        _attackPoints+=apFactor*level;
        _defensePoints+=level;
    }

    // Update relevant fields at each extended class, according to the assignment description.
    protected abstract String getEnemyExp(int enemyExp);

    // Conduct predefined action according to the user input.
    // Param 'move' is verified at the Service Layer.
    public MessageCallback act(String move)
    {
        MessageCallback act;
        switch (move)
        {
            case "a" -> act = board.getTile(i, j - 1).accept(this);
            case "s" -> act = board.getTile(i + 1, j).accept(this);
            case "d" -> act = board.getTile(i, j + 1).accept(this);
            case "w" -> act = board.getTile(i - 1, j).accept(this);
            case "e" -> act = cast();
            default -> act = () -> {};
        };
        Tick();
        return act;
    }


    @Override
    public MessageCallback accept(Tile visitor)
    {
        return visitor.visit(this);
    }

    @Override
    public MessageCallback visit(Empty empty)
    {
        swap(empty);
        return ()-> {};
    }

    @Override
    public MessageCallback visit(Wall wall)
    {
        return ()->printer.print(String.format("Sorry, even %s can't go through wall\n", _name));
    }

    @Override
    public MessageCallback visit(Player player)
    {
        return ()->{};
    }

    protected void proceedEnemyDied(StringBuilder builder, Enemy enemy)
    {
        if(enemy.isDeath())
        {
            enemy.die(); // notify param 'enemy' to remove it self from the board game matrix and enemies list
            // attach detailed report which will be presented at the Service module.
            builder.append(String.format("%s died. %s gained %d experience\n", enemy.getName(), _name, enemy.getExperience()));
            builder.append(getEnemyExp(enemy.getExperience()));
        }
    }

    @Override
    public MessageCallback visit(Enemy enemy)
    {
        var builder = new StringBuilder();
        var report = engage(enemy);
        builder.append(report);
        if(enemy.isDeath())
            enemy.swap(this);
        proceedEnemyDied(builder, enemy);
        return ()->printer.print(builder.toString());
    }

    @Override
    public void die()
    {
        if(isDeath())
            tile='X';
    }

}
