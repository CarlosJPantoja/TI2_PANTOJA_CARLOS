package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import exceptions.*;
import model.Matrix;

public class Menu {

	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	private Matrix matrix;

	public Menu(){}

	public void showMenu() throws IOException{
		try {
			System.out.println();
			System.out.println("    _/_/_/      _/_/    _/    _/  _/      _/    _/_/_/  _/_/_/  _/      _/    _/_/_/   ");
			System.out.println("   _/    _/  _/    _/  _/    _/  _/_/    _/  _/          _/    _/_/    _/  _/          ");
			System.out.println("  _/_/_/    _/    _/  _/    _/  _/  _/  _/  _/          _/    _/  _/  _/  _/  _/_/     ");
			System.out.println(" _/    _/  _/    _/  _/    _/  _/    _/_/  _/          _/    _/    _/_/  _/    _/      ");
			System.out.println("_/_/_/      _/_/      _/_/    _/      _/    _/_/_/  _/_/_/  _/      _/    _/_/_/       ");
			System.out.println("                                                                                       ");
			System.out.println("                                                                                       ");
			System.out.println("                                           _/          _/_/      _/_/_/  _/_/_/_/  _/_/_/      _/_/_/   ");
			System.out.println("                                          _/        _/    _/  _/        _/        _/    _/  _/          ");
			System.out.println("                                         _/        _/_/_/_/    _/_/    _/_/_/    _/_/_/      _/_/       ");
			System.out.println("                                        _/        _/    _/        _/  _/        _/    _/        _/      ");
			System.out.println("                                       _/_/_/_/  _/    _/  _/_/_/    _/_/_/_/  _/    _/  _/_/_/         ");
			System.out.println();
			System.out.println("[1] PLAY");
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
				System.out.println("Thanks for playing!");
				break;

			default:
				throw new NumberFormatException();
			}
		} catch(NumberFormatException e) {
			System.out.println("\nThe option entered is invalid, please try again\n");
			showMenu();
		}
	}

	private void startGame() throws IOException {
		try {
			System.out.println("\nEnter: nickname columns rows mirrors");
			String[] firstLine = br.readLine().split(" ");
			String nickName = firstLine[0];
			int columns = Integer.parseInt(firstLine[1]);
			if(columns>26) {
				throw new AmountLimit("\nThe amount of columns cannot exceed 26\n");
			} else if(columns<1) {
				throw new AmountLimit("\nThe amount of columns cannot be less than 1\n");
			}
			int rows = Integer.parseInt(firstLine[2]);
			if(rows<1) {
				throw new AmountLimit("\nThe amount of rows cannot be less than 1\n");
			}
			int mirrors = Integer.parseInt(firstLine[3]);
			if(mirrors>=(rows*columns)) {
				throw new AmountLimit("\nThe amount of mirrors cannot be or exceed the number of cells in the matrix\n");
			} else if(mirrors<1) {
				throw new AmountLimit("\nThe amount of mirrors cannot be less than 1\n");
			}
			matrix = new Matrix(rows, columns, mirrors, nickName);
			System.out.println(matrix.getRemaining()+matrix.toString());
			waitCommand();
		} catch(AmountLimit e) {
			System.out.print(e.getMessage());
			startGame();
		} catch(NumberFormatException | ArrayIndexOutOfBoundsException e) {
			System.out.print("\nYou must enter the information in the required format\n");
			startGame();
		}
	}

	private void waitCommand() throws IOException {
		String msg = "";
		try {
			String command = br.readLine();
			if(command.equals("menu")) {
				System.out.println("\n");
				showMenu();
				return;
			} else if(command.equals("cheat")) {
				msg = matrix.getRemaining()+matrix.showMirrors();
			} else if(command.length()>=4 && command.charAt(0)=='L' && (command.charAt(command.length()-1)=='R'||command.charAt(command.length()-1)=='L') && (command.charAt(command.length()-2)-65<=matrix.getColumns()-1 && command.charAt(command.length()-2)-65>=0)){
				int row = Integer.parseInt(command.substring(1, command.length()-2))-1;
				if(row>matrix.getRows()-1 || row<0) {
					throw new NumberFormatException();
				}
				int column = command.charAt(command.length()-2)-65;
				String orientation = "";
				if(command.charAt(command.length()-1)=='R') {
					orientation = "R";
				}else {
					orientation = "L";
				}
				msg = matrix.locateMirror(row, column, orientation);
				msg = matrix.getRemaining()+msg;
			} else if(command.length()>=3 && (command.charAt(command.length()-1)=='H'||command.charAt(command.length()-1)=='V') && (command.charAt(command.length()-2)-65<=matrix.getColumns()-1 && command.charAt(command.length()-2)-65>=0)){
				int row = Integer.parseInt(command.substring(0, command.length()-2))-1;
				if(row>matrix.getRows()-1 || row<0) {
					throw new NumberFormatException();
				}
				int column = command.charAt(command.length()-2)-65;
				String orientation = "";
				if(command.charAt(command.length()-1)=='H') {
					orientation = "H";
				}else {
					orientation = "V";
				}
				msg = matrix.getRemaining()+matrix.shootLaser(row, column, orientation);
			} else if(command.length()>=2 && (command.charAt(command.length()-1)-65<=matrix.getColumns()-1 && command.charAt(command.length()-1)-65>=0)){
				int row = Integer.parseInt(command.substring(0, command.length()-1))-1;
				if(row>matrix.getRows()-1 || row<0) {
					throw new NumberFormatException();
				}
				int column = command.charAt(command.length()-1)-65;
				msg = matrix.getRemaining()+matrix.shootLaser(row, column, "N");
			} else {
				throw new NumberFormatException();
			}
		} catch(NumberFormatException e) {
			msg = "\nThe command entered is invalid\n";
			msg += matrix.getRemaining()+matrix.toString();
		} catch(AmountLimit e) {
			msg = e.getMessage();
			msg += matrix.getRemaining()+matrix.toString();
		}
		System.out.println(msg);
		waitCommand();
	}
}
