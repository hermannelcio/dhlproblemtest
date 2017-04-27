/**
 * 
 */
package company.network.shipping;

/**
 * The Package class is a value object that keeps each package given.
 * 
 * @author hermann
 *
 */
public class Package extends OptimizeCost{
    
    /**
     * A string that keep the target of the parcel
     */
    private String target;
    
    /**
     * A string that keep the standard source according to the test description 
     */
    static private String source = "ME";
    
    /**
     * A NormalizedWeith object that keeps, calculate and gives the normalized weight
     */
    private NormalizedWeight normalizedWeight;
    
    /**
     * A double that keeps the result of its lowest cost.
     */
    private Double shippingLowestCost;
    
    /**
     * A double that keeps the lowest cost to compare with the software's answer
     */
    private Double checkCost;
    
    
    public Double getCheckCost() {
        return checkCost;
    }
    
    public void setCheckCost(Double checkCost) {
        this.checkCost = checkCost;
    }
    
    public String getTarget() {
        return target;
    }
    
    public void setTarget(String target) {
        this.target = target;
    }
    
    public String getSource() {
        return source;
    }
    
    public void setSource(String source) {
        Package.source = source;
    }
    
    public NormalizedWeight getNormalizedWeight() {
        return normalizedWeight;
    }
    
    public void setNormalizedWeight(NormalizedWeight normalizedWeight) {
        this.normalizedWeight = normalizedWeight;
    }
    
    
    /**
     * This method, as soon as the whole information necessary is given, calculates the lowest cost 
     * using its super class.
     * 
     * @return shippingLowestCost
     */
    public Double getShippingLowestCost() {
        this.shippingLowestCost = super.calculateLowesCost(this);
        return shippingLowestCost;
    }
    
    public void setShippingLowestCost(Double shippingLowestCost) {
        this.shippingLowestCost = shippingLowestCost;
    }

}
