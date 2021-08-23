package Unit;
import Board.Board;
import Tile.Tile;

import java.util.Random;

public abstract class Unit extends Tile
{
    protected String _name;
    protected Health _health;
    protected int _attackPoints, _defensePoints;
    protected static Random random = new Random();

    public Unit(char c, int i, int j, String name, int healthPool, int attackPoints, int defensePoints, Board board)
    {
        super(c, i, j, board);
        _name = name;
        _health = new Health(healthPool);
        _attackPoints = attackPoints;
        _defensePoints = defensePoints;
    }

    public String getName()
    {
        return _name;
    }


    public int generateRandomDefence()
    {
        return random.nextInt(_defensePoints+1);
    }


    public int generateRandomAttack()
    {
        return random.nextInt(_attackPoints+1);
    }

    protected String engage(Unit other)
    {
        StringBuilder report = new StringBuilder();
        report.append(String.format("%s engaged in combat with %s\n%s%s", getName(), other.getName(), getStatus(), other.getStatus()));
        int rattack = generateRandomAttack();
        report.append(String.format("%s rolled %d attack points\n", getName(), rattack));
        injury(rattack, other, report);
        return report.toString();
    }

    public abstract void die();

    // param attack - either random number or predefined (in case of special ability)
    protected void injury(int rattack, Unit opponent, StringBuilder report)
    {
        int rdefence = opponent.generateRandomDefence();
        int actualDamage = rattack - rdefence;
        report.append(String.format("%s rolled %d defence points\n", opponent.getName(), rdefence));
        if(actualDamage > 0)
        {
            opponent.setDamage(actualDamage);
            report.append(String.format("%s dealt %d damage to %s\n", getName(), actualDamage, opponent.getName()));
        }
    }

    public boolean isAlive()
    {
        return _health.isAlive();
    }

    public boolean isDeath() {return _health.isDead();}

    public void setDamage(int d){_health.setDamage(d);}

    public abstract String getStatus();



}
