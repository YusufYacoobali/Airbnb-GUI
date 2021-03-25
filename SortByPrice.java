import java.util.Comparator;
/**
 * Write a description of class SortByPrice here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class SortByPrice implements Comparator<ShortPlace>
{
    public int compare(ShortPlace a, ShortPlace b){
        return a.getPrice() - b.getPrice();
    }
}