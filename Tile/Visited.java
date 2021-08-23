package Tile;

import Board.MessageCallback;

public interface Visited
{
    MessageCallback accept(Tile visitor);
}
