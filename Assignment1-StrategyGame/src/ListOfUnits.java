
public class ListOfUnits {
	private Unit[] list;
	private int size;

	public ListOfUnits() {
		this.list=new Unit[10];
		this.size=0;
	}
	public int size() {
		size=0;
		for(int i=0; i<list.length;i++) {
			if(list[i] instanceof Unit && list[i]!=null) {
				size++;
			}
		}
		return this.size;
	}
	public Unit[] getUnits() {
		Unit[] temp=new Unit[size()];
		int index=0;
		for(int i=0; i<list.length; i++) {
			if(list[i] instanceof Unit && list[i]!=null) {
				temp[index]=list[i];
				index++;
			}
		}
		return temp;
	}
	public Unit get(int i) {
		if(i<0 || i>=size()) {
			throw new IndexOutOfBoundsException();
		}
		else {
			Unit x=getUnits()[i];
			return x;
		}
	}
	public void add(Unit x) {
		if(list[list.length-1]==null) { // if last slot is open
			for(int i=0;i<list.length;i++) {
				if(list[i]==(null)) {
					list[i]=x;
					break;
				}
			}
		}
		// if no room to add then create new list
		else { 
			int c=list.length+list.length/2+1;
			Unit[] newList = new Unit[c];
			for(int i=0;i<size(); i++) {
				newList[i]=get(i);
			}
			newList[size()]=x;
			list=newList;
		}
	
	}
	public int indexOf(Unit x) {
		int a =-1;
		for(int i=0; i<size(); i++) {
			if(get(i).equals(x)) {
				a=i;
				break;
			}
		}
		return a;
	}
	// **reduce # units in list by 1, not entire list. then shift left all units after removed unit by 1
	public boolean remove(Unit x) {
		int r=indexOf(x);
		if(r!=-1) {
			for(int i=r; i<list.length-1; i++) { // new loop to shift every Unit 1 position leftward
				list[i]=list[i+1];
			}
			return true;
		}
		else if(r==list.length-1) {
			list[list.length-1]=null;
		}
		return false;
	}
	public MilitaryUnit[] getArmy() {
		int count=0;
		int index=0;
		for(int i=0;i<size();i++) {
			if(get(i) instanceof MilitaryUnit) {
				count++;
			}
		}
		MilitaryUnit[] army = new MilitaryUnit[count];
		for(int i=0; i<size();i++) {
			if(get(i) instanceof MilitaryUnit) {
				army[index]=(MilitaryUnit) get(i);
				index++;
			}
		}
		
		return army;
	}
}
