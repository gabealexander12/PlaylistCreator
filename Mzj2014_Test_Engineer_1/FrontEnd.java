package project4;

////--== CS400 File Header Information ==--
////Name: Gabriel Alexander
////Email: gmalexander@wisc.edu
////Team: Team: BB
////TA: Bri
////Lecturer: Gary Dahl
////Notes to Grader: <optional extra notes>
import java.util.Scanner;

public class FrontEnd {
	private static Scanner sc = new Scanner(System.in);

// calls driver to begin program and closes scanner when program is finished.
	public static void main(String[] args) {
		driver();
		sc.close();
	}

// runs the program
	public static void driver() {
		System.out.print("Welcome to the Playlist Builder!\n"
				+ "To begin, you can either create a library from scratch or \n" + "load a provided song library. \n"
				+ "To create a library, enter: \"create\"\n" + "To load a provided library, enter: \"load\"");
		boolean validResponse = false;
		do {
			switch (sc.nextLine()) {
			case "create":
				validResponse = true;
				BackEndFinalProject.initializeLibrary(null);
				System.out.println("\nSong library has been created from scratch.");
				break;
			case "load":
				validResponse = true;
				BackEndFinalProject.initializeLibrary("src/music.csv");
				System.out.println("\nExisting song library has been loaded for your use.");
				System.out.println(BackEndFinalProject.songLibrary.toString());
				break;
			default:
				System.out.println("\nInvalid instruction entered. Enter either \"create\" or \"load\"");
				break;
			}
		} while (!validResponse);
		executeUserCommands();

	}

// attempts to insert a song, returns true if successful and false otherwise.
	protected static boolean insertSong() {
		System.out.println("Enter name of song to be added:");
		String songName = sc.nextLine().trim();
		System.out.print("\nPlease enter the artist:");
		String artist = sc.nextLine().trim();
		System.out.print("\nPlease enter the genre:");
		String genre = sc.nextLine().trim();
		System.out.print("\nPlease enter the album:");
		String album = sc.nextLine().trim();
		System.out.print("\nPlease enter the length in seconds:");
		double length = sc.nextDouble();
		System.out.print("\nPlease enter the year released:");
		int year = sc.nextInt();
		Song songToAdd = new Song(songName, artist, genre, length, year, album);
		if (getSong(songName, artist)) {
			return false;
		}
		BackEndFinalProject.songLibrary.put(artist + songName, songToAdd);
		return true;
	}

// checks to see if the song already exists in the song library
	public static boolean getSong(String song, String artist) {
		Song songToGet = BackEndFinalProject.songLibrary.get(artist + song);
		if (songToGet != null) {
			return true;
		}
		return false;
	}

// attempts to remove a song, returns true if successful and false otherwise.
	protected static boolean removeSong() {
		System.out.println("Enter name of song to be removed:");
		String songToBeRemoved = sc.nextLine();
		System.out.println("Enter artist of song to be removed:");
		String artistToBeRemoved = sc.nextLine();
		Song songToFind = BackEndFinalProject.songLibrary.get(artistToBeRemoved + songToBeRemoved);
		return BackEndFinalProject.songLibrary.remove(artistToBeRemoved + songToBeRemoved, songToFind);
	}

// executes the user's requested command.
	protected static void executeUserCommands() {
		printCommands();
		boolean quit = false;
		do {
			switch (sc.nextLine()) {
			case "insert":
				Boolean successAdd = insertSong();
				if (successAdd) {
					System.out.println("Song has been successfully added!");
				} else {
					System.out.println("Song may already exist in the music library!");
				}
				break;
			case "remove":
				Boolean successRemoved = removeSong();
				if (successRemoved) {
					System.out.print("\nThe song was removed!");
				} else {
					System.out.print("\nThe song was not removed, it may not exist in the library");
				}
				break;
			case "playlist":
				createHelper();
				break;
			case "help":
				printCommands();
				break;

			case "quit":
				System.out.println("\nWe hope you enjoyed using the Playlist Builder! Goodbye!");
				sc.nextLine(); // "consume remaining input"
				quit = true;
				break;
			default:
				System.out.println("\nInvalid instruction entered. Printing list of valid instructions.");
				printCommands();
				break;
			}
		} while (!quit);

	}

// method that executed the playlist creator functionality
	protected static void createHelper() {
		System.out.print("\nTo begin creating a playlist, you must choose one of the following criteria:\n"
				+ "- \"released between two years\" (playlist will only contain songs released between two user provided years)\n"
				+ "- \"genre\" (playlist will only contain songs in user provided genre)\n"
				+ "- \"artist\" (playlist will only contain songs by the user provided artist)\n"
				+ "- \"select years\" (playlist will only contain songs in select years)\n"
				+ "- \"quit\" (if you are finished building your playlist)\n");
		Boolean quit = false;
		do {

			switch (sc.nextLine()) {
			case "released between two years":
				twoYearsCriteria();
				System.out.println("Would you like to view your current playlist? (yes/no)\n");
				if (sc.nextLine().equals("yes")) {
					System.out.println("\nYour current playlist:\n");
					System.out.println(BackEndFinalProject.getOrganizedPlaylist());
					printPlaylistCommands();
				}
				break;
			case "genre":
				genreCriteria();
				System.out.println("Would you like to view your current playlist? (yes/no)\n");
				if (sc.nextLine().equals("yes")) {
					System.out.println("\nYour current playlist:\n");
					System.out.println(BackEndFinalProject.getOrganizedPlaylist());
					printPlaylistCommands();
				}
				break;
			case "artist":
				artistCriteria();
				System.out.println("Would you like to view your current playlist? (yes/no)\n");
				if (sc.nextLine().equals("yes")) {
					System.out.println("\nYour current playlist:\n");
					System.out.println(BackEndFinalProject.getOrganizedPlaylist());
					printPlaylistCommands();
				}
				break;
			case "select years":
				selectYearCriteria();
				System.out.println("Would you like to view your current playlist? (yes/no)\n");
				if (sc.nextLine().equals("yes")) {
					System.out.println("\nYour current playlist:\n");
					System.out.println(BackEndFinalProject.getOrganizedPlaylist());
					printPlaylistCommands();
				}
				break;
			case "quit":
				quit = true;
				break;
			default:
				System.out.println("\nInvalid instruction entered.");
				printPlaylistCommands();
				break;
			}
		} while (!quit);

	}

// filters playlist between songs released between two years
	protected static void twoYearsCriteria() {
		System.out.print("\nEnter earliest year:");
		Integer earliestYear = sc.nextInt();
		System.out.print("\nEnter latest year:");
		Integer latestYear = sc.nextInt();
		BackEndFinalProject.Filter.setStartYear(earliestYear);
		BackEndFinalProject.Filter.setEndYear(latestYear);
	}

// filters playlist according to the artist criteria
	protected static void artistCriteria() {
		System.out.print("\nYou may enter up to 5 artists:");
		Boolean quit = false;
		int artists = 0;
		do {
			if (artists >= 5) {
				System.out.print("You have entered the maximum number of artists. Type \"quit\"");
			} else {
				System.out.print("\nEnter an artist name or type \"quit\" if you are done entering artists:");
			}
			String artistName = sc.nextLine().trim();
			switch (artistName) {
			case ("quit"):
				quit = true;
				break;
			default:
				++artists;
				BackEndFinalProject.Filter.addArtist(artistName);
				break;
			}
		} while (!quit);
		artists = 0;
	}

// filters playlist according to select years
	protected static void selectYearCriteria() {
		System.out.print("\nYou may enter up to 5 select years:");
		Boolean quit = false;
		int years = 0;
		do {
			if (years >= 5) {
				System.out.print("You have entered the maximum number of years. Type \"quit\"");
			} else {
				System.out.print("\nEnter a year or type \"quit\" if you are done entering years:");
			}

			String year = sc.nextLine().trim();
			switch (year) {
			case ("quit"):
				quit = true;
				break;
			default:
				++years;
				BackEndFinalProject.Filter.addSelectYear(Integer.parseInt(year));
				break;
			}
		} while (!quit);
		years = 0;
	}

// filters playlist according to the genre criteria
	protected static void genreCriteria() {
		System.out.print("\nEnter up to 5 of these genres to include in your playlist:\n"
				+ "POP, ROCK, DANCE, COUNTRY, FOLK, RNB, HIPHOP, INDIE");
		System.out.print("\nTo stop adding genres, type: \"quit\"");

		boolean quit = false;
		int genres = 0;
		do {
			if (genres >= 5) {
				System.out.println("You have selected 5 genres.");
				break;
			} else {
				System.out.print("\n\nEnter a genre:");
			}
			switch (sc.nextLine().trim()) {
			case "POP":
				BackEndFinalProject.Filter.addGenre(Genre.POP);
				++genres;
				break;
			case "ROCK":
				BackEndFinalProject.Filter.addGenre(Genre.ROCK);
				++genres;
				break;
			case "DANCE":
				BackEndFinalProject.Filter.addGenre(Genre.DANCE);
				++genres;
				break;
			case "COUNTRY":
				BackEndFinalProject.Filter.addGenre(Genre.COUNTRY);
				++genres;
				break;
			case "FOLK":
				BackEndFinalProject.Filter.addGenre(Genre.FOLK);
				++genres;
				break;
			case "RNB":
				BackEndFinalProject.Filter.addGenre(Genre.RNB);
				++genres;
				break;
			case "HIPHOP":
				BackEndFinalProject.Filter.addGenre(Genre.HIPHOP);
				++genres;
				break;
			case "INDIE":
				BackEndFinalProject.Filter.addGenre(Genre.INDIE);
				++genres;
				break;
			case "quit":
				quit = true;
				break;
			}
		} while (!quit);
		genres = 0;
	}

// prints a list of commands to the user
	protected static void printCommands() {
		System.out.println("To insert a song, type: \"insert\"");
		System.out.println("To remove a song, type: \"remove\"");
		System.out.println("To create a playlist, type: \"playlist\"");
		System.out.println("To quit the program, type: \"quit\"");
		System.out.println("To print out a list of commands, type: \"help\"");
	}

	protected static void printPlaylistCommands() {
		System.out.println("Enter:\n" + "- \"released between two years\"\n" + "- \"genre\"\n" + "- \"artist\"\n"
				+ "- \"select years\"\n" + "- \"quit\"\n");
	}
}