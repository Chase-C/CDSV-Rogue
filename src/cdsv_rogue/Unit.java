package cdsv_rogue;

import org.newdawn.slick.*;

import it.randomtower.engine.entity.Entity;

public abstract class Unit extends Entity{
	
	protected int currentSpell;
	Room room;
	
	protected float cooldown;
	protected float health;
	protected boolean dead;
	
	public float dx, dy, speedMod;
	
	public enum StatusEffect {
		BURNING(0), FROZEN(1), SHOCKED(2);
		
		private int index;
		private StatusEffect(int index) {
			this.index = index;
		}
		
		public int getIndex() { return index; }
	}
	
	protected boolean[] statusEffects;
	protected int[] statusDuration;

	public Unit(float x, float y, Room room) {
		super(x, y);
		this.room = room;
		addType("UNIT");
		
		dead = false;
		statusEffects = new boolean[3];
		statusDuration = new int[3];
	}
	
	//casts the current spell selected
	//abstract public void castSpell();
	
	/*
	 * Different methods will represent the different kinds of spells.
	 * 
	 * 1 - fireball
	 * 2, 3 and so on will represent the other spells
	 */
	/*public void castFireball(){
		Projectile fireball = new Projectile(x, y);
		TestRoom.projectiles.add(fireball);
		System.out.println("Projectile added");
	}*/
	
	abstract public void move(Input i) throws SlickException;
	
	public void damage(float amount) {
		health -= amount;
		if(health <= 0) {
			health = 0;
			dead = true;
		}
	}
	
	protected void executeStatusEffects(int delta) {
		if(statusEffects[StatusEffect.SHOCKED.getIndex()]) {
			damage(0.4f);
			speedMod = 0.8f;
		} else {
			speedMod = 1.0f;
		}
		if(statusEffects[StatusEffect.BURNING.getIndex()]) {
			damage(0.1f);
		}
		if(statusEffects[StatusEffect.FROZEN.getIndex()]) {
			speedMod = 0.5f;
		} else {
			speedMod = 1.0f;
		}
		
		for(StatusEffect s : StatusEffect.values()) {
			statusDuration[s.getIndex()] -= delta;
			if(statusDuration[s.getIndex()] <= 0) {
				statusDuration[s.getIndex()] = 0;
				statusEffects[s.getIndex()] = false;
			}
		}
	}
	
	public void burn(int amount, int duration) {
		damage(amount);
		statusEffects[StatusEffect.BURNING.getIndex()] = true;
		statusDuration[StatusEffect.BURNING.getIndex()] = duration;
		
		if(statusEffects[StatusEffect.FROZEN.getIndex()])
			statusEffects[StatusEffect.FROZEN.getIndex()] = false;
	}
	
	public void freeze(int amount, int duration) {
		damage(amount);
		statusEffects[StatusEffect.FROZEN.getIndex()] = true;
		statusDuration[StatusEffect.FROZEN.getIndex()] = duration;

		if(statusEffects[StatusEffect.BURNING.getIndex()])
			statusEffects[StatusEffect.BURNING.getIndex()] = false;
	}
	
	public void shock(int amount, int duration) {
		damage(amount);
		statusEffects[StatusEffect.SHOCKED.getIndex()] = true;
		statusDuration[StatusEffect.SHOCKED.getIndex()] = duration;
	}
	//casts the current spell selected
	//abstract public void castSpell();
}
