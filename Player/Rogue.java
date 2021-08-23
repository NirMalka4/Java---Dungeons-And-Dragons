package Player;
import Unit.*;
import Enemy.*;

import java.util.ArrayList;
import java.util.List;
import Board.*;

public class Rogue extends Player
{
    private int cost, currentEnergy;
    private final int DEFAULT_ENERGY = 100, DEFAULT_RANGE = 2;

    public int getCurrentEnergy() {
        return currentEnergy;
    }

    public Rogue(char c, int i, int j, String name, int healthPool, int attackPoints, int defensePoints, int cost, Board b)
    {
        super(c, i, j, name, healthPool, attackPoints, defensePoints, b);
        this.cost = cost;
        currentEnergy = DEFAULT_ENERGY;
    }


    @Override
    protected MessageCallback cast()
    {
        return canApplyCast() ? applyCast() : denyCast();
    }

    @Override
    protected boolean canApplyCast()
    {
        return currentEnergy>=cost;
    }

    @Override
    protected MessageCallback applyCast()
    {
        var report = new StringBuilder();
        report.append(_name).append(" cast Fan Of Knives.\n");
        currentEnergy -= cost;
        List<Enemy> opponents = board.enemiesInRange(DEFAULT_RANGE);
        for (Enemy e: opponents)
            injury(generateRandomAttack(), e, report);
        return ()-> printer.print(report.toString());
    }

    @Override
    protected MessageCallback denyCast()
    {
        return ()->printer.print(String.format("You must wait until current energy reached at least %d\n.", cost));
    }

    @Override
    public void Tick()
    {
        currentEnergy = Math.min(currentEnergy + 10, DEFAULT_ENERGY);
    }

    @Override
    protected String getEnemyExp(int enemyExp)
    {
        if(enemyExp>0)
            experience+=enemyExp;
        if(isLevelUp())
    {
        int prevPool = _health.getPool(), prevAttack= _attackPoints, prevDefence=_defensePoints;
        super.levelUp();
        currentEnergy = DEFAULT_ENERGY;
        _attackPoints += 3*level;
        int poolDiff = _health.getPool() - prevPool;
        int attackDiff = _attackPoints - prevAttack;
        int defenceDiff = _defensePoints - prevDefence;

        return String.format("%s reached level %d: +%d Health, +%d Attack, +%d Defence\n", _name, level, poolDiff, attackDiff, defenceDiff);
    }
        else
            return "";
    }

    @Override
    public String getStatus()
    {
        return String.format("""
                        %s\t\t\tHealth: %d/%d\t\t\tAttack: %d\t\t\tDefense: %d\t\t\tLevel: %d
                        \t\t\tExperience: %d/%d\t\t\tCurrent Energy: %d/%d
                        """,
                _name, _health.getAmount(), _health.getPool(), _attackPoints, _defensePoints, level, experience, levelUpFactor*level,
                currentEnergy, DEFAULT_ENERGY);
    }

}
