/**
 * 
 */
package company.network.shipping;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The ReadArchive class, given a local path, read the files type .csv, create and load the objects related with.
 * 
 * 
 * @author hermann
 *
 */
public class ReadArchive {
    
   
    private static final String COMMA = ",";
     
    /**
      * An ArrayList that is used to keep the list of files' names with its paths.
      */
    private ArrayList<File> archivesList;
    
    /**
     * A HashMap that keeps the information of friends connection.
     */
    protected HashMap<String, Map<String, Integer>> wholeNetwork;
    
    /**
     * A List that keeps the packages given.
     */
    protected List<Package> packages;
    
    /**
     * This method gets the list of files in the given path and fill up an ArrayList with this information/
     * 
     * @param pathLocal
     * @return ArrayList<String>
     */
    public ArrayList<File> getArchivesList(String pathLocal) {
        archivesList = new ArrayList<File>();
        File fList[] = new File(pathLocal).listFiles();
        if(fList != null){
            for (int i = 0; fList.length > i; i++) {
                if(fList[i].getPath().substring(fList[i].getPath().lastIndexOf(".")).equalsIgnoreCase(".csv"))
                    archivesList.add(fList[i]);
            }
        }
        return archivesList;
    }
    
    public ArrayList<File> getArchivesList() {
        return archivesList;
    }
    
    public HashMap<String, Map<String, Integer>> getWholeNetwork() {
        return wholeNetwork;
    }
    
    public void setWholeNetwork(HashMap<String, Map<String, Integer>> wholeNetwork) {
        this.wholeNetwork = wholeNetwork;
    }
    
    public List<Package> getPackages() {
        return packages;
    }
    
    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }
    
    public void setArchivesList(ArrayList<File> archivesList) {
        this.archivesList = archivesList;
    }
    
    /**
     * This method get the list or archives from the system and call another method to read and parse it
     */
    public void readArchives() {
        if(!archivesList.isEmpty()){
            archivesList.stream().forEach((cSVFile) -> {
                readParseCSV(cSVFile.getPath());
            });
        }
    }
    
    /**
     * This method call another method to read and parse a file if its name path is given,
     * 
     * @param cSVFile
     */
    public void readArchives(String cSVFile) {
        readParseCSV(cSVFile);
    }
    
    /**
     * This method read the file and fill up all its object found in it. 
     * 
     * @param CSVFile
     */
    private void readParseCSV(String CSVFile){
        wholeNetwork = new HashMap<String, Map<String, Integer>>();
        packages = new ArrayList<Package>();
        try (FileReader file = new FileReader(CSVFile)) {
                
            BufferedReader read = new BufferedReader(file);
            
            read.lines().forEach((line) -> {
                if(!line.contains("@")){
                
                    String key = Stream.of(line.split(COMMA)).findFirst().get();
                    
                    Stream<String> friendPlusHard = Stream.of(line.split(COMMA)).skip(1);

                    Map<String, Integer> networkMap = friendPlusHard
                            .map(element -> element.split(":"))
                            .collect(Collectors.toMap(e -> e[0], e -> Integer.parseInt(e[1])));

                    wholeNetwork.put(key, networkMap);
                    
                } else {
            
                    
                    Stream<String> parcel = Stream.of(line.split(COMMA)).skip(1);
                    
                    String[] ArrayParcel = parcel.toArray(size -> new String[size]);
                    
                    final Package aPackage = new Package();
                    
                    aPackage.setTarget(ArrayParcel[0]);
                    
                    String[] measures = ArrayParcel[1].split("x");
                    
                    final NormalizedWeight normalizedWeight = new NormalizedWeight(Float.parseFloat(measures[0]), Integer.parseInt(measures[1]), Integer.parseInt(measures[2]), Float.parseFloat(measures[3]));
                    
                    aPackage.setNormalizedWeight(normalizedWeight);
                    
                    aPackage.setCheckCost(ArrayParcel[2].contains("~") ? Double.POSITIVE_INFINITY : Double.valueOf(ArrayParcel[2]));
                    
                    aPackage.setWholeNetwork(this.wholeNetwork);
                    
                    packages.add(aPackage);
                
                }
                        
            });
                
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
}
