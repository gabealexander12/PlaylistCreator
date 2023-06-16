import java.util.Scanner;

// --== CS400 File Header Information ==--
// Name: Jacob Revnew
// Email: revnew@wisc.edu
// Team: Team: BB
// TA: Bri Cochran
// Lecturer: Florian Heirmerl
// Notes to Grader: <optional extra notes>


/**
 * This class provides the user interface for our play list app. 
 * 
 * @author Jacob Revnew
 *
 */
public class FrontEnd {
  private static Scanner scnr = new Scanner(System.in);
  private static String playlist;
  
  
  /**
   * The main method calls the driver which runs the run program and when over closes the scanner
   * 
   * @param args - not used inn this program
   */
  public static void main(String[] args) {
    driver();
    scnr.close();
  }
  
  /**
   * This class drives the program
   * 
   */
  public static void driver() {
    System.out.print(
        "Welcome to the \"Playlist Builder\"!\nThere are many features available to you. "
            + "Would you like to have them listed now? (yes or no): ");
    switch (scnr.nextLine()) {
      case "yes":
        printCommands();
        break;
      case "no":
        System.out.println(
            "\nIf at any time you need help or forget commands, type \"help\" and the list of commands "
                + "will be printed.");
        break;
      default:
        System.out.println("\nInvalid command entered. Printing list of valid commands.");
        printCommands();
        break;
    }

    System.out.print("\nNow that you are familiar with the features of this program, would you "
        + "like to start a song library from scratch, or load a provided song library? (scratch or load) : ");

    boolean load = true; // take user command to either start from scratch or load database
    do {
      switch (scnr.nextLine()) {
        case "scratch":
          load = false;
          loadHelper(null);
          System.out.println("\nPlaylist has been created from scratch.");
          break;
        case "load":
          loadHelper("music.csv");
          System.out.println("\nDatabase has been initalized with default songs.");
          load = false;
          break;
        default:
          System.out.println("\nInvalid instruction entered. Enter either \"scratch\" or \"load\"");
          break;
      }
    } while (load);

    executeUserCommands(); // takes user commands until user enters "quit" command
  }
  
  
  /**
   * This class initializes the song library
   * 
   * @param filepath - path to the csv file
   */
  public static void loadHelper(String filepath) {
    BackEndFinalProject.initializeLibrary(filepath);
  }
  
  /**
   * The class is called to insert a song
   * 
   * @return - returns whether the song is inserted or not
   */
  public static boolean insertSong(String song) {
    song.trim();
    System.out.print("\nPlease enter the artist:");
    String artist = scnr.nextLine().trim();
    System.out.print("\nPlease enter the genre:");
    String genre = scnr.nextLine().trim();
    System.out.print("\nPlease enter the album:");
    String album = scnr.nextLine().trim();
    System.out.print("\nPlease enter the length in seconds:");
    double length = scnr.nextDouble();
    System.out.print("\nPlease enter the year released:");
    int year = scnr.nextInt();
    Song songToAdd = new Song(song, artist, genre, length, year, album);
    if (getSong(song, artist)) {
      return false; 
    }
    BackEndFinalProject.songLibrary.put(artist + song, songToAdd);
    return true;
  }
  
  /**
   * The class is called to check if a song is in the song library
   * 
   * @return - returns whether the song is in the library or not
   */
  public static boolean getSong(String song, String artist) {
    if (BackEndFinalProject.songLibrary.get(artist + song) != null) {
      return true;
    }
    return false;
  }
  
  /**
   * The class is called to remove a song from the library
   * 
   * @return - returns whether the song is removed from the library or not
   */
  public static boolean removeSong(String song, String artist) {
    Song songToFind = BackEndFinalProject.songLibrary.get(artist + song);
    return BackEndFinalProject.songLibrary.remove(artist + song, songToFind);
  }
  
  
  /**
   * This method prompts the user to enter a command and ignores capital letters and 
   * and any whitespace
   * 
   * @return a string representing the user's command
   */
  private static String getCommand() {
    System.out.print("\nENTER COMMAND: ");
    return scnr.nextLine().toLowerCase().trim();
  }
  
  /**
   * This class takes the user commands and executes them correctly
   * 
   */
  public static void executeUserCommands() {
    boolean quit = false;
    do {
      switch (getCommand()) {
        case "help":
          printCommands();
          break;
        case "add new song":
          System.out.print("\nPlease enter the name of the song to add: ");
          Boolean addedSong = insertSong(scnr.nextLine());
          if (addedSong) {
          System.out.print("\nThe song was added!" );
          } else {
          System.out.print("\nThe song was not added, it may already exist in the library" );
          }
          break;
        case "get song":
          System.out.print("\nPlease enter the name of the song: ");
          String song = scnr.nextLine().trim();
          System.out.print("\nPlease enter the name of the artist: ");
          String artist = scnr.nextLine().trim();
          Boolean songExists = getSong(song, artist);
          if (songExists) {
            System.out.print("\nThe song is in the library!");
          } else {System.out.print("\nThe song does not exist in the library");
          }
          break;
        case "remove song":
          System.out.print("\nPlease enter the name of the song to remove: ");
          Boolean removedSong = insertSong(scnr.nextLine());
          if (removedSong) {
          System.out.print("\nThe song was removed!" );
          } else {
          System.out.print("\nThe song was not removed, it may already not exist in the library" );
          }
        case "create playlist":
          createPlaylistHelper();
          break;
        case "quit":
          System.out.println("\nThank you for using the Playlist Builder! Goodbye!");
          scnr.nextLine(); // "consume remaining input"
          quit = true;
        default:
          if (!quit) {
            System.out
                .println("\nInvalid command entered. Printing list of valid commands.");
            printCommands();
          }
          break;
      }
    } while (!quit);
  }
  
  
  /**
   * This class helps to find the play list criteria give by the user to build the play list
   * 
   */
  private static void createPlaylistHelper() {
    System.out.print("\nPlease enter one of the following 5 criteria:\n"
        + "1) \"released between two years\" -- creats a playlist of songs inside those two years\n"
        + "2) \"genre\" -- creates a playlist of songs in the genre selected\n"
        + "3) \"artist\" -- creates a playlist of songs by the artist selected\n"
        + "4) \"select years\" -- creates a playlist of songs in certain years\n"
        + "5) \"exit\" -- exits out of creating a playlist\n"
        );
    boolean exit = false;
    do {
    switch (scnr.nextLine().toLowerCase().trim()) {
      case "released between two years":
        System.out.print("\nEnter start year:");
        Integer startYear = scnr.nextInt();
        System.out.print("\nEnter end year:");
        Integer endYear = scnr.nextInt();
        BackEndFinalProject.Filter.setStartYear(startYear);
        BackEndFinalProject.Filter.setEndYear(endYear);
        playlist = BackEndFinalProject.getOrganizedPlaylist();
        System.out.print("\nYour playlist is:");
        System.out.print("\n" + playlist);
        exit = true;
        break;
      case "genre":
        genreHelper();
        playlist = BackEndFinalProject.getOrganizedPlaylist();
        System.out.print("\nYour playlist is:");
        System.out.print("\n" + playlist);
        exit = true;
        break;
      case "artist":
        Boolean done = false;
        int artists = 0;
        do {
          System.out.print("\nEnter an artist:");
          BackEndFinalProject.Filter.addArtist(scnr.nextLine().trim());
          ++artists;
        } while (!done || artists < 5);
        artists = 0;
        playlist = BackEndFinalProject.getOrganizedPlaylist();
        System.out.print("\nYour playlist is:");
        System.out.print("\n" + playlist);
        exit = true;
        break;
      case "select years":
        Boolean full = false;
        int years = 0;
        do {
          System.out.print("\nEnter a year:");
          BackEndFinalProject.Filter.addSelectYear(scnr.nextInt());
          ++years;
        } while (!full || years < 5);
        years = 0;
        playlist = BackEndFinalProject.getOrganizedPlaylist();
        System.out.print("\nYour playlist is:");
        System.out.print("\n" + playlist);
        exit = true;
        break;
      case "exit":
        exit = true; 
        break;
      default:
        if (!exit) {
          System.out
              .println("\nInvalid command entered. Printing list of valid commands.");
          System.out.print("\nPlease enter one of the following 5 criteria:\n"
              + "1) \"released between two years\" -- creats a playlist of songs inside those two years\n"
              + "2) \"genre\" -- creates a playlist of songs in the genre selected\n"
              + "3) \"artist\" -- creates a playlist of songs by the artist selected\n"
              + "4) \"select years\" -- creates a playlist of songs in certain years\n"
              + "5) \"exit\" -- exits out of creating a playlist\n"
              );
        }
        break;
    }
    } while (!exit);
  }
  
  
  /**
   * This class helps to find the correct genres to add to the filter from user input to create the 
   * play list
   * 
   */
  protected static void genreHelper() {
    System.out.print("\nEnter up to 5 of these genres to sort by:\n"
        + "POP, ROCK, DANCE, COUNTRY, FOLK, RNB, HIPHOP, INDIE");
    System.out.print("\nType \"q\" at anytime to quit adding genres\n");
    boolean done = false;
    int genres = 0;
    do {
    System.out.print("Enter a genre:");
    switch (scnr.nextLine().toLowerCase().trim()) {
      case "pop":
        BackEndFinalProject.Filter.addGenre(Genre.POP);
        ++genres;
        break;
      case "rock":
        BackEndFinalProject.Filter.addGenre(Genre.ROCK);
        ++genres;
        break;
      case "dance":
        BackEndFinalProject.Filter.addGenre(Genre.DANCE);
        ++genres;
        break;
      case "country":
        BackEndFinalProject.Filter.addGenre(Genre.COUNTRY);
        ++genres;
        break;
      case "folk":
        BackEndFinalProject.Filter.addGenre(Genre.FOLK);
        ++genres;
        break;
      case "rnb":
        BackEndFinalProject.Filter.addGenre(Genre.RNB);
        ++genres;
        break;
      case "hiphop":
        BackEndFinalProject.Filter.addGenre(Genre.HIPHOP);
        ++genres;
        break;
      case "indie":
        BackEndFinalProject.Filter.addGenre(Genre.INDIE);
        ++genres;
        break;
      case "q":
        done = true;
        break;
    }
    } while (!done || genres >= 5);
    genres = 0;
  }
  
  /**
   * This class prints the commands available to the user
   * 
   */
  public static void printCommands() {
    System.out.println("\nPlease enter one of the following 6 commands:\n"
        + "1) \"add new song\" -- inserts a new song into the song library\n"
        + "2) \"get song\" -- checks if a song is in the song library\n"
        + "3) \"remove song\" -- removes song from the library\n"
        + "4) \"create playlist\" -- creates a playlist based off the parameters given by the user\n"
        + "5) \"quit\" -- exits the program\n"
        + "6) \"help\" -- prints list of valid commands");
  
  }
  

}

