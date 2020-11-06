package model;

public class PositionsTable {
	
	private String msg;
	
	private Score root;
	
	private int cont;
	
	public PositionsTable() {
		root = null;
	}
	
	public void addScore(String name, int qualification, int n, int m, int k) {
		Score newScore = new Score(name, qualification, n, m, k);
		if(root==null) {
			root = newScore;
		} else {
			addScore(root, newScore);
		}
	}
	
	private void addScore(Score current, Score newScore) {
		if(current.getScore()>newScore.getScore()) {
			if(current.getLeft()==null) {
				current.setLeft(newScore);
				newScore.setFather(current);
			} else {
				addScore(current.getLeft(), newScore);
			}
		} else {
			if(current.getRight()==null) {
				current.setRight(newScore);
				newScore.setFather(current);
			} else {
				addScore(current.getRight(), newScore);
			}
		}
	}
	
	@Override
	public String toString() {
		msg = "";
		cont = 1;
		printPositions(root);
		if(root==null) {
			msg = "\nScores have not been saved yet";
		}
		return msg;
	}

	private void printPositions(Score current) {
		if(current!=null) {
			printPositions(current.getLeft());
			msg += "\n"+cont+"th "+current.getNickName()+": "+current.getScore()+" point, with a Matrix: "+current.getRows()+"x"+current.getColumns()+" and "+current.getMirrors()+" mirrors";
			cont++;
			printPositions(current.getRight());
		}
	}
}
