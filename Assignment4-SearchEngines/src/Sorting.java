import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry; // You may need it to implement fastSort

public class Sorting {
	static int run=32;
	/*
	 * This method takes as input an HashMap with values that are Comparable. 
	 * It returns an ArrayList containing all the keys from the map, ordered 
	 * in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n^2) as it uses bubble sort, where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable> ArrayList<K> slowSort (HashMap<K, V> results) {
        ArrayList<K> sortedUrls = new ArrayList<K>();
        sortedUrls.addAll(results.keySet());	//Start with unsorted list of urls

        int N = sortedUrls.size();
        for(int i=0; i<N-1; i++){
			for(int j=0; j<N-i-1; j++){
				if(results.get(sortedUrls.get(j)).compareTo(results.get(sortedUrls.get(j+1))) <0){
					K temp = sortedUrls.get(j);
					sortedUrls.set(j, sortedUrls.get(j+1));
					sortedUrls.set(j+1, temp);					
				}
			}
        }
        return sortedUrls;                    
    }
    
    // I am using MergeSort as the faster sorting algorithm 
    
    public static <K,V extends Comparable> ArrayList<K> merge(HashMap<K,V> results, K[] list1, K[] list2) {
    	ArrayList<K> sorted = new ArrayList<K>();
    	int i=0;
    	int j=0;
    	while(i<list1.length && j<list2.length) {
    		if(results.get(list1[i]).compareTo(results.get(list2[j]))>0) {
    			sorted.add(list1[i]);
    			i++;
    		}else {
    			sorted.add(list2[j]);
    			j++;
    		}
    	}
    	while(i<list1.length) {
    		sorted.add(list1[i]);
    		i++;
    	}
    	while(j<list2.length) {
    		sorted.add(list2[j]);
    		j++;
    	}
    	return sorted;
    }
    public static <K,V extends Comparable> ArrayList<K> mergeSort(HashMap<K,V> results, ArrayList<K> sorted2){
    	if(sorted2.size()==1) {
    		return sorted2;
    	}else {
    		int mid = (sorted2.size()-1)/2;
    		ArrayList<K> list1 = new ArrayList<K>(sorted2.subList(0, mid+1));
    		ArrayList<K> list2 = new ArrayList<K>(sorted2.subList(mid+1, sorted2.size()));
    		
        
    		list1 = mergeSort(results, list1);
        	list2 = mergeSort(results, list2);

        	
        	return merge(results,(K[]) list1.toArray(),(K[]) list2.toArray()); 

    	}
    }

   

	/*
	 * This method takes as input an HashMap with values that are Comparable. 
	 * It returns an ArrayList containing all the keys from the map, ordered 
	 * in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n*log(n)), where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable> ArrayList<K> fastSort(HashMap<K, V> results) {
    	
    	ArrayList<K> sorted = new ArrayList<K>();

    		return mergeSort(results, new ArrayList<K>(results.keySet()));
    }
    
}