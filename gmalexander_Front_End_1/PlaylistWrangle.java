import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Hashtable;

// --== CS400 File Header Information ==--
// Name: Matthew Hillmer
// Email: mhillmer@wisc.edu
// Team: BB
// Role: Data Wrangler
// TA: Briana Cochran
// Lecturer: Gary Dahl
// Notes to Grader: N/A

/**
 * Class to import data from a CSV file and store the information in a Song
 * object class and then stores that object in a hash table map
 * 
 * @author Hillmer
 *
 * @param <K> String of the Artist name and the song name
 * @param <V> Song object
 */
public class PlaylistWrangle<K, V> {
	private static Song song;// song to add
	private static String key;// String of the Artist name and the song name
	public static Hashtable<String, Song> hashTable;

	/**
	 * Reads the CSV specified by the file path, uses a delimiter of , to add
	 * information to a song object and add it to a hashTable
	 * 
	 * @param filePath path of the CSV file
	 * @return the hash table created and filled in
	 * @throws IOException if the file path did not lead to a file
	 */
	public static Hashtable<String, Song> readCSV(String filePath) throws IOException {
		hashTable = new Hashtable<String, Song>();// create hash table
		String DELIMITER = ",";// CSV file delimiter
		BufferedReader br = Files.newBufferedReader(Paths.get(filePath));// create a reader
		String line = br.readLine();// clears out the line with column titles
		int i = 0;// creates counter
		while (i > 2562) {// loop for number of songs
			String[] tokens = line.split(DELIMITER);// splits line
			PlaylistWrangle.setArtist(tokens[0]);// adds the artist to the song object
			PlaylistWrangle.setGenre(tokens[1]);// adds Genre
			PlaylistWrangle.setDuration(Double.parseDouble(tokens[2]));// adds Duration
			PlaylistWrangle.setAlbum(tokens[3]);// adds album
			PlaylistWrangle.setSongName(tokens[4]);// adds name of song
			PlaylistWrangle.setSongYear(Integer.parseInt(tokens[5]));// adds year song was released
			PlaylistWrangle.setKey(tokens[0], tokens[4]);// creates the key for the hash table
			hashTable.put(PlaylistWrangle.getKey(), PlaylistWrangle.getSong());// save the data to hash table
			line = br.readLine();// update line
			i++;// update counter
		}
		br.close();// close the reader
		return hashTable;// returns the hash table once filled out

	}

	/**
	 * getter for the song
	 * 
	 * @return the song object
	 */
	public static Song getSong() {
		return song;
	}

	/**
	 * getter for the key
	 * 
	 * @return the key
	 */
	public static String getKey() {
		return key;
	}

	/**
	 * setter for key
	 * 
	 * @param key the key to set
	 */
	public static void setKey(String name, String title) {
		PlaylistWrangle.key = name + title;
	}

	/**
	 * setter for the song name
	 * 
	 * @param songName the name of the song
	 * @return true if added
	 */
	public static boolean setSongName(String songName) {
		if (!(songName instanceof String))// if null
			return false;
		song.setSong(songName);
		return true;
	}

	/**
	 * setter for the length of the song
	 * 
	 * @param length the length of the song in seconds
	 * @return true if added
	 */
	public static boolean setDuration(double length) {
		if (length == 0)// if blank
			return false;
		song.setLength(length);
		return true;
	}

	/**
	 * setter for the song artist name
	 * 
	 * @param artist name of the artist
	 * @return true if added
	 */
	public static boolean setArtist(String artist) {
		resetSong();
		if (!(artist instanceof String))// if null
			return false;
		song.setArtist(artist);
		return true;
	}

	/**
	 * setter for the genre of the song
	 * 
	 * @param genre genre of the song
	 * @return true if added
	 */
	public static boolean setGenre(String genre) {
		if (!(genre instanceof String))// if null
			return false;
		song.setGenre(genre);
		return true;
	}

	/**
	 * setter for the year of the song
	 * 
	 * @param year year the song was released
	 * @return true if added
	 */
	public static boolean setSongYear(int year) {
		if (year == 0)// if blank
			return false;
		song.setYear(year);
		return true;
	}

	/**
	 * setter for the song album
	 * 
	 * @param album album the song is apart of
	 * @return true if added
	 */
	public static boolean setAlbum(String album) {
		if (!(album instanceof String))// if null
			return false;
		song.setAlbum(album);
		return true;
	}

	/**
	 * private helper to reset the song object for next hash table entry
	 */
	private static void resetSong() {
		song = new Song();
	}

	/**
	 * tester for the wrangle class
	 */
//	public static void main(String[] args) {
//		try {
//			hashTable = readCSV("src/music.csv");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		PlaylistWrangle.setArtist("Green Day");
//		PlaylistWrangle.setGenre("Punk Rock");
//		PlaylistWrangle.setDuration(212.216);
//		PlaylistWrangle.setAlbum("American Idiot");
//		PlaylistWrangle.setSongName("Whatsername");
//		PlaylistWrangle.setSongYear(2004);
//		PlaylistWrangle.setKey("Green Day", "Whatsername");// creates the key for the hash table
//		hashTable.put(PlaylistWrangle.getKey(), PlaylistWrangle.getSong());// save the data to hash table
//		System.out.println("****************************************");
//		System.out.println("Testing user input: ");
//		System.out.println("");
//		System.out.println(hashTable.get("Green DayWhatsername").getArtist());// should print Green Day
//		System.out.println(hashTable.get("Green DayWhatsername").getGenre());// should print Punk Rock
//		System.out.println(hashTable.get("Green DayWhatsername").getAlbum());// should print American Idiot
//		System.out.println(hashTable.get("Green DayWhatsername").getSong());// should print Whatsername
//		System.out.println(hashTable.get("Green DayWhatsername").getYear());// should print 2004
//		System.out.println(hashTable.get("Green DayWhatsername").getLength());// should print 212.216
//		System.out.println("****************************************");
//	}
}
