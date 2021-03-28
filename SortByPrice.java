import java.util.Comparator;
/**
 * This class is sorts the listing by the field: price
 *
 * @author Yusuf Yacoobali
 * @version v1
 */
public class SortByPrice implements Comparator<ShortPlace>
{
    /**
     * Each listing is compared to another and subsequently sorted in order
     */
    public int compare(ShortPlace a, ShortPlace b){
        return a.getPrice() - b.getPrice();
    }
}