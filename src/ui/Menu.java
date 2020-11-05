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
		String[] firstLine = br.readLine().split(" ");
		String nickName = firstLine[0];
		int columns = Integer.parseInt(firstLine[1]);
		int rows = Integer.parseInt(firstLine[2]);
		int mirrors = Integer.parseInt(firstLine[3]);
		Matrix m = new Matrix(rows, columns, mirrors);
		System.out.println(m.toString());
		waitCommand(m);
	}
	
	private void waitCommand(Matrix m) throws IOException {
		String command = br.readLine();
		if(command.equals("menu")) {
			showMenu();
			return;
		} else if(command.equals("cheat")) {
			m.showMirrors();
			System.out.println(m.toString());
			waitCommand(m);
		} else{
			
		}
	}
}
