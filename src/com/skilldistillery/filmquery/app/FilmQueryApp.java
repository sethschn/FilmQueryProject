package com.skilldistillery.filmquery.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) throws SQLException {
		FilmQueryApp app = new FilmQueryApp();
		//app.test();
		app.launch();
	}

	private void test() throws SQLException {
		Film film = db.findFilmById(1);
		if (film != null) {
			System.out.println(film);
			System.out.println(film.getActorList());
		} else {
			System.out.println("Film is null");
		}
		Actor actor = db.findActorById(2);
		if (actor != null) {
			System.out.println(actor);
		} else {
			System.out.println("Actor is null");
		}
		List<Film> filmList = new ArrayList();
		filmList = db.findFilmBySearchKeyword("zorro");
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println(filmList);
	}

	private void launch() throws SQLException {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) throws SQLException {
		int choice = 0;
		System.out.println("\nFilm Query");
		System.out.println("1) Look up a film by its ID");
		System.out.println("2) Look up a film by a searchword");
		System.out.println("3) Quit the program");
		System.out.print("Choice: ");
		try {
			choice = input.nextInt();
			if (choice < 1 || choice > 3) {
				System.out.println("please enter a number higher than 0 and lower than 4");
				startUserInterface(input);
			}
		} catch (Exception e) {
			System.out.println("Invalid input ");
			input.nextLine();
			startUserInterface(input);

		}
		switch(choice) {
		case 1:
			getUserFilmIdInput(input);
			break;
		case 2:
			getUserSearchWord(input);
			break;
		case 3:
			System.out.println("Quitting");
			break;	
		}

	}

	private void getUserSearchWord(Scanner input) throws SQLException {
		String userInput = null;
		System.out.println("\nSearch for film by keyword");
		System.out.print("Enter a search word: ");
		try {
			userInput = input.next();
			if (userInput != null && !userInput.equals("")) {
				System.out.println("Searching for film");
				List<Film> filmList = db.findFilmBySearchKeyword(userInput);
				if (filmList.size() > 0) {			
					for (Film filmObj : filmList) {
						System.out.println("Title: "+filmObj.getTitle());					
						System.out.println("Language: "+filmObj.getLanguage());					
						System.out.println("Rating: "+filmObj.getRating());					
						System.out.println("Description: "+filmObj.getDescription());	
						System.out.println("Cast: "+filmObj.getActorList());
					}
				}else {
					System.out.println("No films found");
				}
				input.nextLine();
			}else {
				System.out.println("user input is null");				
			}
		} catch (Exception e) {
			System.out.println("Invalid input ");
			input.nextLine();
		}
		startUserInterface(input);	
		
	}

	private void getUserFilmIdInput(Scanner input) throws SQLException {
		int choice = 0;
		System.out.println("\nSearch for film by ID");
		System.out.print("Enter a film id: ");
		try {
			choice = input.nextInt();
			if (choice > 0) {
				Film filmObj = db.findFilmById(choice);
				if (filmObj == null) {
					System.out.println("Film not found");
				}else {
					//its title, year, rating, and description are displayed.
					System.out.println("Title: "+filmObj.getTitle());
					System.out.println("Language: "+filmObj.getLanguage());					
					System.out.println("Rating: "+filmObj.getRating());					
					System.out.println("Description: "+filmObj.getDescription());
					System.out.println("Cast: "+filmObj.getActorList());

				}
			}else {
				System.out.println("No films at 0");				
			}
		} catch (Exception e) {
			System.out.println("Invalid input ");
			input.nextLine();
		}
		startUserInterface(input);
		
	}

}
