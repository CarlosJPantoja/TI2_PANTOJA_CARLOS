package model;

import java.util.Random;

public class Matrix {
	
	private int rows;;
	private int columns;
	private int mirrors;
	private int remainingM;
	private String msg;
	
	private Node firstNode;
	
	public Matrix(int r, int c, int k) {
		rows = r;
		columns = c;
		mirrors = k;
		createMatrix();
		assignMirrors();
		setLinks();
		shotLaser(0, 2, 0);
	}
	
	private void createMatrix() {
		firstNode = new Node(0, 0);
		createRow(0, 0, firstNode);
	}

	private void createRow(int i, int j, Node firstRow) {
		createColumn(i, j+1, firstRow);
		if(i+1<rows) {
			Node downFirstRow = new Node(i+1, j);
			downFirstRow.setUpN(firstRow);
			firstRow.setDownN(downFirstRow);
			createRow(i+1, j, downFirstRow);
		}
	}

	private void createColumn(int i, int j, Node prevNode) {
		if(j<columns) {
			Node current = new Node(i, j);
			current.setPrevN(prevNode);
			prevNode.setNextN(current);
			createColumn(i, j+1, current);
		}
	}
	
	private void setLinks() {
		if(firstNode!=null && firstNode.getDownN()!=null) {
			setRowsL(firstNode, firstNode.getDownN());
		}
	}
	
	private void setRowsL(Node current, Node downCurrent) {
		setColumnsL(current, downCurrent);
		if(downCurrent.getDownN()!=null) {
			current = downCurrent;
			downCurrent = downCurrent.getDownN();
			setRowsL(current, downCurrent);
		}
	}
	
	private void setColumnsL(Node current, Node downCurrent) {
		if(current.getNextN()!=null && downCurrent.getNextN()!=null) {
			current = current.getNextN();
			downCurrent = downCurrent.getNextN();
			downCurrent.setUpN(current);
			current.setDownN(downCurrent);
			setColumnsL(current, downCurrent);
		}
	}
	
	@Override
	public String toString() {
		msg = "";
		printRow(firstNode);
		return msg;
	}
	
	private void printRow(Node current) {
		printColumn(current);
		if(current.getDownN() != null){
			current = current.getDownN();
			printRow(current);
		}
	}
	
	private void printColumn(Node current) {
		if(current.getColumn()==0) {
			msg += "\n";
		}
		if(current.getRightM() && current.getViewM()) {
			msg += "[/]";
		} else if(current.getLeftM() && current.getViewM()) {
			msg += "[\\]";
		} else {
			msg += "[ ]";
		}
		if(current.getNextN() != null) {
			current = current.getNextN();
			printColumn(current);
		}
	}
	
	private void assignMirrors() {
		remainingM = 0;
		assignRowsM(firstNode);
		
	}
	
	private void assignRowsM(Node current) {
		if(remainingM<mirrors) {
			assignColunmM(current);
			if(current.getDownN() != null){
				current = current.getDownN();
				assignRowsM(current);
			} else {
				if(remainingM<mirrors){
					current = firstNode;
					assignRowsM(current);
				}
			}
		}
	}
	
	private void assignColunmM(Node current) {
		if(remainingM<mirrors) {
			Random random = new Random();
			boolean setMirror = random.nextBoolean();
			if(setMirror && !current.getRightM() && !current.getLeftM()) {
				setMirror = random.nextBoolean();
				if(setMirror) {
					current.setRightM(true);
				} else {
					current.setLeftM(true);
				}
				remainingM++;
			}
			if(current.getNextN() != null) {
				current = current.getNextN();
				assignColunmM(current);
			}
		}
	}

	public void showMirrors() {
		revealRowsM(firstNode);
	}

	private void revealRowsM(Node current) {
		revealColumnsM(current);
		if(current.getDownN() != null){
			current = current.getDownN();
			revealRowsM(current);
		}
	}

	private void revealColumnsM(Node current) {
		current.setViewM(true);
		if(current.getNextN() != null) {
			current = current.getNextN();
			revealColumnsM(current);
		}
	}
	
	public void shotLaser(int i, int j, int h) {
		Node current = searchNode(i, j);
		boolean horizontal = false;
		boolean rigth = false;
		boolean down = false;
		if(i==0) {
			horizontal = false;
			down = true;
		} else if(i+1==rows) {
			horizontal = false;
			down = false;
		} else if(j==0) {
			horizontal = true;
			rigth = true;
		} else if(j+1==columns) {
			horizontal = true;
			rigth = false;
		}
		Node exit = shotLaser(horizontal, rigth, down, current);
		String matrixDraw = toString();
		int n = columns*3*(exit.getRow());
		int x = (3*exit.getColumn())+1;
		String first = matrixDraw.substring(0, n+x+1);
		String second = matrixDraw.substring(n+x+1+1, matrixDraw.length());
		System.out.println(first+"E"+second);
	}
	
	public Node shotLaser(boolean horizontal, boolean rigth, boolean down, Node current) {
		int i = current.getRow();
		int j = current.getColumn();
		if(current.getLeftM()||current.getRightM()) {
			if(current.getRightM()) {
				if(horizontal) {
					if(rigth) {
						horizontal = false;
						down = false;
					} else {
						horizontal = false;
						down = true;
					}
				} else {
					if(down) {
						horizontal = true;
						rigth = false;
					} else {
						horizontal = true;
						rigth = true;
					}
				}
			} else {
				if(horizontal) {
					if(rigth) {
						horizontal = false;
						down = true;
					} else {
						horizontal = false;
						down = false;
					}
				} else {
					if(down) {
						horizontal = true;
						rigth = true;
					} else {
						horizontal = true;
						rigth = false;
					}
				}
			}
			current = chosseDirection(horizontal, rigth, down, current);
		} else{
			current = chosseDirection(horizontal, rigth, down, current);
		}
		if(!(current.getRow()==i&&current.getColumn()==j)) {
			current = shotLaser(horizontal, rigth, down, current);
		}
		return current;
	}
	
	public Node chosseDirection(boolean horizontal, boolean rigth, boolean down, Node current) {
		if(horizontal) {
			if(rigth) {
				if(current.getNextN()!=null) {
					current = current.getNextN();
				}
			} else {
				if(current.getPrevN()!=null) {
					current = current.getPrevN();
				}
			}
		} else {
			if(down) {
				if(current.getDownN()!=null) {
					current = current.getDownN();
				}
			} else {
				if(current.getUpN()!=null) {
					current = current.getUpN();
				}
			}
		}
		return current;
	}
	
	private Node searchNode(int i, int j) {
		Node current = searchRowsN(i, j, firstNode);
		return current;
	}

	private Node searchRowsN(int i, int j, Node current) {
		current = searchColumnsN(i, j, current);
		if(current.getRow()<i) {
			current = current.getDownN();
			current = searchRowsN(i, j, current);
		}
		return current;
	}

	private Node searchColumnsN(int i, int j, Node current) {
		if(current.getColumn()<j) {
			current = current.getNextN();
			current = searchColumnsN(i, j, current);
		}
		return current;
	}
}
