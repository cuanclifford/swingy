package za.co.wethinkcode.model;

import javax.validation.constraints.NotNull;

public class Hero extends Character {
	
	@NotNull
	private String heroClass;
	
	public Weapon weapon;
	
	public Armour armour;
	
	public Helmet helmet;
	
	@NotNull
	private int level;
	
	@NotNull
	private int xp;
	
	private int xpRequirement;
	
	public Hero(String name, String heroClass, int level, int xp, int health, int attack, int defense, Weapon weapon, Armour armour, Helmet helmet) {
		super(name, health, attack, defense);
		
		this.heroClass = heroClass;
		
		this.level = level;
		this.xp = xp;
		this.xpRequirement = this.calculateXpRequirement();
		
		this.weapon = weapon;
		this.armour = armour;
		this.helmet = helmet;
	}
	
	public Hero(String name, String heroClass, int health, int attack, int defense, Weapon weapon, Armour armour, Helmet helmet) {
		super(name, health, attack, defense);
		
		this.heroClass = heroClass;
		
		this.level = 1;
		this.xp = 0;
		this.xpRequirement = this.calculateXpRequirement();
		
		this.weapon = weapon;
		this.armour = armour;
		this.helmet = helmet;
	}
	
	public int calculateXpRequirement() {
		return this.level * 1000 + (this.level - 1) * (this.level - 1) * 450;
	}
	
	public int getTotalHealth() {
		if (this.helmet != null)
			return this.getHealth() + this.helmet.getProtection();
		return this.getHealth();
	}
	
	public int getTotalAttack() {
		if (this.weapon != null)
			return this.getAttack() + this.weapon.getDamage();
		return this.getAttack();
	}
	
	public int getTotalDefense() {
		if (this.armour != null)
			return this.getDefense() + this.armour.getArmour();
		return this.getDefense();
	}
	
	public String getHeroClass() { return this.heroClass; }
	
	public int getLevel() { return this.level; }
	
	public int getXp() { return this.xp; }
	
	public int getXpRequirement() { return this.xpRequirement; }
	
	private void levelUp() {
		this.level++;
		this.setHealth(this.getHealth() + 1);
		this.setAttack(this.getAttack() + 1);
		this.setDefense(this.getDefense() + 1);
		
	}
	
	public void gainXp(int xp) {
		this.xp += xp;
		if (this.xp >= this.xpRequirement) {
			this.levelUp();
			this.xp = this.xp - this.xpRequirement;
			this.xpRequirement = this.calculateXpRequirement();
		}
	}
	
	public void equipItem(Item item) {
		if (item instanceof Weapon) {
			this.weapon = (Weapon)item;
		} else if (item instanceof Armour) {
			this.armour = (Armour)item;
		} else if (item instanceof Helmet) {
			this.helmet = (Helmet)item;
		} else {
			System.out.println("This item cannot be equipped.");
		}
	}
	
	@Override
	public String toString() {
		return String.format(
			"%-10s %-10s\thp:%d(%d)\tatk:%d(%d)\tdef:%d(%d)\tlvl:%d\txp:%d/%d\nWeapon: %s Armour: %s Helmet: %s",
			this.getName(),
			this.getHeroClass(),
			this.getTotalHealth(),
			this.getHealth(),
			this.getTotalAttack(),
			this.getAttack(),
			this.getTotalDefense(),
			this.getDefense(),
			this.getLevel(),
			this.getXp(),
			this.getXpRequirement(),
			this.weapon != null ? this.weapon.toString() : "none",
			this.armour != null ? this.armour.toString() : "none",
			this.helmet != null ? this.helmet.toString() : "none"
		);
	}

}
