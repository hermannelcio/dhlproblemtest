/**
 * 
 */
package company.network.shipping;

/**
 * 
 * The NoemalizedWeight class defines the normalized package weight.
 * 
 * @author hermann
 *
 */
public class NormalizedWeight extends VolumetricWeight {
    
    /**
     * A Float that keep the weight given
     */
    private Float weight;
    
    /**
     * A Float that will be generate by the system
     */
    private Float normalizedWeight;
    

    private void setNormalizedWeight(Float normalizedWeight) {
        this.normalizedWeight = normalizedWeight;
    }

    public Float getNormalizedWeight() {
        
        return normalizedWeight;
    }


    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }


    /**
     * The NormalizedWeight method set the normalizedWeight variable and also is a constructor 
     */
    public NormalizedWeight(final float width, final int length, final int height, final Float weight) {
        super.setVolumetricWeight(width, length, height);
        this.weight = super.roundWeight(weight/1000);
        setNormalizedWeight();
    }
    
    /**
     * This method decide the greater value between the volumetric weight and the weight aiming
     * to define the normalized weight.
     */
    private void setNormalizedWeight() {
        if(weight != null && volumetricWeight!= null){
            setNormalizedWeight(weight > volumetricWeight ? weight : volumetricWeight);
        }
    }

}
