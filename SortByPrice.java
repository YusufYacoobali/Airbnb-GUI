import java.util.Comparator;
/**
 * This class sorts the listing by the field: price
 *
 * @author Sebastian Malos, Yusuf Yacoobali, Moonis Altaf and Kamil Duszak.
 * @version 1.0
 */
public class SortByPrice implements Comparator<ShortPlace>
{
    /**
     * Each listing is compared to another and subsequently sorted in order from cheapest to most expensive
     */
    public int compare(ShortPlace a, ShortPlace b){
        return a.getPrice() - b.getPrice();
    }
}