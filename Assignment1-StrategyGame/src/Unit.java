
abstract class Unit {
	private Tile position;
	private double hp;
	private int moveRange;
	private String faction;

	public Unit(Tile p, double hp, int r, String f) {
		this.position=p;
		this.hp=hp;
		this.moveRange=r;
		this.faction=f;
		if(!position.addUnit(this)) {
			throw new IllegalArgumentException();
		}
	}
	public final Tile getPosition() {
		return this.position;
	}
	public final double getHP() {
		return this.hp;
	}
	public final String getFaction() {
		return this.faction;
	}
	public boolean moveTo(Tile t) {
		if(Tile.getDistance(position, t)<moveRange+1 && t.addUnit(this)) {
			final Tile old=position;
			position=t;
			old.removeUnit(this);
			return true;
		}
		return false;
	} 
	public void receiveDamage(double damage) {
		if(this.position.isCity()) {
			hp-=damage*.9;
		}
		else {
			hp-=damage;
		}
		if(hp<=0) {
			position.removeUnit(this);
		}
	}
	public abstract void takeAction(Tile t);
	
	public boolean equals(Object obj) { 
		//fuck it we ball
		if(((Unit)obj).getPosition().equals(getPosition()) 
		&&((Unit)obj).getHP()==getHP()
		&&((Unit)obj).getFaction().contentEquals(getFaction())
		&&obj instanceof Unit){
			return true;
		}
		return false;
		
	}
}
