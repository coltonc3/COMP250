
abstract class MilitaryUnit extends Unit{
	private double attackDamage;
	private int attackRange;
	private int armor; 
	
	public MilitaryUnit(Tile p, double hp, int r, String f, double ad, int ar, int a) {
		super(p, hp, r, f);
		this.attackDamage=ad;
		this.attackRange=ar;
		this.armor=a;
	}
	public void takeAction(Tile t) {
		if(Tile.getDistance(getPosition(), t)>=attackRange+1) {
			return;
		}else if(this.getPosition().isImproved()){
			if(t.selectWeakEnemy(getFaction())!=null) {
				t.selectWeakEnemy(getFaction()).receiveDamage(attackDamage*1.05);
			}
		}else {
			if(t.selectWeakEnemy(getFaction())!=null) {
				t.selectWeakEnemy(getFaction()).receiveDamage(attackDamage);
			}
		}
	}
	public void receiveDamage(double damage) { 
		super.receiveDamage(damage*(100/(100+armor)));
	}
}
