package Enemy;

import Board.*;
import Player.Player;
import Unit.*;
import Tile.*;

public abstract class Enemy extends Unit
{

    protected int experienceValue;

    public Enemy(char c, int i, int j, String name, int healthPool, int attackPoints, int defensePoints, int exp, Board b)
    {
        super(c, i, j, name, healthPool, attackPoints, defensePoints, b);
        experienceValue = exp;
    }

    protected abstract boolean InRange(Player player);

    @Override
    public MessageCallback accept(Tile visitor)
    {
        return visitor.visit(this);
    }

    @Override
    public MessageCallback visit(Empty empty)
    {
        swap(empty);
        return ()->{};
    }

    @Override
    public MessageCallback visit(Wall wall)
    {
        return ()->{};
    }

    @Override
    public MessageCallback visit(Enemy enemy)
    {
        return ()->{};
    }

    public MessageCallback visit(Player player)
    {
        var builder = new StringBuilder();
        var report = engage(player);
        builder.append(report);
        if(player.isDeath())
        {
            player.die();
            builder.append(String.format("%s was killed by %s\nYou lost.\n", player.getName(), _name));
        }
        return ()->printer.print(builder.toString());
    }

    public abstract MessageCallback act(Player player);


    @Override
    public void die()
    {
        if(isDeath())
            board.removeEnemy(this);
    }

    public int getExperience(){return experienceValue;}

    @Override
    public abstract void swap(Tile other);

}
