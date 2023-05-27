package battleship_main.ui;

import java.util.LinkedList;
import java.util.Random;

public class Computer {
	private LinkedList<Position> listOfHits;
	private Random r;
	private int hit;
	private LinkedList<String> possibility;
	private Position lastHit;
	private String direction;
	private Mappa plMap;
	private Position firstHitLocation;// location where you first hit the octopus

	public Computer(Mappa cpuMap) {
		listOfHits = new LinkedList<Position>();
		this.plMap = cpuMap;
		for (int i = 0; i < Mappa.DIM_MAPPA; i++) {
			for (int j = 0; j < Mappa.DIM_MAPPA; j++) {
				Position p = new Position(i, j);
				listOfHits.add(p);// initialize possible hits
			}
		}
		r = new Random();
		hit = 0;
	}

	public Report nextTurn() {

		Report rep = new Report();
		if (hit == 0) {
			boolean attack = hitRandomly();
			rep.setP(lastHit);
			rep.setHit(attack);
			OctPos dead;
			if (attack) {
				hit++;
				dead = plMap.sunk(lastHit);
				if (dead != null) {
					rep.setDead(true);
					removeOutlines(dead);
					hit = 0;
					direction = null;
				} else {
					firstHitLocation = lastHit;
					possibility = new LinkedList<String>();
					initializeHit();
				}
			}
			return rep;
		} // shoot randomly
		if (hit == 1) {
			boolean isAttacked = wrongHit1();
			OctPos dead;
			rep.setP(lastHit);
			rep.setHit(isAttacked);
			rep.setDead(false);
			if (isAttacked) {
				hit++;
				possibility = null;
				dead = plMap.sunk(lastHit);
				if (dead != null) {
					rep.setDead(true);
					removeOutlines(dead);
					hit = 0;
					direction = null;
				}
			}
			return rep;
		}
		if (hit >= 2) {
			boolean isAttacked = wrongHit2();
			OctPos sunk;
			rep.setP(lastHit);
			rep.setHit(isAttacked);
			rep.setDead(false);
			if (isAttacked) {
				hit++;
				sunk = plMap.sunk(lastHit);
				if (sunk != null) {
					rep.setDead(true);
					removeOutlines(sunk);
					hit = 0;
					direction = null;
				}
			} else {
				revertDirection();
			}
			return rep;
		}
		return null;// unattainable
	}

	private boolean hitRandomly() {
		int attackNo = r.nextInt(listOfHits.size());
		Position p = listOfHits.remove(attackNo);
		lastHit = p;
		boolean attack = plMap.hitt(p);
		return attack;
	}

	private boolean wrongHit1() {
		boolean error = true;
		Position p = null;
		do {
			int tiro = r.nextInt(possibility.size());
			String dove = possibility.remove(tiro);
			p = new Position(firstHitLocation);
			p.move(dove.charAt(0));
			direction = dove;
			if (!plMap.acqua(p)) {
				listOfHits.remove(p);
				error = false;
			}
		} while (error);// Verify that you are not attempting to fire on an already hit position
		lastHit = p;
		return plMap.hitt(p);
	}

	private boolean wrongHit2() {
		boolean isHit = false;
		Position p = new Position(lastHit);
		do {
			p.move(direction.charAt(0));

			if (p.outOfMap() || plMap.acqua(p)) {
				revertDirection();
			} else {
				if (!plMap.hit(p)) {
					isHit = true;
				}

			}
		} while (!isHit);
		listOfHits.remove(p);
		lastHit = p;
		return plMap.hitt(p);
	}

	//

	private void removeOutlines(OctPos dead) {
		int Xin = dead.getXin();
		int Xfin = dead.getXfin();
		int Yin = dead.getYin();
		int Yfin = dead.getYfin();
		if (Xin == Xfin) {// horizontal
			if (Yin != 0) {
				Position p = new Position(Xin, Yin - 1);
				if (!plMap.acqua(p)) {
					listOfHits.remove(p);
					plMap.setAcqua(p);

				}
			}
			if (Yfin != Mappa.DIM_MAPPA - 1) {
				Position p = new Position(Xin, Yfin + 1);
				if (!plMap.acqua(p)) {
					listOfHits.remove(p);
					plMap.setAcqua(p);
				}
			}
			if (Xin != 0) {
				for (int i = 0; i <= Yfin - Yin; i++) {
					Position p = new Position(Xin - 1, Yin + i);
					if (!plMap.acqua(p)) {
						listOfHits.remove(p);
						plMap.setAcqua(p);
					}
				}

			}
			if (Xin != Mappa.DIM_MAPPA - 1) {
				for (int i = 0; i <= Yfin - Yin; i++) {
					Position p = new Position(Xin + 1, Yin + i);
					if (!plMap.acqua(p)) {
						listOfHits.remove(p);
						plMap.setAcqua(p);
					}
				}
			}
		} else {// vertical
			if (Xin != 0) {
				Position p = new Position(Xin - 1, Yin);
				if (!plMap.acqua(p)) {
					listOfHits.remove(p);
					plMap.setAcqua(p);
				}
			}
			if (Xfin != Mappa.DIM_MAPPA - 1) {
				Position p = new Position(Xfin + 1, Yin);
				if (!plMap.acqua(p)) {
					listOfHits.remove(p);
					plMap.setAcqua(p);
				}
			}
			if (Yin != 0) {
				for (int i = 0; i <= Xfin - Xin; i++) {
					Position p = new Position(Xin + i, Yin - 1);
					if (!plMap.acqua(p)) {
						listOfHits.remove(p);
						plMap.setAcqua(p);
					}
				}

			}
			if (Yfin != Mappa.DIM_MAPPA - 1) {
				for (int i = 0; i <= Xfin - Xin; i++) {
					Position p = new Position(Xin + i, Yin + 1);
					if (!plMap.acqua(p)) {
						listOfHits.remove(p);
						plMap.setAcqua(p);
					}
				}
			}
		}
	}

	private void initializeHit() {
		if (lastHit.getCoordX() != 0) {
			possibility.add("N");
		}
		if (lastHit.getCoordX() != Mappa.DIM_MAPPA - 1) {
			possibility.add("S");
		}
		if (lastHit.getCoordY() != 0) {
			possibility.add("O");
		}
		if (lastHit.getCoordY() != Mappa.DIM_MAPPA - 1) {
			possibility.add("E");
		}
	}

	private void revertDirection() {
		if (direction.equals("N")) {
			direction = "S";
		} else if (direction.equals("S")) {
			direction = "N";
		} else if (direction.equals("E")) {
			direction = "O";
		} else if (direction.equals("O")) {
			direction = "E";
		}
	}

}
