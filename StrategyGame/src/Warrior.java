
class Warrior extends MilitaryUnit {
	public Warrior(Tile p, double hp, String f) {
		super(p, hp, 1, f, 20.0, 1, 25);
	}
	public boolean equals(Object obj) {
		boolean b = !(obj instanceof Archer || obj instanceof Settler || obj instanceof Worker);
		if(b && super.equals(obj)) {
			return true;
		}
		return false;
	}
}
