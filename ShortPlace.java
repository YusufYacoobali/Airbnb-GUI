/**
 * Write a description of class ShortPlace here.
 *
 * @author (your name)
 * @version (a version number or a date)
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
    
    public String getHost_name() {
        return host_name;
    }
    
    public int getPrice() {
        return price;
    }
    
    public int getNumberOfReviews() {
        return numberOfReviews;
    }
    
    public String toString(){
        return host_name +  " hosting for $" + price + ", Min stay: " + minimumNights + ", Number of reviews:" + numberOfReviews;
    }
}