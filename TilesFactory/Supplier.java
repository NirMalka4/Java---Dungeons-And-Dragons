package TilesFactory;

import Board.Board;
import Tile.Tile;

public interface Supplier<T> {
    T get(char c, int i, int j, Board b);
}
