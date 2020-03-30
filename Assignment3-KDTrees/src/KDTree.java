// Colton Campbell
// 260777576

import java.util.ArrayList;
import java.util.Iterator;
public class KDTree implements Iterable<Datum>{ 

	KDNode 		rootNode;
	int    		k; 
	int			numLeaves;
	ArrayList<Datum> leaves = new ArrayList<Datum>();
	
	// constructor

	public KDTree(ArrayList<Datum> datalist) throws Exception {

		Datum[]  dataListArray  = new Datum[ datalist.size() ]; 
		if (datalist.size() == 0) {
			throw new Exception("Trying to create a KD tree with no data");
		}
		else
			this.k = datalist.get(0).x.length;

		int ct=0;
		for (Datum d :  datalist) {
			dataListArray[ct] = datalist.get(ct);
			ct++;
		}
		
	//   Construct a KDNode that is the root node of the KDTree.

		rootNode = new KDNode(dataListArray);
	}
	
	//   KDTree methods
	public void printLeaves() {
		System.out.println(rootNode.getLeaves());
	}
	public ArrayList<Datum> getLeaves(){
		return this.rootNode.getLeaves();
	}
	public Datum nearestPoint(Datum queryPoint) {
		return rootNode.nearestPointInNode(queryPoint);
	}
	

	public int height() {
		return this.rootNode.height();	
	}

	public int countNodes() {
		return this.rootNode.countNodes();	
	}
	
	public int size() {
		return this.numLeaves;	
	}

	//-------------------  helper methods for KDTree   ------------------------------

	public static long distSquared(Datum d1, Datum d2) {

		long result = 0;
		for (int dim = 0; dim < d1.x.length; dim++) {
			result +=  (d1.x[dim] - d2.x[dim])*((long) (d1.x[dim] - d2.x[dim]));
		}
		// if the Datum coordinate values are large then we can easily exceed the limit of 'int'.
		return result;
	}

	public double meanDepth(){
		int[] sumdepths_numLeaves =  this.rootNode.sumDepths_numLeaves();
		return 1.0 * sumdepths_numLeaves[0] / sumdepths_numLeaves[1];
	}
	
	class KDNode { 

		boolean leaf;
		Datum leafDatum;           //  only stores Datum if this is a leaf
		
		//  the next two variables are only defined if node is not a leaf

		int splitDim;      // the dimension we will split on
		int splitValue;    // datum is in low if value in splitDim <= splitValue, and high if value in splitDim > splitValue  
		
		KDNode lowChild, highChild;   //  the low and high child of a particular node (null if leaf)
		  //  You may think of them as "left" and "right" instead of "low" and "high", respectively
		ArrayList<Datum> lowTree;
		ArrayList<Datum> highTree;
		ArrayList<Integer> mins; 
		ArrayList<Integer> maxs;
		KDNode(Datum[] datalist) throws Exception{

			/*
			 *  This method takes in an array of Datum and returns 
			 *  the calling KDNode object as the root of a sub-tree containing  
			 *  the above fields.
			 */

			//   ADD YOUR CODE BELOW HERE
		
			Datum[] list;
			// prevent duplicates
			if(datalist.length>1 && isAllEqual(datalist)) {			
				list = new Datum[1];
				list[0]=datalist[0];
				this.leaf=true;
			}else{
				list = datalist.clone();
			}
			if(list.length==1) {
				this.leaf=true;
				this.leafDatum = list[0];
				numLeaves++;
				this.lowChild=null;
				this.highChild=null;
				leaves.add(this.leafDatum);
			}
			else {
				this.leaf=false;
				this.lowTree=new ArrayList<Datum>();
				this.highTree=new ArrayList<Datum>();
				this.mins = new ArrayList<Integer>(); 
				this.maxs = new ArrayList<Integer>();
				
				int min;
				int max;
				int maxRange=0;
				ArrayList<Integer> ranges = new ArrayList<Integer>();
				// fill arraylist with mins and maxes for each dimension
				int i=0;
				while(i<k) {
					min=list[0].x[i];
					max=list[0].x[i];
					for(Datum d : list) {
						if(d.x[i]<=min) {
							min= d.x[i];
						}
						else if(d.x[i]>=max) { 
							max=d.x[i];
						}
					}
					this.mins.add(min);
					this.maxs.add(max);
					ranges.add((int)Math.abs(max-min));
					i++;
				}
				for(int j=0;j<ranges.size();j++) {
					if(ranges.get(j)>=maxRange) {
						maxRange=ranges.get(j);
						this.splitDim=j;
						if(maxRange==1) {
							this.splitValue=this.mins.get(j);
						}else {
							double real =((this.mins.get(j)+this.maxs.get(j)))/2.0;
							this.splitValue=(this.mins.get(j)+this.maxs.get(j))/2;
							if(real>0 && this.splitValue<real) {
								this.splitValue++;
							}
							else if(real<0 && this.splitValue>real) {
								this.splitValue--;
							}
						}
					}
				}
				
				int b=0;
				for(Datum d:list) {
					if(d.x[this.splitDim]<=this.splitValue) {
						this.lowTree.add(d);
					}else {
						this.highTree.add(d);
					}
				}
				Datum[] low = new Datum[this.lowTree.size()];
				Datum[] high = new Datum[this.highTree.size()];
				int ct=0;
				for(Datum d:this.lowTree) {
					low[ct]=this.lowTree.get(ct);
					ct++;
				}
				int c=0;
				for(Datum d:this.highTree) {
					high[c]=this.highTree.get(c);
					c++;
				}
				this.lowChild=new KDNode(low);
				this.highChild=new KDNode(high);
			
			}
			//   ADD YOUR CODE ABOVE HERE

		}
		public boolean isAllEqual(Datum[] a){
			for(int i=1; i<a.length; i++){
				if(!a[0].equals(a[i])){
					return false;
				}	
			}
			return true;
		}

		
		public ArrayList<Datum> getLeaves() {
			return leaves;
		}
		public Datum nearestPointInNode(Datum queryPoint) {
			Datum nearestPoint, nearestPoint_otherSide;
		
			//   ADD YOUR CODE BELOW HERE
			nearestPoint=closestInNode(this,queryPoint);
			return nearestPoint;
			
			//   ADD YOUR CODE ABOVE HERE

		}
		public Datum closestInNode(KDNode node, Datum queryPoint) {
			Datum best;
			Datum nearest;
			long minDist;
			if(node.leaf) {
				best=node.leafDatum;
				minDist=distSquared(best, queryPoint);
				return best;
			}
			else {

				int i=0;
				int[] points = new int[k];
				while(i<k) {
					if(i!=node.splitDim) {
						points[i]=queryPoint.x[i];
					}else {
						points[i]=node.splitValue;
					}
					i++;
				}
				Datum splitter = new Datum(points);
				if(queryPoint.x[node.splitDim]<node.splitValue) {
					best = closestInNode(node.lowChild, queryPoint);
					nearest=best;
					minDist = distSquared(queryPoint, best);
					if(distSquared(splitter, queryPoint)<minDist) {
						best =closestInNode(node.highChild,queryPoint);
					}
				}else {
					best= closestInNode(node.highChild, queryPoint);
					nearest=best;
					minDist = distSquared(queryPoint, best);
					if(distSquared(splitter, queryPoint)<minDist) {
						best=closestInNode(node.lowChild,queryPoint);
					}
					
				}
				if(nearest.equals(best)) {
					return best;
				}
				else if(distSquared(queryPoint, nearest)<distSquared(queryPoint,best)) {
					return nearest;
				}
				return best;
			}
		}
		
		// -----------------  KDNode helper methods (might be useful for debugging) -------------------

		public int height() {
			if (this.leaf) {
				return 0;
			}
			else {
				return 1 + Math.max( this.lowChild.height(), this.highChild.height());
			}
		}
		public int countNodes() {
			if (this.leaf)
				return 1;
			else
				return 1 + this.lowChild.countNodes() + this.highChild.countNodes();
		}
		
		/*  
		 * Returns a 2D array of ints.  The first element is the sum of the depths of leaves
		 * of the subtree rooted at this KDNode.   The second element is the number of leaves
		 * this subtree.    Hence,  I call the variables  sumDepth_size_*  where sumDepth refers
		 * to element 0 and size refers to element 1.
		 */
				
		public int[] sumDepths_numLeaves(){
			int[] sumDepths_numLeaves_low, sumDepths_numLeaves_high;
			int[] return_sumDepths_numLeaves = new int[2];
			
			/*     
			 *  The sum of the depths of the leaves is the sum of the depth of the leaves of the subtrees, 
			 *  plus the number of leaves (size) since each leaf defines a path and the depth of each leaf 
			 *  is one greater than the depth of each leaf in the subtree.
			 */
			
			if (this.leaf) {  // base case
				return_sumDepths_numLeaves[0] = 0;
				return_sumDepths_numLeaves[1] = 1;
			}
			else {
				sumDepths_numLeaves_low  = this.lowChild.sumDepths_numLeaves();
				sumDepths_numLeaves_high = this.highChild.sumDepths_numLeaves();
				return_sumDepths_numLeaves[0] = sumDepths_numLeaves_low[0] + sumDepths_numLeaves_high[0] + sumDepths_numLeaves_low[1] + sumDepths_numLeaves_high[1];
				return_sumDepths_numLeaves[1] = sumDepths_numLeaves_low[1] + sumDepths_numLeaves_high[1];
			}	
			return return_sumDepths_numLeaves;
		}
		
	}
	
	public Iterator<Datum> iterator() {
		return new KDTreeIterator();
	}
	
	private class KDTreeIterator implements Iterator<Datum> {
		
		//   ADD YOUR CODE BELOW HERE
		Datum cur;
		int p=0;
		KDTreeIterator() {
			p=0;
			cur=leaves.get(0);

		}
		@Override
		public boolean hasNext() {
			return (cur!=null);
		}

		@Override
		public Datum next() {
				Datum tmp=cur;
				if(p+1<size()) {
					p++;
					cur=leaves.get(p);
					return tmp;
				}
				else if(p+1==size()) {
					cur=null;
					return leaves.get(size()-1);
				}else {
					return null;
				}
				
				
				
		}
		//   ADD YOUR CODE ABOVE HERE

	}

}

