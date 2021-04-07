import java.util.Comparator;
/**
 * This class sorts the listing by the field: host_name
 *
 * @author Sebastian Malos, Yusuf Yacoobali, Moonis Altaf and Kamil Duszak.
 * @version 1.0
 */
public class SortByHost implements Comparator<ShortPlace>
{
    /**
     * Each listing is compared to another and subsequently sorted in alphabetical order
     */
    public int compare(ShortPlace a, ShortPlace b){
        return a.getHost_name().compareTo(b.getHost_name());
    }
}