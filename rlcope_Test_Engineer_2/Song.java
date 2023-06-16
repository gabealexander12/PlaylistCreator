// --== CS400 File Header Information ==--
// Name: Matthew Hillmer
// Email: mhillmer@wisc.edu
// Team: BB
// Role: Data Wrangler
// TA: Briana Cochran
// Lecturer: Gary Dahl
// Notes to Grader: N/A
/**
 * Song object class to hold all the information for a song
 * 
 * @author Hillmer
 *
 */
public class Song {

	private String song;// name of the song
	private String artist;// name of the artist
	private String genre;// main genre of the artist
	private double length;// length of the song in seconds
	private int year;// year song was released
	private String album;// year song was released
	private Genre rigidGenre; // Genre enum

	/**
	 * Constructor with fields
	 * 
	 * @param song   name of the song
	 * @param artist name of the artist
	 * @param genre  main genre of the artist
	 * @param length length of the song in seconds
	 * @param year   year song was released
	 */
	public Song(String song, String artist, String genre, double length, int year, String album) {
		super();
		this.song = song;
		this.artist = artist;
		this.genre = genre;
		this.length = length;
		this.year = year;
		this.album = album;
	}

	/**
	 * Constructor for no provided information
	 */
	public Song() {
		super();
		this.song = null;
		this.artist = null;
		this.genre = null;
		this.length = 0;
		this.year = 0;
		this.album = null;
	}

	/**
	 * getter for song
	 * 
	 * @return the song
	 */
	public String getSong() {
		return song;
	}

	/**
	 * setter for song
	 * 
	 * @param song the song to set
	 */
	public void setSong(String song) {
		this.song = song;
	}

	/**
	 * getter for artist
	 * 
	 * @return the artist
	 */
	public String getArtist() {
		return artist;
	}

	/**
	 * setter for artist
	 * 
	 * @param artist the artist to set
	 */
	public void setArtist(String artist) {
		this.artist = artist;
	}

	/**
	 * getter for genre
	 * 
	 * @return the genre
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * setter for genre
	 * 
	 * @param genre the genre to set
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}

	/**
	 * getter for length
	 * 
	 * @return the length
	 */
	public double getLength() {
		return length;
	}

	/**
	 * setter for length
	 * 
	 * @param length the length to set
	 */
	public void setLength(double length) {
		this.length = length;
	}

	/**
	 * getter for year
	 * 
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * setter for year
	 * 
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * getter for album
	 * 
	 * @return the album
	 */
	public String getAlbum() {
		return album;
	}

	/**
	 * setter for album
	 * 
	 * @param album the album to set
	 */
	public void setAlbum(String album) {
		this.album = album;
	}

	/*
	 * Returns the song's name.
	 */
	public String toString() {
		return song;
	}

	/**
	 * getter for genre enum
	 * 
	 * @return
	 */
	public Genre getRigidGenre() {
		return rigidGenre;
	}

	/**
	 * setter for genre enum
	 * 
	 * @param rigidGenre
	 */
	public void setRigidGenre(Genre rigidGenre) {
		this.rigidGenre = rigidGenre;
	}

}