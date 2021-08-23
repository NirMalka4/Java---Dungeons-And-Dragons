package Player;
import Board.*;
import Enemy.*;
import java.util.List;

public class Mage extends Player
{

    private int manaPool, manaAmount, manaCost, spellPower, hitsCount, abilityRange;

    public int getManaAmount() {
        return manaAmount;
    }

    public Mage(char c, int i, int j, String name, int healthPool, int attackPoints, int defensePoints
    , int manapool, int manacost, int spellpower, int hitscount, int abilityrange, Board b)
    {
        super(c, i, j, name, healthPool, attackPoints, defensePoints,b);
        this.manaPool = manapool;
        this.manaAmount = manapool/4;
        this.manaCost = manacost;
        this.spellPower = spellpower;
        this.hitsCount = hitscount;
        this.abilityRange = abilityrange;
    }



    @Override
    protected MessageCallback cast()
    {
        return canApplyCast() ? applyCast() : denyCast();
    }

    @Override
    protected boolean canApplyCast()
    {
        return manaAmount > manaCost;
    }

    @Override
    protected MessageCallback applyCast()
    {
        manaAmount -= manaCost;
        int hits = 0;
        var report = new StringBuilder();
        report.append(_name).append(" cast Blizzard\n");
        List<Enemy> opponents = board.enemiesInRange(abilityRange); // Find all enemies in range
        int length = opponents.size();
        boolean attackOccured = false;
        while (hits < hitsCount & !opponents.isEmpty())
        {
            Enemy randomEnemy = opponents.get(random.nextInt(length)); // Choose random enemy
            injury(spellPower, randomEnemy, report);
            if(randomEnemy.isDeath())
            {
                proceedEnemyDied(report, randomEnemy);
                opponents.remove(randomEnemy);
                length = opponents.size();
            }
            hits++;
            attackOccured = true;
        }
        if(!attackOccured)
            report.append(String.format("There is no enemy within %s range: %d.\n", _name, abilityRange));
        return ()-> printer.print(report.toString());
    }

    @Override
    protected MessageCallback denyCast()
    {
        return ()->printer.print(String.format("You must wait until enough mana is reached. missing: %s.\n", manaCost-manaAmount));
    }

    @Override
    public void Tick()
    {
        manaAmount = Math.min(manaPool, manaAmount + level);
    }


    @Override
    public String getStatus()
    {
        return String.format("""
                        %s\t\t\tHealth: %d/%d\t\t\tAttack: %d\t\t\tDefense: %d\t\t\tLevel: %d
                        \t\t\tExperience: %d/%d\t\t\tMana: %d/%d\t\t\tSpell Power: %d
                        """,
                _name, _health.getAmount(), _health.getPool(), _attackPoints, _defensePoints, level, experience, levelUpFactor*level,
                manaAmount, manaPool, spellPower);
    }

    @Override
    protected void levelUp()
    {
        super.levelUp();
        manaPool += 25 * level;
        manaAmount = Math.min(manaAmount + (manaPool / 4), manaPool);
        spellPower += 10 * level;
    }

    @Override
    protected String getEnemyExp(int enemyExp)
    {
        if(enemyExp>0)
            experience+=enemyExp;
        if(isLevelUp())
        {
            int prevPool = _health.getPool(), prevAttack= _attackPoints, prevDefence=_defensePoints;
            int prevManaPool = manaPool, prevSpellPower= spellPower;
            super.levelUp();
            manaPool += 25*level;
            manaAmount = Math.min(manaAmount + (manaPool/4), manaPool);
            spellPower += 10*level;
            int poolDiff = _health.getPool() - prevPool;
            int attackDiff = _attackPoints - prevAttack;
            int defenceDiff = _defensePoints - prevDefence;
            int manaDiff = manaPool - prevManaPool;
            int spDiff = spellPower - prevSpellPower;
            return String.format("%s reached level %d: +%d Health, +%d Attack, +%d Defence +%d maximum mana, +%d spell power\n"
                    , _name, level, poolDiff, attackDiff, defenceDiff, manaDiff, spDiff);
        }
        else
            return "";
    }

}
