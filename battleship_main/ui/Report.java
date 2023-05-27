//Some general purpose Boolean flags for the different states of gameplay
package battleship_main.ui;
public class Report {
	private Position p;
	private boolean hit;
	private boolean dead;
	
	public Report(){
	}
	
	public Report(Position p, boolean hit, boolean dead) {
		this.p = p;
		this.hit = hit;
		this.dead = dead;
	}
	public Position getP() {
		return p;
	}
	public void setP(Position p) {
		this.p = p;
	}
	public boolean isHit() {
		return hit;
	}
	public void setHit(boolean hit) {
		this.hit = hit;
	}
	public boolean isDead() {
		return dead;
	}
	public void setDead(boolean dead) {
		this.dead = dead;
	}	
	public String toString(){
		return "coordinate:"+p+" attack:"+hit+" dead:"+ dead;
	}
}
