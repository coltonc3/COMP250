
class Worker extends Unit {
	private int jobs;
	public Worker(Tile p, double hp, String f) {
		super(p, hp, 2, f);
		this.jobs=0;
	}
	public void takeAction(Tile t) {
		if(this.getPosition().equals(t) && !t.isImproved()) {
			t.buildImprovement();
		} 
		jobs++;
		if(jobs>=10) {
			t.removeUnit(this);
		}
	}
	public boolean equals(Object obj) {
		boolean b = !(obj instanceof Archer || obj instanceof Warrior || obj instanceof Settler);
		if(b && super.equals(obj) && ((Worker)obj).jobs==jobs) {
			return true;
		}
		return false;
		
	}
	
}
