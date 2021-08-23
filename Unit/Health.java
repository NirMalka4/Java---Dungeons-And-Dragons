package Unit;

public class Health
{
    private int pool, amount;

    public Health(int n)
    {
        pool = n;
        amount = n;
    }

    public void heal(int n)
    {
        amount = Math.min(amount + n, pool);
    }

    public void setDamage(int d)
    {
        amount -=d;
        if (amount < 0)
            amount = 0;
    }

    public boolean isAlive()
    {
        return amount>0;
    }

    public boolean isDead()
    {
        return amount <= 0;
    }

    public void levelUp (int level)
    {
        pool+=10*level;
        amount = pool;
    }

    public void increasePool(int inc)
    {
        pool += inc;
    }


    public int getAmount(){
        return amount;
    }

    public int getPool()
    {
        return pool;
    }



}
