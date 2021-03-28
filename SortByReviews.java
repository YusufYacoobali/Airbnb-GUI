import java.util.Comparator;
/**
 * This class is sorts the listing by the field: numberOfReviews
 *
 * @author Yusuf Yacoobali
 * @version v1
 */
public class SortByReviews implements Comparator<ShortPlace>
{
    /**
     * Each listing is compared to another and subsequently sorted in order
     */
    public int compare(ShortPlace a, ShortPlace b){
        return a.getNumberOfReviews() - b.getNumberOfReviews();
    }
}