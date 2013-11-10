package cdsv_rogue;

import org.newdawn.slick.Input;

import it.randomtower.engine.entity.Entity;

public abstract class Unit extends Entity{
	
	TestRoom room;
	
	protected float health;
	protected boolean dead;
	
	protected float dx, dy, speedMod;
	
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

	public Unit(float x, float y, TestRoom room) {
		super(x, y);
		this.room = room;
		addType("UNIT");
		
		dead = false;
		statusEffects = new boolean[3];
		statusDuration = new int[3];
	}
	
	//handles the movement of the unit
	abstract public void move(Input i);
	
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
