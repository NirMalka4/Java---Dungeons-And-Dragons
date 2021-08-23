package Enemy;

import Board.*;
import Player.Player;
import Tile.Tile;

public class Trap extends Enemy
{

    private final int DEFAULT_TRAP_RANGE = 2;
    private int visibilityTime, invisibilityTime, ticks = 0;
    private boolean isVisible = true;
    public Trap(char c, int i, int j, String name, int healthPool, int attackPoints, int defensePoints,
                int exp, int vt, int ivt, Board b) {
        super(c, i, j, name, healthPool, attackPoints, defensePoints, exp, b);
        visibilityTime = vt;
        invisibilityTime = ivt;
    }

    @Override
    protected boolean InRange(Player player)
    {
        return range(player) < DEFAULT_TRAP_RANGE;
    }

    @Override
    public MessageCallback act(Player player)
    {
        isVisible = ticks < visibilityTime;
        ticks = (ticks == visibilityTime + invisibilityTime) ? 0 : ticks+1;
        return InRange(player) ? visit(player) : ()->{};
    }

    @Override
    public void swap(Tile other) {} // Trap does not participate in locations swap.

    @Override
    public String getStatus()
    {
        return String.format("""
                        %s\t\t\tHealth: %d/%d\t\t\tAttack: %d\t\t\tDefense: %d\t\t\tExperience: %d
                        """,
                _name, _health.getAmount(), _health.getPool(), _attackPoints, _defensePoints, experienceValue);
    }

    @Override
    public String toString()
    {
        return isVisible? String.valueOf(tile) : ".";
    }


}
