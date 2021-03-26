import java.util.Comparator;
/**
 * Write a description of class SortByHost here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class SortByHost implements Comparator<ShortPlace>
{
    public int compare(ShortPlace a, ShortPlace b){
        return a.getHost_name().compareTo(b.getHost_name());
    }
}