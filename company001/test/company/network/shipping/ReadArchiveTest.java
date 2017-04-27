/**
 * 
 */
package company.network.shipping;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import company.network.shipping.Package;
import company.network.shipping.ReadArchive;

/**
 * @author hermann
 *
 */
public class ReadArchiveTest {

    private final static String path = System.getProperty("file.separator");
    private final static String pathLocal = path + "tmp" + path + "company";
    private ArrayList<File> archivesList;
    
    @Test
    public void testGetArchivesList(){
        ReadArchive readfiles = new ReadArchive();
        ArrayList<File> files = readfiles.getArchivesList(pathLocal);
        		files.forEach((f) -> {
        			assertEquals("Expected type of archive from list of archives:", ".csv", f.getName().substring(f.getName().length()-4));
        		});
    }
    
    @Test
    public void testReadArchives(){
        ReadArchive readfiles = new ReadArchive();
        archivesList = readfiles.getArchivesList(pathLocal);
        
        archivesList.stream().forEach((CSVFile) -> {
            readfiles.readArchives(CSVFile.getPath());
            
            List<Package> parcels = readfiles.getPackages();
            
            parcels.forEach((p) -> {
                assertEquals("Cost", p.getCheckCost(), p.getShippingLowestCost());
            });
        });
    }

}
