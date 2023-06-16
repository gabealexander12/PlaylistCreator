// --== CS400 File Header Information ==--
// Name: Rohan Nadgir
// Email: nadgir@wisc.edu
// Team: BB
// Role: Data Wrangler 2
// TA: Bri Cochran
// Lecturer: Gary Dahl
// Notes to Grader:

public class Song {
    private String song;
     private String artist;
     private String genre;
     private int year;
     private double length;
     private String album;
     private Genre rigidGenre;
     
     public Song(String song, String artist, String genre, double length, 
         int year, String album) {
         this.song = song;
         this.artist = artist;
         this.genre = genre;
         this.length = length;
         this.year = year;
         this.album = album;
         this.rigidGenre = null;
     }
    
    /*
     * Sets the song name
     */
    public void setSong(String song) {
        this.song = song;
    }
    
    /*
     * Retrieves the song name
     */
    public String getSong() {
        return this.song;
    }
    
    /*
     * Sets the artist name
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }
    
    /*
     * Retrieves the artist name
     */
    public String getArtist() {
        return this.artist;
    }
    
    /*
     * Sets the genre
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    /*
     * Retrieves the genre
     */
    public String getGenre() {
        return this.genre;
    }
    
    /*
     * Sets the song length
     */
    public void setLength(double length) {
        this.length = length;
    }
    
    /*
     * Retrieves the song length
     */
    public double getLength() {
        return this.length;
    }
    
    /*
     * Sets the year
     */
    public void setYear(int year) {
        this.year = year;
    }
    
    /*
     * Retrieves the year
     */
    public int getYear() {
        return this.year;
    }
    
    /*
     * Sets the album name
     */
    public void setAlbum(String album) {
        this.album = album;
    }
    
    /*
     * Retrieves the album name
     */
    public String getAlbum() {
        return this.album;
    }

    /*
     * Retrieves the RigidGenre
     */
    public Genre getRigidGenre() {
      return rigidGenre;
    }

    /*
     * Sets the RigidGenre
     */
    public void setRigidGenre(Genre rigidGenre) {
      this.rigidGenre = rigidGenre;
    }
   
    /*
     * Returns the song's name.
     */
    public String toString() {
      return song;
    }
}