import java.util.Comparator;
/**
 * This class is sorts the listing by the field: host_name
 *
 * @author Yusuf Yacoobali
 * @version v1
 */
public class SortByHost implements Comparator<ShortPlace>
{
    /**
     * Each listing is compared to another and subsequently sorted in order
     */
    public int compare(ShortPlace a, ShortPlace b){
        return a.getHost_name().compareTo(b.getHost_name());
    }
}