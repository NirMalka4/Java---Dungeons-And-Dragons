package Reader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

// This class is responsible to handle all content of the path directory given as command line argument.
// That is load each level when required.
public class Reader
{
    private File _dir, _currentFile;
    private Iterator<File>  _dirIterator;
    private Scanner _fileReader;

    public Reader(String path)
    {
        init(path);
    }

    private void init(String path)
    {
        _dir = new File(path);
        try
        {
            File [] _levels = _dir.listFiles();
            assert _levels != null;
            _dirIterator = Arrays.stream(_levels).iterator();
            loadNextLevel();
        }
        catch(FileNotFoundException e)
        {
            System.out.printf("Open file at path: %s has been failed since: %s.\n", path, e.toString());
            e.printStackTrace();
        }
    }

    public boolean hasNext()
    {
        return _fileReader.hasNext();
    }

    public String Next()
    {
        return hasNext() ? _fileReader.nextLine() : null;
    }

    public void setPath(String newPath)
    {
        init(newPath);
    }

    public long size()
    {
        return _currentFile.length();
    }

    public boolean hasNextLevel()
    {
        return _dirIterator.hasNext();
    }

    public void loadNextLevel() throws FileNotFoundException {
        if(hasNextLevel())
        {
            _currentFile = _dirIterator.next();
            _fileReader = new Scanner(_currentFile);
        }
    }
}
