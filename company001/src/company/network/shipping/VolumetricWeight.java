/**
 * 
 */
package company.network.shipping;

/**
 * The VolumetricWeight class implements the variables which is necessary to
 * defines the volumetric weight.
 * 
 * @author hermann
 *
 */
public class VolumetricWeight {
    
    /**
     * A float that keeps the package's width
     */
    protected float width;
    
    /**
     * An int that keeps the package's length
     */
    protected int length;
    
    /**
     * An int that keeps the package's height
     */
    protected int height;
    
    /**
     * A int that keeps a constant to calculate the volumetric weight 
     */
    static private int constant = 5000;
    
    /**
     * A float that keeps the volumetric weight 
     */
    protected Float volumetricWeight;
    
    /**
     * This method verify if there are data enough to calculate the volumetric weight and 
     * if it is necessary. It also give the volumetric weight.
     * 
     * @return
     */
    public Float getVolumetricWeight() {
        if (length > 0 && height > 0){
            CalculateVolumetricWeight();
        }
        return volumetricWeight;
    }
    
    public void setWidth(float width) {
        this.width = width;
    }
    
    public void setLength(int length) {
        this.length = length;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    private void setVolumetricWeight(Float volumetricWeight) {
        this.volumetricWeight = volumetricWeight;
    }
    
    /**
     * This method set the 3 mains variable.
     * 
     * @param width
     * @param length
     * @param height
     */
    public void setVolumetricWeight (final float width, final int length, final int height){
        this.width = width;
        this.length = length;
        this.height = height;
        CalculateVolumetricWeight();
    }
    
    /**
     * this method calculates the volumetric weight and set the value in the volumetricweight variable.
     */
    protected void CalculateVolumetricWeight(){
        Float calc = (float) ((width * height * length) / constant);
        this.setVolumetricWeight(roundWeight(calc));
    }
    
    /**
     * This method round up its variable to the nearest 0.5
     * It has not any other math API due to their being slower for giving the result than this way.
     * 
     * @param number
     * @return
     */
    public float roundWeight(float number) {
        return (float) ((Math.ceil(number * 2) / 2));
    }
    

}
