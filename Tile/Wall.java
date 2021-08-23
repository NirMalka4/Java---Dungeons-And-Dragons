package Tile;

import Board.*;
import Enemy.*;
import Player.*;

public class Wall extends Tile
{

    public Wall(char c, int i, int j, Board b)
    {
        super(c, i, j, b);
    }

    @Override
    public MessageCallback accept(Tile visitor) {
        return visitor.visit(this);
    }

    @Override
    public MessageCallback visit(Empty empty) {
        return null;
    }

    @Override
    public MessageCallback visit(Wall wall) {
        return null;
    }

    @Override
    public MessageCallback visit(Player player) {
        return null;
    }

    @Override
    public MessageCallback visit(Enemy enemy) {
        return null;
    }

}
