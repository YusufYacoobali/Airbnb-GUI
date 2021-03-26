import java.util.ArrayList;
import java.util.HashMap;


/**
 * Write a description of class StatisticEngine here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class StatisticEngine
{
    // instance variables - replace the example below with your own
        
    private AirbnbDataLoader loader = new AirbnbDataLoader();
    private ArrayList<AirbnbListing> listings = loader.load();
    private HashMap<String, Integer> bouroughPrice = new HashMap();
    
    private int numberOfAvailableProperties;
    private int numberOfEntireHomes;
    private int totalReviews;
    private double averageNumberOfreviewsPerProperty;
    private String mostExpeniveBourough;
    
    public StatisticEngine(){
        runStatistics();
    }
    
    private void runStatistics(){
        for(int i = 0; i < listings.size(); i++){
            AirbnbListing thisProperty = listings.get(i);
            checkAvailability(thisProperty);
            checkIfEntireHome(thisProperty);
            calculateTotalReviews(thisProperty);
            addBoroughCost(thisProperty);
        }
        calculateAverageReviews();
        checkMostExpensiveBourough();
    }
    
    private void checkAvailability(AirbnbListing property){
        if (property.getAvailability365() > 0){
           numberOfAvailableProperties ++; 
        }
    }
    
    private void checkIfEntireHome(AirbnbListing property){
        if (property.getRoom_type() .equals( "Entire home/apt")){
            numberOfEntireHomes ++;
        }
    }
    
    private void calculateTotalReviews(AirbnbListing property){
        totalReviews = totalReviews + property.getNumberOfReviews();   
    }
    
    private void calculateAverageReviews(){
        averageNumberOfreviewsPerProperty = totalReviews/numberOfAvailableProperties;
    }
    
    private void addBoroughCost(AirbnbListing property){
        int minCost = property.getPrice() * property.getMinimumNights();
        String borough = property.getNeighbourhood();
        if (bouroughPrice.get(borough) == null){
            bouroughPrice.put(borough, minCost);
        }
        else{
            bouroughPrice.put(borough, bouroughPrice.get(borough) + minCost);
        }
    }
    
    private void checkMostExpensiveBourough(){
        ArrayList<String> boroughs = new ArrayList<>(bouroughPrice.keySet());
        mostExpeniveBourough = boroughs.get(0);
        int max = bouroughPrice.get(boroughs.get(0));
        for (int i = 1; i < boroughs.size(); i++){
            int thisPrice = bouroughPrice.get(boroughs.get(i)); 
            if (thisPrice > max){
                max = thisPrice;
                mostExpeniveBourough = boroughs.get(i);
            }
        }
        
    }
    
    public int getNumberOfEntireHomes(){
        return numberOfEntireHomes;
    }
    
    public double getAverageNumberOfreviewsPerProperty(){
        return averageNumberOfreviewsPerProperty;
    }
    
    public int getTotalAvailableProperties(){
        return numberOfAvailableProperties;
    }
    
    public String getMostExpeniveBourough(){
        return mostExpeniveBourough;
    }
}
