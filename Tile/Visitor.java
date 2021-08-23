package Tile;

import Board.MessageCallback;
import Enemy.Enemy;
import Player.Player;

public interface Visitor
{
    MessageCallback visit (Empty empty);
    MessageCallback visit (Wall wall);
    MessageCallback visit (Player player);
    MessageCallback visit (Enemy enemy);
}
