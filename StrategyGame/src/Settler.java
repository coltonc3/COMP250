
class Settler extends Unit {
	public Settler(Tile p, double hp, String f) {
		super(p, hp, 2, f);
	}

	public void takeAction(Tile t) {
		if(this.getPosition().equals(t) && !t.isCity()) {
			t.foundCity();
			t.removeUnit(this);
		} 
	}
	public boolean equals(Object obj) {
		boolean b = !(obj instanceof Archer || obj instanceof Warrior || obj instanceof Worker);
		if(b && super.equals(obj)) {
			return true;
		}
		return false;
		
	}

}
