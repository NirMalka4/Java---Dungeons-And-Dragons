package GameManager;

import java.io.IOException;

public class Executor {
    public static void main(String[] args) throws IOException {
        GameManager gm = new GameManager(args[0]);
        gm.run();
    }
}
