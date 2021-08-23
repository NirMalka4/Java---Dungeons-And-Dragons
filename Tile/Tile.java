package Tile;
import Board.*;

// Parent class
// Implement either Visitor and Observable pattern
// Visitor -> Extended class apply their predefined logic when visit a Tile object.
// Observable -> Each Tile hold a reference to its subject- Board, to update its state when required.
public abstract class Tile implements Visitor, Visited
{
    protected char tile;
    protected int i, j;
    protected Board board;
    protected static final Printer printer = PrinterImpl.getInstance();

    public Tile(char c, int i, int j, Board board)

    {
        tile = c;
        this.i = i;
        this.j = j;
        this.board = board;
    }

    public void swap(Tile other)
    {
        int i_other = other.i, j_other = other.j;
        other.i = i;
        other.j = j;
        i = i_other;
        j = j_other;
        board.swap(this, other);
    }

    public int getRow(){return i;}

    public int getColumn(){return j;}

    public void setLocation(int i, int j)
    {
        this.i = i;
        this.j = j;
    }

    public int range(Tile other)
    {
        return (int) Math.sqrt(Math.pow((i - other.getRow()), 2) + Math.pow((j - other.getColumn()), 2));
    }

    public String toString()
    {
        return String.valueOf(tile);
    }
}
