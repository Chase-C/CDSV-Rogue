package cdsv_rogue.spells;

import it.randomtower.engine.entity.Entity;
import cdsv_rogue.Room;

public enum Spells {
	FIREBALL("Fireball"),
	ICEBALL("Iceball"),
	LIGHTNINGBOLT("Lightning Bolt"),
	ROCKTHROW("Rock Throw"),
	HEAL("Heal"),
	PASSIVEHEAL("Passive Heal"),
	SHIELD("Shield");
	
	public String name;
	
	private Spells(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void cast(Room room, Entity parent, float x, float y, float tx, float ty, float dx, float dy) {
		Spells spell = FIREBALL;
		for(Spells s : Spells.values()) {
			if(name.equals(s.getName()))
				spell = s;
		}
		
		switch(spell) {
		case FIREBALL:
			new Fireball(room, parent, x, y, tx, ty, dx, dy);
			break;
		case ICEBALL:
			new Iceball(room, parent, x, y, tx, ty, dx, dy);
			break;
		case LIGHTNINGBOLT:
			new LightningBolt(room, parent, x, y, tx, ty, dx, dy);
			break;
		case ROCKTHROW:
			new RockThrow(room, parent, x, y, tx, ty, dx, dy);
			break;
		case HEAL:
			new Heal(room, parent, x, y, tx, ty, dx, dy);
			break;
		case PASSIVEHEAL:
			new PassiveHeal(room, parent, x, y, tx, ty, dx, dy);
			break;
		case SHIELD:
			new Shield(room, parent, x, y, tx, ty, dx, dy);
			break;
		}
	}
}
