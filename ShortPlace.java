/**
 * This class was created to make it very easy and simple to sort the list of listings
 *
 * @author Sebastian Malos, Yusuf Yacoobali, Moonis Altaf and Kamil Duszak.
 * @version 1.0
 */
public class ShortPlace
{
    private String host_name;
    private int price;
    private int minimumNights;
    private int numberOfReviews;

    /**
     * Constructor for objects of class ShortPlace
     */
    public ShortPlace(String host_name,int price,int minimumNights,int numberOfReviews)
    {
        this.host_name = host_name;
        this.price = price;
        this.minimumNights = minimumNights;
        this.numberOfReviews = numberOfReviews;
    }
    
    /**
     * Returns host name
     */
    public String getHost_name() {
        return host_name;
    }
    
    /**
     * Returns price
     */
    public int getPrice() {
        return price;
    }
    
    /**
     * Returns number of reviews
     */
    public int getNumberOfReviews() {
        return numberOfReviews;
    }
    
    /**
     * Returns a string to be displayed on the list view
     */
    public String toString(){
        return host_name +  " hosting for $" + price + ", Min stay: " + minimumNights + ", Number of reviews:" + numberOfReviews;
    }
}