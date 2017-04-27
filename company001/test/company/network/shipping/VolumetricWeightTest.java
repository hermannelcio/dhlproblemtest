package company.network.shipping;

import static org.junit.Assert.*;

import org.junit.Test;

import company.network.shipping.VolumetricWeight;

public class VolumetricWeightTest {

    @Test
    public void test() {
        VolumetricWeight vol = new VolumetricWeight();
        assertEquals(0.5f, vol.roundWeight(0.400f), 0.1f);
        assertEquals(2.5f, vol.roundWeight(2.250f), 0.1f);
        assertEquals(2.0f, vol.roundWeight(1.890f), 0.1f);
        assertEquals(1.0f, vol.roundWeight(0.800f), 0.1f);
        
    }

}
