package Player;
import Board.*;
import Enemy.*;
import java.util.List;

public class Warrior extends Player
{

    private final int  abilityCooldown, DEFUALT_RANGE = 3;

    public int getRemainingCooldown() {
        return remainingCooldown;
    }

    private int remainingCooldown = 0;
    public Warrior(char c, int i, int j, String name, int healthPool, int attackPoints, int defensePoints, int abilityCooldown, Board b)
    {
        super(c, i, j, name, healthPool, attackPoints, defensePoints, b);
        this.abilityCooldown = abilityCooldown;
    }

    @Override
    protected boolean canApplyCast()
    {
        return remainingCooldown == 0;
    }

    private int abilityDamage(Enemy enemy)
    {
        return (int) (0.1 * _health.getPool()) - enemy.generateRandomDefence();
    }

    @Override
    protected MessageCallback applyCast()
    {
        var report = new StringBuilder();
        int prevHealthAmount = _health.getAmount();
        _health.heal(10 * _defensePoints + _health.getAmount());
        int healthDiff = _health.getAmount()-prevHealthAmount;
        report.append(String.format("%s used Avenger's Shield, healing for %d.\n", _name, healthDiff));
        List<Enemy> InRange = board.enemiesInRange(DEFUALT_RANGE);
        if(!InRange.isEmpty())
        {
            Enemy rEnemy = InRange.get(random.nextInt(InRange.size())); // Choose enemy randomly
            injury(abilityDamage(rEnemy) ,rEnemy, report);
            proceedEnemyDied(report, rEnemy);
        }
        else
            report.append(String.format("There is no enemy within %s range: %d.\n", _name, DEFUALT_RANGE));
        // add extra 1 since Tick() is called after and decrease 'remainingCooldown' field by one.
        remainingCooldown = abilityCooldown + 1;
        return ()->printer.print(report.toString());
    }

    @Override
    protected MessageCallback denyCast()
    {
        return ()->printer.print(String.format("You JUST used Avenger's Shield. Please wait %d turn.\n", (remainingCooldown + 1)));
    }

    @Override
    protected MessageCallback cast()
    {
        return canApplyCast() ? applyCast() : denyCast();
    }

    @Override
    public void Tick()
    {
        remainingCooldown = Math.max(remainingCooldown - 1, 0);
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
            remainingCooldown = 0;
            _health.increasePool(5*level);
            _attackPoints += 2 * level;
            _defensePoints += level;
            int poolDiff = _health.getPool() - prevPool;
            int attackDiff = _attackPoints - prevAttack;
            int defenceDiff = _defensePoints - prevDefence;

            return String.format("%s reached level %d: +%d Health, +%d Attack, +%d Defence\n",
                    _name, level, poolDiff, attackDiff, defenceDiff);
        }
        else
            return "";
    }

    // print current player state
    @Override
    public String getStatus()
    {
        return String.format("""
                        %s\t\t\tHealth: %d/%d\t\t\tAttack: %d\t\t\tDefense: %d\t\t\tLevel: %d
                        \t\t\tExperience: %d/%d\t\t\tCooldown: %d/%d
                        """,
                _name, _health.getAmount(), _health.getPool(), _attackPoints, _defensePoints, level, experience, levelUpFactor*level,
                remainingCooldown, abilityCooldown);
    }

}
