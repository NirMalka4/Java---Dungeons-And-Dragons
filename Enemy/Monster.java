package Enemy;

import Board.*;
import Player.Player;
import Tile.Tile;

public class Monster extends Enemy
{
    private final int visionRange;

    public Monster(char c, int i, int j, String name, int healthPool, int attackPoints,
                   int defensePoints, int exp, int vr, Board b) {
        super(c, i, j, name, healthPool, attackPoints, defensePoints, exp, b);
        visionRange = vr;
    }

    @Override
    protected boolean InRange(Player player)
    {
        return range(player)<visionRange;
    }

    private MessageCallback actWithinRange(Player player)
    {

        Tile dest;
        int pi = player.getRow();
        int pj = player.getColumn();
        dest =  (i < pi) ? board.getTile(i+1,j) :
                (i > pi) ? board.getTile(i-1, j) :
                (j < pj) ? board.getTile(i, j+1) :
                board.getTile(i, j-1);
        return dest.accept(this);
    }

    private MessageCallback actRandom()
    {
        Tile dest;
        int direction = random.nextInt(5);
        dest = direction == 0 ? board.getTile(i,j-1) :
                direction == 1 ? board.getTile(i,j+1) :
                        direction == 2 ? board.getTile(i-1, j):
                                direction == 3 ? board.getTile(i+1, j) :
                                        null;
        if(dest!=null)
            return dest.accept(this);
        return ()->{};
    }

    @Override
    public MessageCallback act(Player player)
    {
        return player.isDeath() ? ()->{} :
                InRange(player) ? actWithinRange(player) :
                        actRandom();
    }

    @Override
    public void swap(Tile other)
    {
        other.swap(this);
    }

    @Override
    public String getStatus()
    {
        return String.format("""
                        %s\t\t\tHealth: %d/%d\t\t\tAttack: %d\t\t\tDefense: %d\t\t\tExperience: %d
                        \t\t\t   Vision Range: %d
                        """,
                _name, _health.getAmount(), _health.getPool(), _attackPoints, _defensePoints, experienceValue,visionRange);
    }

}
