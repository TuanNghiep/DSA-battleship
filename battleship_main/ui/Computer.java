package battleship_main.ui;

import java.util.LinkedList;
import java.util.Random;

public class Computer {
	private LinkedList<Position> listaColpi;
	private Random r;
	private int hit;
	private LinkedList<String> possibilita;
	private Position ultimoColpo;
	private String direzione;
	private Mappa plMap;
	private Position primohit;// location where you first hit the ship

	public Computer(Mappa mappaAvversario) {
		listaColpi = new LinkedList<Position>();
		this.plMap = mappaAvversario;
		for (int i = 0; i < Mappa.DIM_MAPPA; i++) {
			for (int j = 0; j < Mappa.DIM_MAPPA; j++) {
				Position p = new Position(i, j);
				listaColpi.add(p);// initialize possible hits
			}
		}
		r = new Random();
		hit = 0;
	}

	public Report mioTurno() {

		Report rep = new Report();
		if (hit == 0) {
			boolean colpo = sparaRandom();
			rep.setP(ultimoColpo);
			rep.setHit(colpo);
			ShipPos sunk;
			if (colpo) {
				hit++;
				sunk = plMap.sunk(ultimoColpo);
				if (sunk != null) {
					rep.setSunk(true);
					rimuoviContorni(sunk);
					hit = 0;
					direzione = null;
				} else {
					primohit = ultimoColpo;
					possibilita = new LinkedList<String>();
					inizializzaLista();
				}
			}
			return rep;
		} // shoot randomly
		if (hit == 1) {
			boolean colpo = sparaMirato1();
			ShipPos sunk;
			rep.setP(ultimoColpo);
			rep.setHit(colpo);
			rep.setSunk(false);
			if (colpo) {
				hit++;
				possibilita = null;
				sunk = plMap.sunk(ultimoColpo);
				if (sunk != null) {
					rep.setSunk(true);
					rimuoviContorni(sunk);
					hit = 0;
					direzione = null;
				}
			}
			return rep;
		}
		if (hit >= 2) {
			boolean colpo = sparaMirato2();
			ShipPos sunk;
			rep.setP(ultimoColpo);
			rep.setHit(colpo);
			rep.setSunk(false);
			if (colpo) {
				hit++;
				sunk = plMap.sunk(ultimoColpo);
				if (sunk != null) {
					rep.setSunk(true);
					rimuoviContorni(sunk);
					hit = 0;
					direzione = null;
				}
			} else {
				invertiDirezione();
			}
			return rep;
		}
		return null;// unattainable
	}

	private boolean sparaRandom() {
		int tiro = r.nextInt(listaColpi.size());
		Position p = listaColpi.remove(tiro);
		ultimoColpo = p;
		boolean colpo = plMap.hitt(p);
		return colpo;
	}

	private boolean sparaMirato1() {
		boolean errore = true;
		Position p = null;
		do {
			int tiro = r.nextInt(possibilita.size());
			String dove = possibilita.remove(tiro);
			p = new Position(primohit);
			p.sposta(dove.charAt(0));
			direzione = dove;
			if (!plMap.acqua(p)) {
				listaColpi.remove(p);
				errore = false;
			}
		} while (errore);// Verify that you are not attempting to fire on an already hit position
		ultimoColpo = p;
		return plMap.hitt(p);
	}

	private boolean sparaMirato2() {
		boolean colpibile = false;
		Position p = new Position(ultimoColpo);
		do {
			p.sposta(direzione.charAt(0));

			if (p.fuoriMappa() || plMap.acqua(p)) {
				invertiDirezione();
			} else {
				if (!plMap.hit(p)) {
					colpibile = true;
				}

			}
		} while (!colpibile);
		listaColpi.remove(p);
		ultimoColpo = p;
		return plMap.hitt(p);
	}

	//

	private void rimuoviContorni(ShipPos sunk) {
		int Xin = sunk.getXin();
		int Xfin = sunk.getXfin();
		int Yin = sunk.getYin();
		int Yfin = sunk.getYfin();
		if (Xin == Xfin) {// horizontal
			if (Yin != 0) {
				Position p = new Position(Xin, Yin - 1);
				if (!plMap.acqua(p)) {
					listaColpi.remove(p);
					plMap.setAcqua(p);

				}
			}
			if (Yfin != Mappa.DIM_MAPPA - 1) {
				Position p = new Position(Xin, Yfin + 1);
				if (!plMap.acqua(p)) {
					listaColpi.remove(p);
					plMap.setAcqua(p);
				}
			}
			if (Xin != 0) {
				for (int i = 0; i <= Yfin - Yin; i++) {
					Position p = new Position(Xin - 1, Yin + i);
					if (!plMap.acqua(p)) {
						listaColpi.remove(p);
						plMap.setAcqua(p);
					}
				}

			}
			if (Xin != Mappa.DIM_MAPPA - 1) {
				for (int i = 0; i <= Yfin - Yin; i++) {
					Position p = new Position(Xin + 1, Yin + i);
					if (!plMap.acqua(p)) {
						listaColpi.remove(p);
						plMap.setAcqua(p);
					}
				}
			}
		} else {// vertical
			if (Xin != 0) {
				Position p = new Position(Xin - 1, Yin);
				if (!plMap.acqua(p)) {
					listaColpi.remove(p);
					plMap.setAcqua(p);
				}
			}
			if (Xfin != Mappa.DIM_MAPPA - 1) {
				Position p = new Position(Xfin + 1, Yin);
				if (!plMap.acqua(p)) {
					listaColpi.remove(p);
					plMap.setAcqua(p);
				}
			}
			if (Yin != 0) {
				for (int i = 0; i <= Xfin - Xin; i++) {
					Position p = new Position(Xin + i, Yin - 1);
					if (!plMap.acqua(p)) {
						listaColpi.remove(p);
						plMap.setAcqua(p);
					}
				}

			}
			if (Yfin != Mappa.DIM_MAPPA - 1) {
				for (int i = 0; i <= Xfin - Xin; i++) {
					Position p = new Position(Xin + i, Yin + 1);
					if (!plMap.acqua(p)) {
						listaColpi.remove(p);
						plMap.setAcqua(p);
					}
				}
			}
		}
	}

	private void inizializzaLista() {
		if (ultimoColpo.getCoordX() != 0) {
			possibilita.add("N");
		}
		if (ultimoColpo.getCoordX() != Mappa.DIM_MAPPA - 1) {
			possibilita.add("S");
		}
		if (ultimoColpo.getCoordY() != 0) {
			possibilita.add("O");
		}
		if (ultimoColpo.getCoordY() != Mappa.DIM_MAPPA - 1) {
			possibilita.add("E");
		}
	}

	private void invertiDirezione() {
		if (direzione.equals("N")) {
			direzione = "S";
		} else if (direzione.equals("S")) {
			direzione = "N";
		} else if (direzione.equals("E")) {
			direzione = "O";
		} else if (direzione.equals("O")) {
			direzione = "E";
		}
	}

}
