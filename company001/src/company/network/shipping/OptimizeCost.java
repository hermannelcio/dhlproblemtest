/**
 * 
 */
package company.network.shipping;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The OptimizeCost class manages to calculate the lowest cost of sending a package.
 * 
 * @author hermann
 *
 */
public class OptimizeCost extends ReadArchive {
    
    /**
     * List used by this code to keep friends that will not be calculated.
     */
    private List<String> blackNetWork;
    
    /**
     * List that keeps the network inverse in which the target is the key of the map.
     */
    private List<HashMap<String, LinkedHashMap<String, Integer>>> possibleWaysReachTarget;
    
    /**
     * Map that keeps the whole net work of a csv file.
     */
    private Map<String, Map<String, Integer>> netWork;
    
    /**
     * String that keeps the parcel source.
     */
    private String parcelSource;
    
    /**
     * An Integer that keeps the result of the lowest cost.
     */
    private Integer result;
    
    /**
     * A boolean that keeps control if the map need to be recalculate.
     */
    private boolean isNeededRecalculateMap;
    
    /**
     * A list that is auxiliar to the system to calculate and fill up the PossibleWaysReachTarget list
     */
    private List<HashMap<String, LinkedHashMap<String, Integer>>> auxiliaryList;
    
    /**
     * A boolean that keeps control what friend should be included in the BlackNetWork list
     */
    private boolean noTarget;
    
    /**
     * A string that keeps the package's target 
     */
    private String parcelTarget;
    
    /**
     * This method calculates and returns the package's lowest hardness.
     * 
     * @return result
     */
   private double totalSumming(){
       result = 0;
       if(possibleWaysReachTarget.isEmpty())
           return Double.POSITIVE_INFINITY;
       possibleWaysReachTarget.forEach((map) -> {
           map.forEach((k, v) -> {
               if(v.containsKey(parcelSource)){
                   Integer sum = v.values().stream().mapToInt(Number::intValue).sum();
                   if(result > sum || result == 0){
                       result = sum;
                   }
               }
           });
       });
   return result;
   }
   
   /**
    * This method creates a new map based on target aiming to compare its lowest hardness.
    * @param target
    */
   private void createNewMapBasedOnTarget(String target){
       if (possibleWaysReachTarget == null || possibleWaysReachTarget.isEmpty()) {
           possibleWaysReachTarget = new ArrayList<HashMap<String, LinkedHashMap<String, Integer>>>();
           getTargetWays(target);
       }
       
       isNeededRecalculateMap = true;
       
       if(possibleWaysReachTarget != null || !possibleWaysReachTarget.isEmpty()){
           while(isNeededRecalculateMap){
               isNeededRecalculateMap = false;
               auxiliaryList = new ArrayList<HashMap<String, LinkedHashMap<String, Integer>>>(possibleWaysReachTarget);
               possibleWaysReachTarget.forEach((map) -> {
                   map.forEach((key, value) -> {
                       if(!blackNetWork.contains(value.keySet().toArray()[value.size() -1]) && !value.keySet().toArray()[value.size() -1].equals(parcelSource)){
                           isNeededRecalculateMap = true; 
                           getTargetFriendsWays( value.keySet().toArray()[value.size() -1].toString());
                       } 
                   });
                   
               });
               possibleWaysReachTarget = auxiliaryList;
           }
       }
   }
       
   
   /**
    * This method fill up the first time of possibleWaysReachTarget list with all the friends that can send to the target.
    * 
    * @param target
    */
   private void getTargetWays (String target) {
       netWork.forEach((key, value) -> {
           HashMap<String, LinkedHashMap<String, Integer>> auxiliaryMapList = new HashMap<String, LinkedHashMap<String, Integer>>();
           LinkedHashMap<String, Integer> auxiliaryMap = new LinkedHashMap<String, Integer>();
           if(value.containsKey(target)){
               auxiliaryMap.put(key, value.get(target));
               auxiliaryMapList.put(target, auxiliaryMap);
               possibleWaysReachTarget.add(auxiliaryMapList);
           }
       });
       blackNetWork.add(target);
   }
   
   /**
    * This method continue filling up the possibleWaysReachTarget list with all the friends that is needed to send anything to a target.
    * 
    * @param target
    */
   private void getTargetFriendsWays (String target) {
       noTarget = true;
       netWork.forEach((key, value) -> {
           if(value.containsKey(target) && !key.equals(parcelTarget) && !blackNetWork.contains(key)){
               noTarget = false;
               HashMap<String, LinkedHashMap<String, Integer>> auxBuildMap = new HashMap<String, LinkedHashMap<String, Integer>>();
               HashMap<String, LinkedHashMap<String, Integer>> auxMapRemoveList = new HashMap<String, LinkedHashMap<String, Integer>>();
               LinkedHashMap<String, Integer> auxiliaryMap = new LinkedHashMap<String, Integer>();
               possibleWaysReachTarget.forEach((subMap) -> {
                   subMap.forEach((subKey, subValue) -> {
                       if(subValue.keySet().toArray()[subValue.size() -1].equals(target) && !subValue.containsKey(key)){
                           subValue.forEach(auxiliaryMap::put);
                           auxMapRemoveList.put(subKey, auxiliaryMap);
                           auxiliaryList.remove(auxMapRemoveList);
                           auxiliaryMap.put(key, value.get(target));
                           auxBuildMap.put(subKey, auxiliaryMap);
                           auxiliaryList.add(auxBuildMap);
                       }
                   });
               });
           }
       });
       if(noTarget)
           blackNetWork.add(target);
   }
   
   /**
    * this method manage to get and return the lowest cost of sending a package.
    * 
    * @param parcel
    * @return result
    */
   public Double calculateLowesCost(Package parcel){
       DecimalFormat decimalFormat = new DecimalFormat("#.##");  
       
       this.netWork = parcel.getWholeNetwork();
       this.parcelSource = parcel.getSource();
       this.parcelTarget = parcel.getTarget();
       
       blackNetWork = new ArrayList<String>();

       createNewMapBasedOnTarget(parcel.getTarget());
       
       Double totalSummingHardDouble = totalSumming();
       
       Double result = Math.sqrt(totalSummingHardDouble) * parcel.getNormalizedWeight().getNormalizedWeight();
       
       if(result.isInfinite())
           return result;
       else    
           return Double.valueOf(decimalFormat.format(result));
   }
   
}