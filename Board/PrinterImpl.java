package Board;
import java.io.*;


/* Implemented as a Singelton, use to handle all printing to stdout socket. */

public class PrinterImpl implements Printer {

    private BufferedWriter log;
    private static class PrinterHolder
    {
        private static PrinterImpl instance = new PrinterImpl();
    }

    private PrinterImpl()
    {
        log = new BufferedWriter(new OutputStreamWriter(System.out));
    }

    public static PrinterImpl getInstance()
    {
        return PrinterHolder.instance;
    }

    @Override
    public void print(String msg)
    {
        try
        {
            log.write(msg);
            log.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
