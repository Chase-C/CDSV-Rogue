package cdsv_rogue;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.*;

import cdsv_rogue.spells.*;
import it.randomtower.engine.entity.Entity;

public abstract class Unit extends Entity{
	
	protected int currentSpell;
	Room room;
	
	protected float cooldown;
	protected float health;
	protected float maxHealth;
	protected boolean dead;
	
	public float dx, dy, speedMod;
	
	protected Spells[] spells;
	private ArrayList<Spell> passiveSpells;
	
	public enum StatusEffect {
		BURNING(0), FROZEN(1), SHOCKED(2), STUNNED(3);
		
		private int index;
		private StatusEffect(int index) {
			this.index = index;
		}
		
		public int getIndex() { return index; }
	}
	
	protected boolean shielded;
	protected int shieldDuration;
	
	protected boolean[] statusEffects;
	protected int[] statusDuration;

	public Unit(float x, float y, Room room) {
		super(x, y);
		this.room = room;
		addType("UNIT");
		
		health = 100;
		maxHealth = 100;
		speedMod = 1.0f;
		
		currentSpell = 0;
		spells = new Spells[3];
		randSpells();
		passiveSpells = new ArrayList<Spell>(0);
		
		dead = false;
		
		shielded = false;
		shieldDuration = 0;
		
		statusEffects = new boolean[4];
		statusDuration = new int[4];
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
	
	public void render(GameContainer gc, Graphics g) throws SlickException{
		/*g.setColor(Color.red);
		g.drawRect(x, y - 12, 16, 4);
		g.setColor(Color.green);
		g.drawRect(x, y - 12, 16 * health / maxHealth, 4);*/
	}
	
	protected void updatePassives() {
		for(Spell s : passiveSpells)
			s.activate(this);
	}
	
	abstract public void move(Input i) throws SlickException;
	
	public void randSpells() {
		int numSpells = Spells.values().length;
		int spell1 = (new Random()).nextInt(4); // Make sure we have an offensive move
		int spell2;
		do {
			spell2 = (new Random()).nextInt(numSpells);
		} while (spell2 == spell1);
		
		int spell3;
		do {
			spell3 = (new Random()).nextInt(numSpells);
		} while (spell3 == spell1 || spell3 == spell2);
		
		System.out.printf("%d\n", spell1);
		System.out.println(Spells.values()[spell1]);
		spells[0] = Spells.values()[spell1];
		spells[1] = Spells.values()[spell2];
		spells[2] = Spells.values()[spell3];
	}
	
	public void addPassiveSpell(Spell s) {
		passiveSpells.add(s);
	}
	
	public void removePassiveSpell(Spell s) {
		passiveSpells.remove(s);
	}
	
	public void heal(float amount) {
		health += amount;
		if(health > maxHealth)
			health = maxHealth;
	}
	
	public void damage(float amount) {
		health -= amount;
		if(health <= 0) {
			health = 0;
			dead = true;
			this.destroy();
		}
	}
	
	public void shield(int duration) {
		shielded = true;
		shieldDuration = duration;
	}
	
	protected void executeStatusEffects(int delta) {
		speedMod = 1.0f;
		if(statusEffects[StatusEffect.SHOCKED.getIndex()]) {
			damage(0.4f);
			speedMod = 0.8f;
		}
		if(statusEffects[StatusEffect.BURNING.getIndex()]) {
			damage(0.1f);
		}
		if(statusEffects[StatusEffect.FROZEN.getIndex()]) {
			speedMod = 0.5f;
		}
		if(statusEffects[StatusEffect.STUNNED.getIndex()]) {
			speedMod = 0.0f;
		}
		
		shieldDuration -= delta;
		if(shieldDuration < 0) {
			shieldDuration = 0;
			shielded = false;
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
		if(shielded) return;
		
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
	
	public void stun(int amount, int duration, int chance) {
		damage(amount);
		if((new Random()).nextInt(100) < chance) {
			statusEffects[StatusEffect.STUNNED.getIndex()] = true;
			statusDuration[StatusEffect.STUNNED.getIndex()] = duration;
		}
	}
	
	public float distToUnit(Unit u) {
		return (float)Math.sqrt(((x - u.x) * (x - u.x)) + ((y - u.y) * (y - u.y)));
	}
	//casts the current spell selected
	//abstract public void castSpell();
}
