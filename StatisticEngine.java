import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is responsible for calculating the eight statistics.
 *
 * @author Sebastian Malos, Yusuf Yacoobali, Moonis Altaf and Kamil Duszak.
 * @version 1.0
 */
public class StatisticEngine
{
    private AirbnbDataLoader loader = new AirbnbDataLoader();
    private ArrayList<AirbnbListing> listings = loader.load();
    private HashMap<String, Integer> boroughPrice = new HashMap();
    private HashMap<String, Integer> boroughProperty = new HashMap();
    private HashMap<String, Integer> hostProperty = new HashMap();

    // fields:
    private int totalProperties;
    private int numberOfAvailableProperties;
    private int numberOfEntireHomes;
    private int totalReviews;
    private double averageNumberOfreviewsPerProperty;
    private String mostExpensiveBorough;
    private String mostPopularBorough;
    private int numberOfPropertiesViewed;
    private int bookForLessThanWeek;
    private String hostWithMostProperties;
    private int numberOfPropertiesAllYear;

    /**
     * Constructor for the StatisticEngine class.
     */
    
    public StatisticEngine(){
        runStatistics();
    }

    /**
     * A for loop which goes through the whole database and runs different methods on each property.
     * The current property that the loop is viewing is passed through all these methods.
     * At the end of the loop some calculation methods are called.
     */
    private void runStatistics(){
        for(int i = 0; i < listings.size(); i++){
            totalProperties++;
            AirbnbListing thisProperty = listings.get(i);
            checkAvailability(thisProperty);
            checkIfEntireHome(thisProperty);
            calculateTotalReviews(thisProperty);
            addBoroughCost(thisProperty);
            addBoroughProperty(thisProperty);
            checkIfBookForLessThanWeek(thisProperty);
            addHost(thisProperty);
            checkAllYear(thisProperty);
        }
        calculateAverageReviews();
        checkMostExpensiveBorough();
        calculateMostPopularBorough();
        calculateHostWithMostProperties();
    }

    /**
     * Checks if property is available or not by calling a method in the AirbnbListing class.
     * If the property is available it increments the field by one.
     * @param property A property of type AirbnbListing
     */
    private void checkAvailability(AirbnbListing property){
        if (property.getAvailability365() > 0){
            numberOfAvailableProperties ++; 
        }
    }

    /**
     * Checks if property is an Entire home.
     * If the property is then increments the field by one.
     * @param property A property of type AirbnbListing
     */
    private void checkIfEntireHome(AirbnbListing property){
        if (property.getRoom_type() .equals( "Entire home/apt")){
            numberOfEntireHomes ++;
        }
    }

    /**
     * Method Increments the amount of total reviews with the specified property's review count
     */
    private void calculateTotalReviews(AirbnbListing property){
        totalReviews = totalReviews + property.getNumberOfReviews();   
    }

    /**
     * Method calculates the average reviews by deviding the total reviews by the total properties
     */
    private void calculateAverageReviews(){
        averageNumberOfreviewsPerProperty = totalReviews/totalProperties;
    }

    /**
     * Method adds a property and its minimum cost to the boroughPrice hashMap. If the borough hasnt been entered then it simply adds the cost of the current proeprty.
     * If the borough of the specified property has already been added to the map it replaces it by incrementing the price with the minimum price of the current property.
     */
    private void addBoroughCost(AirbnbListing property){
        int minCost = property.getPrice() * property.getMinimumNights(); //calculating the minimum nights
        String borough = property.getNeighbourhood(); // getting the borough
        if (boroughPrice.get(borough) == null){ //if the borough has not been added
            boroughPrice.put(borough, minCost);
        }
        else{ //If the borough is already in the Map.
            boroughPrice.put(borough, boroughPrice.get(borough) + minCost);
        }
    }

    /**
     * Method goes through a list of all the boroughs and compares all their minimum prices with the max to calculate the most expensive borough
     */
    private void checkMostExpensiveBorough(){
        ArrayList<String> boroughs = new ArrayList<>(boroughPrice.keySet());
        mostExpensiveBorough = boroughs.get(0);
        int max = boroughPrice.get(boroughs.get(0)); // first borough initially the max
        for (int i = 1; i < boroughs.size(); i++){
            int thisPrice = boroughPrice.get(boroughs.get(i)); 
            if (thisPrice > max){ // comparing the current borough with the max
                max = thisPrice;
                mostExpensiveBorough = boroughs.get(i);
            }
        }
    }

    /**
     * Method incremnets the total number of properties in a specified borough.
     * Adds the borough of the specified property to the hash map, borough property.
     * If it already exits it overwrites by incrementing and if not then places 1 in the value of the map.
     */
    private void addBoroughProperty(AirbnbListing property){
        String borough = property.getNeighbourhood();   
        if (boroughProperty.get(borough) == null){
            boroughProperty.put(borough, 1);
        }
        else{
            boroughProperty.put(borough, boroughProperty.get(borough) + 1);
        }
    }

    /**
     * Method goes through a list of all the boroughs and compares all their total properties with 
     * the max to calculate the most popular borough.
     */
    private void calculateMostPopularBorough(){
        ArrayList<String> boroughs = new ArrayList<>(boroughPrice.keySet());
        mostPopularBorough = boroughs.get(0);
        int max = boroughProperty.get(mostPopularBorough);
        for (int i = 1; i < boroughs.size(); i++){
            int thisBorough = boroughProperty.get(boroughs.get(i)); 
            if (thisBorough > max){
                max = thisBorough;
                mostPopularBorough = boroughs.get(i);
            }
        }
    }
    
    /**
     * Checks if the specified propety is available to book for less than 7 days, and if it is 
     * it increments the variable bookForLessThanWeek.
     */
    private void checkIfBookForLessThanWeek(AirbnbListing property){
        if (property.getMinimumNights() < 7){
            bookForLessThanWeek++;
        }
    }

    /**
     * The host of the specified property is added to a hash map alongside the amount of properties they own. 
     */
    private void addHost(AirbnbListing property){
        String host = property.getHost_name();   
        if (hostProperty.get(host) == null){
            hostProperty.put(host, property.getCalculatedHostListingsCount());
        }
    }
    
    /**
     * Method goes through a list of all the hosts and compares all their total properties 
     * with the max to calculate the host with the most properties.
     */
    private void calculateHostWithMostProperties(){
        ArrayList<String> hosts = new ArrayList<>(hostProperty.keySet());
        hostWithMostProperties = hosts.get(0);
        int max = hostProperty.get(hostWithMostProperties);
        for (int i = 1; i < hosts.size(); i++){
            int thisHostProperty = hostProperty.get(hosts.get(i)); 
            if (thisHostProperty > max){
                max = thisHostProperty;
                hostWithMostProperties = hosts.get(i);
            }
        } 
    }

    /**
     * If the specified property is available all year than the numberOfPropertiesAllYear variable increases by one.
     */
    private void checkAllYear(AirbnbListing property){
        if (property.getAvailability365() == 365){
            numberOfPropertiesAllYear++;
        }
    }
    
    /**
     * @return the number of entire homes
     */
    public int getNumberOfEntireHomes(){
        return numberOfEntireHomes;
    }
    
    /**
     * @return the average number of reviews per property
     */
    public double getAverageNumberOfreviewsPerProperty(){
        return averageNumberOfreviewsPerProperty;
    }

    /**
     * @return the total number of available properties
     */
    public int getTotalAvailableProperties(){
        return numberOfAvailableProperties;
    }

    /**
     * @return the most expensive borough
     */
    public String getMostExpensiveBorough(){
        return mostExpensiveBorough;
    }
    
    /**
     * @return the most popular borough
     */
    public String getMostPopularBorough(){
        return mostPopularBorough;
    }

    /**
     * @return properties availabe for less than a week
     */
    public int getBookForLessThanWeek(){
        return bookForLessThanWeek;
    }

    /**
     * @return the host with the most properties
     */
    public String getHostWithMostProperties(){
        return hostWithMostProperties;
    }
    
    /**
     * @return the number of propeties available all year
     */
    public int getNumberOfPropertiesAllYear(){
        return numberOfPropertiesAllYear;
    }

    /**
     * @return the total number of properties 
     */
    public int getTotalProperties(){
        return totalProperties;
    }
}
