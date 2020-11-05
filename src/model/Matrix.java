package model;

import java.util.Random;

import exceptions.AmountLimit;

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
	
	public String shootLaser(int i, int j, String ort) throws AmountLimit{
		boolean border = !(i==0||i+1==rows||j==0||j+1==columns);
		if(border) {
			throw new AmountLimit("\nCan only be shot from an edge or a corner\n");
		}
		Node current = searchNode(i, j);
		boolean corner = (i==0||i+1==rows)&&(j==0||j+1==columns);
		boolean orientation = true;
		boolean direction = false;
		if(!corner) {
			if(i==0||i+1==rows) {
				orientation = false;
			}
			if(i==0||j==0) {
				direction = true;
			}
		} else {
			if(!(ort.equals("H")||ort.equals("V"))) {
				throw new AmountLimit("\nAs it is a corner, it must indicate the direction of the shot\n");
			}
			if(ort.equals("V")) {
				orientation = false;
			}
			if(i==0&&j==0) {
				direction = true;
			} else if(i+1==rows&&j==0 && ort.equals("H")) {
				direction = true;
			} else if(i==0&&j+1==columns && ort.equals("V")) {
				direction = true;
			}
		}
		Node exit = travelLaser(orientation, direction, current);
		String matrixDraw = toString();
		matrixDraw = personalizeDraw(matrixDraw, current, "S");
		matrixDraw = personalizeDraw(matrixDraw, exit, "E");
		return matrixDraw;
	}
	
	private String personalizeDraw(String matrixDraw, Node current, String symbol) {
		matrixDraw = matrixDraw.replace('\n', 'L').replace((char)92, 'B');
		int previusText = (3*columns*current.getRow())+(3*current.getColumn())+(current.getRow()+2);
		String firstPart = matrixDraw.substring(0, previusText);
		String secondPart = matrixDraw.substring(previusText+1, matrixDraw.length());
		String message = firstPart+symbol+secondPart;
		message = message.replace('L', '\n').replace('B', (char)92);
		return message;
	}
	
	private Node travelLaser(boolean orientation, boolean direction, Node current) {
		int i = current.getRow();
		int j = current.getColumn();
		if(current.getLeftM()||current.getRightM()) {
			if(orientation) {
				orientation = false;
			} else {
				orientation = true;
			}
			if(current.getRightM()) {
				if(direction) {
					direction = false;
				} else {
					direction = true;
				}
			}
			current = chosseDirection(orientation, direction, current);
		} else{
			current = chosseDirection(orientation, direction, current);
		}
		if(!(current.getRow()==i && current.getColumn()==j)) {
			current = travelLaser(orientation, direction, current);
		}
		return current;
	}
	
	private Node chosseDirection(boolean orientation, boolean direction, Node current) {
		if(orientation) {
			if(direction) {
				if(current.getNextN()!=null) {
					current = current.getNextN();
				}
			} else {
				if(current.getPrevN()!=null) {
					current = current.getPrevN();
				}
			}
		} else {
			if(direction) {
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

	public String locateMirror(int i, int j, String orientation) {
		Node current = searchNode(i, j);
		if((orientation.equals("R") && current.getRightM()) || (orientation.equals("L") && current.getLeftM())) {
			current.setViewM(true);
		} 
		String message = toString();
		if(!current.getViewM()) {
			message = personalizeDraw(message, current, "X");
		}
		return message;
	}

	public int getColumns() {
		return columns;
	}

	public int getRows() {
		return rows;
	}
}
