package battleship_main.ui;

import java.util.LinkedList;
import java.util.Random;

public class Mappa {
	public static final int DIM_MAPPA = 10;
	private final char NULL = '0', OCTOPUS = 'X', ACQUA = 'A', HIT = 'H';
	private char[][] mappa;
	private LinkedList<OctPos> listOct;

	public Mappa() {
		listOct = new LinkedList<OctPos>();
		mappa = new char[DIM_MAPPA][DIM_MAPPA];
		for (int i = 0; i < DIM_MAPPA; i++)
			for (int j = 0; j < DIM_MAPPA; j++)
				mappa[i][j] = NULL;
	}

	public void initializeRandomMap() {
		clear();
		Random r = new Random();
		insertOctRandom(r, 4);
		insertOctRandom(r, 3);
		insertOctRandom(r, 3);
		insertOctRandom(r, 2);
		insertOctRandom(r, 2);
		insertOctRandom(r, 2);
		insertOctRandom(r, 1);
		insertOctRandom(r, 1);
		insertOctRandom(r, 1);
		insertOctRandom(r, 1);
	}

	private void clear() {
		for (int i = 0; i < DIM_MAPPA; i++)
			for (int j = 0; j < DIM_MAPPA; j++)
				mappa[i][j] = NULL;

	}

	public boolean insertOct(int x, int y, int dim, int dir) {
		if (dir == 1 && x + dim > DIM_MAPPA) {
			return false;
		} // vertical
		if (dir == 0 && y + dim > DIM_MAPPA) {
			return false;
		} // horizontal 
		boolean insert;

		if (dir == 0)
			insert = checkHorizontal(x, y, dim);
		else
			insert = checkVertical(x, y, dim);

		if (!insert)
			return false;
		if (dir == 0) {
			OctPos n = new OctPos(x, y, x, y + dim - 1);
			listOct.add(n);
		} else {
			OctPos n = new OctPos(x, y, x + dim - 1, y);
			listOct.add(n);
		}
		for (int i = 0; i < dim; i++) {
			if (dir == 0) {
				mappa[x][y + i] = OCTOPUS;
			} else
				mappa[x + i][y] = OCTOPUS;
		}
		return true;
	}

	public int[] insertOctRandom(Random random, int dimention) {
		boolean insert;
		int[] dati = new int[4];
		int direction, row, column;
		do {
			insert = true;
			direction = random.nextInt(2); // 0=Horizontal, 1=Vertical
			if (direction == 0) {
				column = random.nextInt(DIM_MAPPA - dimention + 1);
				row = random.nextInt(DIM_MAPPA);
			} else {
				column = random.nextInt(DIM_MAPPA);
				row = random.nextInt(DIM_MAPPA - dimention + 1);
			}
			if (direction == 0)
				insert = checkHorizontal(row, column, dimention);
			else
				insert = checkVertical(row, column, dimention);
		} while (!insert);
		if (direction == 0) {
			OctPos n = new OctPos(row, column, row, column + dimention - 1);
			listOct.add(n);
		} else {
			OctPos n = new OctPos(row, column, row + dimention - 1, column);
			listOct.add(n);
		}
		for (int i = 0; i < dimention; i++) {
			if (direction == 0) {
				mappa[row][column + i] = OCTOPUS;
			} else
				mappa[row + i][column] = OCTOPUS;
		}
		dati[0] = row;
		dati[1] = column;
		dati[2] = dimention;
		dati[3] = direction;
		return dati;
	}

	public boolean checkVertical(int row, int column, int dimention) {
		if (row != 0)
			if (mappa[row - 1][column] == OCTOPUS)
				return false;
		if (row != DIM_MAPPA - dimention)// finish place octopus
			if (mappa[row + dimention][column] == OCTOPUS)
				return false;
		for (int i = 0; i < dimention; i++) {
			if (column != 0)
				if (mappa[row + i][column - 1] == OCTOPUS)
					return false;
			if (column != DIM_MAPPA - 1)
				if (mappa[row + i][column + 1] == OCTOPUS)
					return false;
			if (mappa[row + i][column] == OCTOPUS)
				return false;
		}
		return true;
	}

	public boolean checkHorizontal(int row, int column, int dimention) {
		if (column != 0)
			if (mappa[row][column - 1] == OCTOPUS)
				return false;
		if (column != DIM_MAPPA - dimention)
			if (mappa[row][column + dimention] == OCTOPUS)
				return false;
		for (int i = 0; i < dimention; i++) {
			if (row != 0)
				if (mappa[row - 1][column + i] == OCTOPUS)
					return false;
			if (row != DIM_MAPPA - 1)
				if (mappa[row + 1][column + i] == OCTOPUS)
					return false;
			if (mappa[row][column + i] == OCTOPUS)
				return false;
		}
		return true;
	}

	public boolean hitt(Position p) {
		int row = p.getCoordX();
		int column = p.getCoordY();
		if (mappa[row][column] == OCTOPUS) {
			mappa[row][column] = HIT;
			return true;
		}
		mappa[row][column] = ACQUA;
		return false;
	}

	public OctPos sunk(Position p) {
		int row = p.getCoordX();
		int col = p.getCoordY();
		OctPos ship = null;
		for (int i = 0; i < listOct.size(); i++) {
			if (listOct.get(i).checkCoord(row, col)) {
				ship = listOct.get(i);
				break;
			}
		}
		for (int i = ship.getXin(); i <= ship.getXfin(); i++) {
			for (int j = ship.getYin(); j <= ship.getYfin(); j++) {
				if (mappa[i][j] != HIT) {
					return null;
				}
			}
		}
		listOct.remove(ship);
		return ship;
	}

	public void setAcqua(Position p) {
		mappa[p.getCoordX()][p.getCoordY()] = ACQUA;
	}

	public boolean acqua(Position p) {
		return mappa[p.getCoordX()][p.getCoordY()] == ACQUA;
	}

	public boolean hit(Position p) {
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

	public void setAdvOctopus(LinkedList<int[]> advOctopus) {
		listOct.clear();
		for (int[] a : advOctopus) {
			insertOct(a[0], a[1], a[2], a[3]);
			System.out.println("Insert Octopus" + a[0] + a[1] + a[2] + a[3]);
		}
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++)
				System.out.print(mappa[i][j]);
			System.out.println("");
		}
	}
}
