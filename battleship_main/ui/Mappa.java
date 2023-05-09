package battleship_main.ui;

import java.util.LinkedList;
import java.util.Random;

public class Mappa {
	public static final int DIM_MAPPA = 10;
	private final char NULL = '0', SHIP = 'X', ACQUA = 'A', HIT = 'H';
	private char[][] mappa;
	private LinkedList<ShipPos> listaNavi;

	public Mappa() {
		listaNavi = new LinkedList<ShipPos>();
		mappa = new char[DIM_MAPPA][DIM_MAPPA];
		for (int i = 0; i < DIM_MAPPA; i++)
			for (int j = 0; j < DIM_MAPPA; j++)
				mappa[i][j] = NULL;
	}

	public void riempiMappaRandom() {
		clear();
		Random r = new Random();
		insertShipRandom(r, 4);
		insertShipRandom(r, 3);
		insertShipRandom(r, 3);
		insertShipRandom(r, 2);
		insertShipRandom(r, 2);
		insertShipRandom(r, 2);
		insertShipRandom(r, 1);
		insertShipRandom(r, 1);
		insertShipRandom(r, 1);
		insertShipRandom(r, 1);
	}

	private void clear() {
		for (int i = 0; i < DIM_MAPPA; i++)
			for (int j = 0; j < DIM_MAPPA; j++)
				mappa[i][j] = NULL;

	}

	public boolean insertShip(int x, int y, int dim, int dir) {
		if (dir == 1 && x + dim > DIM_MAPPA) {
			return false;
		} // verticale
		if (dir == 0 && y + dim > DIM_MAPPA) {
			return false;
		} // orizzontale
		boolean inserito;

		if (dir == 0)
			inserito = checkHorizontal(x, y, dim);
		else
			inserito = checkVertical(x, y, dim);

		if (!inserito)
			return false;
		if (dir == 0) {
			ShipPos n = new ShipPos(x, y, x, y + dim - 1);
			listaNavi.add(n);
		} else {
			ShipPos n = new ShipPos(x, y, x + dim - 1, y);
			listaNavi.add(n);
		}
		for (int i = 0; i < dim; i++) {
			if (dir == 0) {
				mappa[x][y + i] = SHIP;
			} else
				mappa[x + i][y] = SHIP;
		}
		return true;
	}

	public int[] insertShipRandom(Random random, int dimensione) {
		boolean inserito;
		int[] dati = new int[4];
		int direzione, riga, colonna;
		do {
			inserito = true;
			direzione = random.nextInt(2); // 0=Horizontal, 1=Vertical
			if (direzione == 0) {
				colonna = random.nextInt(DIM_MAPPA - dimensione + 1);
				riga = random.nextInt(DIM_MAPPA);
			} else {
				colonna = random.nextInt(DIM_MAPPA);
				riga = random.nextInt(DIM_MAPPA - dimensione + 1);
			}
			if (direzione == 0)
				inserito = checkHorizontal(riga, colonna, dimensione);
			else
				inserito = checkVertical(riga, colonna, dimensione);
		} while (!inserito);
		if (direzione == 0) {
			ShipPos n = new ShipPos(riga, colonna, riga, colonna + dimensione - 1);
			listaNavi.add(n);
		} else {
			ShipPos n = new ShipPos(riga, colonna, riga + dimensione - 1, colonna);
			listaNavi.add(n);
		}
		for (int i = 0; i < dimensione; i++) {
			if (direzione == 0) {
				mappa[riga][colonna + i] = SHIP;
			} else
				mappa[riga + i][colonna] = SHIP;
		}
		dati[0] = riga;
		dati[1] = colonna;
		dati[2] = dimensione;
		dati[3] = direzione;
		return dati;
	}

	public boolean checkVertical(int riga, int colonna, int dimensione) {
		if (riga != 0)
			if (mappa[riga - 1][colonna] == SHIP)
				return false;
		if (riga != DIM_MAPPA - dimensione)// la nave finisce sul bordo
			if (mappa[riga + dimensione][colonna] == SHIP)
				return false;
		for (int i = 0; i < dimensione; i++) {
			if (colonna != 0)
				if (mappa[riga + i][colonna - 1] == SHIP)
					return false;
			if (colonna != DIM_MAPPA - 1)
				if (mappa[riga + i][colonna + 1] == SHIP)
					return false;
			if (mappa[riga + i][colonna] == SHIP)
				return false;
		}
		return true;
	}

	public boolean checkHorizontal(int riga, int colonna, int dimensione) {
		if (colonna != 0)
			if (mappa[riga][colonna - 1] == SHIP)
				return false;
		if (colonna != DIM_MAPPA - dimensione)
			if (mappa[riga][colonna + dimensione] == SHIP)
				return false;
		for (int i = 0; i < dimensione; i++) {
			if (riga != 0)
				if (mappa[riga - 1][colonna + i] == SHIP)
					return false;
			if (riga != DIM_MAPPA - 1)
				if (mappa[riga + 1][colonna + i] == SHIP)
					return false;
			if (mappa[riga][colonna + i] == SHIP)
				return false;
		}
		return true;
	}

	public boolean colpisci(Position p) {
		int riga = p.getCoordX();
		int colonna = p.getCoordY();
		if (mappa[riga][colonna] == SHIP) {
			mappa[riga][colonna] = HIT;
			return true;
		}
		mappa[riga][colonna] = ACQUA;
		return false;
	}

	public ShipPos sunk(Position p) {
		int riga = p.getCoordX();
		int col = p.getCoordY();
		ShipPos nave = null;
		for (int i = 0; i < listaNavi.size(); i++) {
			if (listaNavi.get(i).uguale(riga, col)) {
				nave = listaNavi.get(i);
				break;
			}
		}
		for (int i = nave.getXin(); i <= nave.getXfin(); i++) {
			for (int j = nave.getYin(); j <= nave.getYfin(); j++) {
				if (mappa[i][j] != HIT) {
					return null;
				}
			}
		}
		listaNavi.remove(nave);
		return nave;
	}

	public void setAcqua(Position p) {
		mappa[p.getCoordX()][p.getCoordY()] = ACQUA;
	}

	public boolean acqua(Position p) {
		return mappa[p.getCoordX()][p.getCoordY()] == ACQUA;
	}

	public boolean colpito(Position p) {
		return mappa[p.getCoordX()][p.getCoordY()] == HIT;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < DIM_MAPPA; i++) {
			for (int j = 0; j < DIM_MAPPA; j++) {
				sb.append(mappa[i][j] + " ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	// metodo che permette di ricevere la lista di navi dell avversario
	public void setAdvShips(LinkedList<int[]> advShips) {
		listaNavi.clear();
		for (int[] a : advShips) {
			insertShip(a[0], a[1], a[2], a[3]);
			System.out.println("sto inserendo" + a[0] + a[1] + a[2] + a[3]);
		}
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++)
				System.out.print(mappa[i][j]);
			System.out.println("");
		}
	}
}
