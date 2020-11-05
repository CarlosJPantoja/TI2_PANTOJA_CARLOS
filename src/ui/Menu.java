package ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import model.Matrix;

public class Menu {
	
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public Menu(){
		
	}

	public void showMenu() throws IOException{
		System.out.println("BOUNCING LASERS");
		System.out.println("[1] START");
		System.out.println("[2] SCORES");
		System.out.println("[3] EXIT");
		int option = Integer.parseInt(br.readLine());
		switch(option) {
		case 1:
			startGame();
			break;
			
		case 2:
			
			break;
			
		case 3:
			
			break;
		}
	}

	private void startGame() throws IOException {
		System.out.println("\nEnter: nickname columns rows mirrors");
		String[] firstLine = br.readLine().split(" ");
		String nickName = firstLine[0];
		int columns = Integer.parseInt(firstLine[1]);
		int rows = Integer.parseInt(firstLine[2]);
		int mirrors = Integer.parseInt(firstLine[3]);
		System.out.print("\nMatrix "+rows+"x"+columns);
		Matrix matrix = new Matrix(rows, columns, mirrors);
		System.out.println(matrix.toString());
		waitCommand(matrix);
	}
	
	private void waitCommand(Matrix matrix) throws IOException {
		String msg = "";
		String command = br.readLine();
		if(command.equals("menu")) {
			showMenu();
			return;
		} else if(command.equals("cheat")) {
			matrix.showMirrors();
			System.out.println(matrix.toString());
			waitCommand(matrix);
		} else if(command.equals("LR")){
			msg = matrix.locateMirror(true);
		} else if(command.equals("LL")) {
			msg = matrix.locateMirror(false);
		} else if(command.length()>=3){
			char lastedChar = command.charAt(command.length()-1);
			char penultimateChar = command.charAt(command.length()-2);
			boolean direction = (lastedChar=='H'||lastedChar=='V')&&(penultimateChar<=90&&penultimateChar>=65);
			if(direction) {
				int row = Integer.parseInt(command.substring(0, command.length()-2))-1;
				int column = penultimateChar-64;
				boolean orientation = false;
				if(lastedChar=='H') {
					orientation = false;
				}
				msg = matrix.shootLaser(row, column, true);
			}
		}
		System.out.println(msg);
	}
}
