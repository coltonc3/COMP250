import java.util.HashMap;
import java.util.ArrayList;

public class SearchEngine {
	public HashMap<String, ArrayList<String> > wordIndex;   // this will contain a set of pairs (String, LinkedList of Strings)	
	public MyWebGraph internet;
	public XmlParser parser;

	public SearchEngine(String filename) throws Exception{
		this.wordIndex = new HashMap<String, ArrayList<String>>();
		this.internet = new MyWebGraph();
		this.parser = new XmlParser(filename);
	}
	
	/* 
	 * This does a graph traversal of the web, starting at the given url.
	 * For each new page seen, it updates the wordIndex, the web graph,
	 * and the set of visited vertices.
	 * 
	 */
	

	public void crawlAndIndex(String url) throws Exception {
		internet.addVertex(url);
		internet.setVisited(url, true);
		for(String link : parser.getLinks(url)) {
			internet.addVertex(link);		
			internet.addEdge(url, link);

			for(String word : parser.getContent(link)) {
				word=word.toLowerCase();
				if(!wordIndex.containsKey(word)) {
					wordIndex.put(word, new ArrayList<String>());
				}
				if(!wordIndex.get(word).contains(link)) {
					ArrayList<String>tmp = wordIndex.get(word);
					tmp.add(link);
					wordIndex.replace(word, tmp);
				}
			}
			if(!internet.getVisited(link)) {	
				
				crawlAndIndex(link);

			}
		}
	}
	
	
	
	/* 
	 * This computes the pageRanks for every vertex in the web graph.
	 * It will only be called after the graph has been constructed using
	 * crawlAndIndex(). 
	 * To implement this method, refer to the algorithm described in the 
	 * assignment pdf. 
	 * 
	 */
	public void assignPageRanks(double epsilon) {
		for(String v:internet.getVertices()) {
			internet.setPageRank(v, 1.0);
		}
		double i = 0;
		boolean b= true;
		boolean c= true;
		while(c==true) {
			int j=0;
			i++;
			ArrayList<Double> computedRanks = computeRanks(internet.getVertices());
			c=false;
			for(String s: internet.getVertices()) {
				double lastRank = internet.getPageRank(s);
				internet.setPageRank(s, computedRanks.get(j));
				double nowRank=internet.getPageRank(s);
				if(Math.abs(lastRank-nowRank)>=epsilon) {
					b=true;
					c=true;
				}else {
					b=false;
				}
				j++;
			}
		}
	}

	/*
	 * The method takes as input an ArrayList<String> representing the urls in the web graph 
	 * and returns an ArrayList<double> representing the newly computed ranks for those urls. 
	 * Note that the double in the output list is matched to the url in the input list using 
	 * their position in the list.
	 */
	public ArrayList<Double> computeRanks(ArrayList<String> vertices) {
		
		ArrayList<Double> ranks = new ArrayList<Double>();
		for(String v : vertices) {
			double r=.5;
			for(String url:internet.getEdgesInto(v)) {
				r+=0.5*internet.getPageRank(url)/internet.getOutDegree(url);
			}
			ranks.add(r);
		}
		return ranks;
	}

	
	/* Returns a list of urls containing the query, ordered by rank
	 * Returns an empty list if no web site contains the query.
	 * 
	 */
	public ArrayList<String> getResults(String query) {
		// TODO: Add code here
		query=query.toLowerCase();
		ArrayList<String> results = new ArrayList<String>();
		ArrayList<String> onlySite = new ArrayList<String>();
		onlySite.add(query);
		HashMap<String, String> m = new HashMap<String, String>();
		if(wordIndex.get(query)==null) {
			return new ArrayList<String>();
		}
		for(String s:wordIndex.get(query)) {
			
			m.put(s, query); // (site that contain query, query)
		}
		HashMap<String, Double> sites = new HashMap<String, Double>(); 
		for(String site:m.keySet()) {
			sites.put(site, internet.getPageRank(site)); //(site that contains query, respective ranks)
		}
		
		return Sorting.fastSort(sites); // sorts sites that contain query by pageRank
	}
}
