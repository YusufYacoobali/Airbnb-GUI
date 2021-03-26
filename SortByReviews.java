import java.util.Comparator;
/**
 * Write a description of class SortByReviews here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class SortByReviews implements Comparator<ShortPlace>
{
    public int compare(ShortPlace a, ShortPlace b){
        return a.getNumberOfReviews() - b.getNumberOfReviews();
    }
}