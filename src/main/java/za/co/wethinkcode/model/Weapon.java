package za.co.wethinkcode.model;

import javax.validation.constraints.NotNull;

public class Weapon extends Item {
	
	@NotNull
	private int damage;
	
	public Weapon(String name, String id, int level, int damage) {
		super(name, id, level);
		this.damage = damage;
	}
	
	public int getDamage() { return this.damage; }
	
	@Override
	public String toString() {
		return String.format("%s (%d)", this.getName(), this.damage);
	}
}
