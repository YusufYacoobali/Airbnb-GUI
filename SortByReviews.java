import java.util.Comparator;
/**
 * This class sorts the listing by the field: numberOfReviews
 *
 * @author Sebastian Malos, Yusuf Yacoobali, Moonis Altaf and Kamil Duszak.
 * @version 1.0
 */
public class SortByReviews implements Comparator<ShortPlace>
{
    /**
     * Each listing is compared to another and subsequently sorted in order from least reviews to most
     */
    public int compare(ShortPlace a, ShortPlace b){
        return a.getNumberOfReviews() - b.getNumberOfReviews();
    }
}