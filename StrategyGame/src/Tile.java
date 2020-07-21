
public class Tile {
	private int xCord;
	private int yCord;
	private boolean isCity;
	private boolean improved;
	private ListOfUnits list;
	
	public Tile(int x, int y) {
		this.xCord=x;
		this.yCord=y;
		this.isCity=false;
		this.improved=false;
		this.list=new ListOfUnits();
	}
	public int getX() {
		return this.xCord;
	}
	public int getY() {
		return this.yCord;
	}
	public boolean isCity() {
		return this.isCity;
	}
	public boolean isImproved() {
		return this.improved;
	}
	public void foundCity() {
		this.isCity=true;
	}
	public void buildImprovement() {
		this.improved=true;
	}
	public boolean addUnit(Unit u) {
		if(u instanceof MilitaryUnit) {
			for(int i=0;i<list.getArmy().length;i++) {
				String uFaction = u.getFaction();
				String thisFaction = list.getArmy()[i].getFaction();
				if(!uFaction.contentEquals(thisFaction) && list.getArmy()[i] instanceof MilitaryUnit) {
					return false;
				}
			}
		}
		list.add(u);
		return true;
	}
	public boolean removeUnit(Unit u) {
		return list.remove(u);
	}
	public Unit selectWeakEnemy(String f) {
		if(list.size()>0) { 
			Unit weakest=list.get(0);
			for(int i=0; i<list.size();i++) {
				if(list.get(i).getHP()<weakest.getHP() && !f.contentEquals(list.get(i).getFaction())) {
					weakest=list.get(i);
				}
			}
			return weakest;
		}else {
			return null;
		}
		
	}
	public static double getDistance(Tile a, Tile b) {
		double yDist = Math.abs(a.getY())-Math.abs(b.getY());
		double xDist = Math.abs(a.getX())-Math.abs(b.getX());
		return Math.sqrt(Math.pow(yDist, 2)+Math.pow(xDist, 2));
	}
	
}
