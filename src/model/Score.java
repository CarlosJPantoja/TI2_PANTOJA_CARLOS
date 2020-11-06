package model;

public class Score {
	
	private String nickName;
	private int score;
	private int columns;
	private int rows;
	private int mirrors;
	
	private Score father;
	private Score right;
	private Score left;

	public Score(String nick, int q, int n, int m, int k) {
		nickName = nick;
		score = q;
		columns = n;
		rows = m;
		mirrors = k;
	}

	public Score getFather() {
		return father;
	}

	public void setFather(Score f) {
		father = f;
	}

	public Score getRight() {
		return right;
	}

	public void setRight(Score r) {
		right = r;
	}

	public Score getLeft() {
		return left;
	}

	public void setLeft(Score l) {
		left = l;
	}

	public int getScore() {
		return score;
	}

	public String getNickName() {
		return nickName;
	}

	public int getColumns() {
		return columns;
	}

	public int getRows() {
		return rows;
	}

	public int getMirrors() {
		return mirrors;
	}
}
