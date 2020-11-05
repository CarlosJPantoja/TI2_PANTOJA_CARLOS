package model;

public class Node {
	
	private int row;
	private int column;
	private boolean rightM;
	private boolean leftM;
	private boolean viewM;
	
	private Node prevN;
	private Node nextN;
	private Node upN;
	private Node downN;
	
	public Node(int r, int c) {
		row = r;
		column = c;
		rightM = false;
		leftM = false;
		viewM = false;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}

	public boolean getRightM() {
		return rightM;
	}

	public void setRightM(boolean right) {
		rightM = right;
	}

	public boolean getLeftM() {
		return leftM;
	}

	public void setLeftM(boolean left) {
		this.leftM = left;
	}
	
	public boolean getViewM() {
		return viewM;
	}

	public void setViewM(boolean view) {
		viewM = view;
	}

	public Node getPrevN() {
		return prevN;
	}

	public void setPrevN(Node prev) {
		prevN = prev;
	}

	public Node getNextN() {
		return nextN;
	}

	public void setNextN(Node next) {
		nextN = next;
	}

	public Node getUpN() {
		return upN;
	}

	public void setUpN(Node up) {
		upN = up;
	}

	public Node getDownN() {
		return downN;
	}

	public void setDownN(Node down) {
		downN = down;
	}
}
