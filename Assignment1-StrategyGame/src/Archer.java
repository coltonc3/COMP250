
class Archer extends MilitaryUnit{
	private int arrows;
	public Archer(Tile p, double hp, String f) {
		super(p, hp, 2, f, 15.0, 2, 0);
		this.arrows=5;
	}
	public void takeAction(Tile t) {
		if(arrows<=0) {
			arrows=5;
			return;
		}else {
			super.takeAction(t);
			arrows--;
		}
	} 
	public boolean equals(Object obj) {
		boolean b = !(obj instanceof Settler || obj instanceof Warrior || obj instanceof Worker);
		if(b && super.equals(obj) && ((Archer)obj).arrows==arrows) {
			return true;
		}
		return false;
	}

}
