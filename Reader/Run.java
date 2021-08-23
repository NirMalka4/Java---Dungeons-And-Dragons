package Reader;

public class Run
{
    public static void main(String[] args)
    {
        Reader r = new Reader(args[0]);
        while(r.hasNext())
            System.out.println(r.Next());
        r.setPath(args[1]);
        while(r.hasNext())
            System.out.println(r.Next());
    }
}
