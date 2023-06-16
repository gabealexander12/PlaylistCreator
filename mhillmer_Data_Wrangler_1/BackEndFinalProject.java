// --== CS400 File Header Information ==--
// Name: Frank Slavinsky
// Email: fslavinsky@wisc.edu
// Team: Team: BB
// TA: Bri Cochran
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.stream.Stream;
import java.util.regex.*;

/**
 * This class represents the back end for Team BB CS400 F2020's final project.
 * 
 * Specifically, this class contains a hash-table that holds Song objects-- a music library-- and an
 * instance of CS400Graph (from Project 3) to function as a template for an "organized play-list".
 * 
 * This class contains methods to: populate the library from a CSV or from scratch, set a "filter"
 * to specify the type of music the user wants in their play-list, and insert the filtered play-list
 * into a graph with edges connecting each song based on song similarity (determined by a simple
 * pseudo-algorithm). That graph is then spanned using Kruskal's MST algorithm (the code for which
 * is contained in Kruskal.java) thus resulting in a "sorted" play-list with songs in an order based
 * on similarity to one another.
 * 
 * @author Frank Slavinsky
 *
 */
public class BackEndFinalProject {
  // Back End fields
  protected static Hashtable<String, Song> songLibrary; // visible so front end can use HT methods
  private static CS400Graph<Song> songRelations = new CS400Graph<Song>();
  private static LinkedList<Song> filteredSongs = new LinkedList<Song>();

  /**
   * This sub-class represents the "filter" the user wishes to utilize to sort their music library.
   * The class contains fields such as a start and end years-- this allows the user to say that they
   * want music from a range of years. There is also an array of int: this allows the user to say
   * they want music from just specific years. Also, there are genre and artist arrays to be used in
   * the same way as the year selections.
   *
   */
  protected static class Filter {
    // Filter fields
    private static Integer startYear = null;
    private static Integer endYear = null;
    private static ArrayList<Integer> selectYears = new ArrayList<Integer>(5);
    private static ArrayList<Genre> genres = new ArrayList<Genre>(5);
    private static ArrayList<String> artists = new ArrayList<String>(5);

    // setters-- note there are not getters... the front end only needs to set the filter
    public static void setStartYear(Integer year) {
      startYear = year;
    }

    public static void setEndYear(Integer year) {
      endYear = year;
    }

    public static void addSelectYear(Integer year) {
      selectYears.add(year);
    }

    public static void addGenre(Genre genre) {
      genres.add(genre);
    }

    public static void addArtist(String artist) {
      artists.add(artist);
    }

    /**
     * This method "clears" the filter class so as to be ready to be used again or called by the
     * front end if the user wishes to restart.
     */
    protected static void clearFilter() {
      startYear = null;
      endYear = null;
      selectYears = new ArrayList<Integer>(5);
      genres = new ArrayList<Genre>(5);
      artists = new ArrayList<String>(5);
    }
  }

  /**
   * This method is called by the front end to initialize the hash-table representing the song
   * library. The front end can either call this method with a string representing a file path to a
   * CSV, or the front end can pass 'null' to signify the user wishes to create a library from
   * scratch. If an IO exception occurs then false is returned, else the library was initialized and
   * true is returned.
   * 
   * @param filePath - a string representing what should be a valid file path
   * @return true if library successfully initialized, false if not (i.e. invalid file path passed)
   */
  protected static boolean initializeLibrary(String filePath) {
    if (filePath == null) // user wants to start from scratch
      songLibrary = new Hashtable<String, Song>();
    else {
      try {
        songLibrary = PlaylistWrangle.readCSV(filePath);
      } catch (Exception e) {
        return false;
      }
    }
    return true; // no issues encountered, initialization success
  }

  /**
   * This method returns the concatenation of the song artist and title to be used as the key for
   * the hash-table... helpful for when making calls to songLibrary methods
   * 
   * @param song - the song to get the key for
   * @return the key for the song passed
   */
  protected static String getKey(Song song) {
    return song.getArtist() + song.getSong();
  }

  /**
   * This method returns a string representing an organized play-list comprised of songs the user
   * selected via the filters chosen.
   * 
   * @return a string representing the organized play-list
   */
  protected static String getOrganizedPlaylist() {
    filterSongs(); // this populates the field filteredSongs to be used by graphFilteredSongs
    graphFilteredSongs(); // populates the field songRelations
    Filter.clearFilter(); // prepare filter for next use
    return Kruskal.kruskalMST(songRelations).toString();
  }

  /**
   * This method takes all of the contents of the song library and casts said collection to a
   * stream. Then the stream is filtered based on parameters the user was previously prompted for.
   * The user can select multiple parameters. Then the stream is cast to the static field
   * filterSongs which will be used by graphFilteredSongs().
   * 
   */
  private static void filterSongs() {
    songRelations = new CS400Graph<Song>(); // reset the static fields
    filteredSongs = new LinkedList<Song>();
    Stream<Song> songStream = songLibrary.values().stream(); // convert song library to stream
    songStream
        .filter(song -> Filter.selectYears.isEmpty() || Filter.selectYears.contains(song.getYear()))
        .filter(song -> Filter.artists.isEmpty() || Filter.artists.contains(song.getArtist()))
        .filter(song -> Filter.genres.isEmpty() || Filter.genres.contains(getSongRigidGenre(song)))
        .filter(song -> Filter.startYear == null || song.getYear() >= Filter.startYear)
        .filter(song -> Filter.endYear == null || song.getYear() <= Filter.endYear)
        .forEach(song -> filteredSongs.add(song));
  }

  /**
   * This method adds every one of the filtered songs to the CS400Graph and then compares each song
   * to every other song but itself. In comparing two given songs a "edge weight" is determined. The
   * "edge" is then inserted into the graph between the two songs, for every possible song pairing.
   */
  private static void graphFilteredSongs() {
    for (Song song : filteredSongs) {
      songRelations.insertVertex(song);
    }
    for (int i = 0; i < filteredSongs.size(); i++) {
      Song firstSong = filteredSongs.get(i);
      for (int j = i + 1; j < filteredSongs.size(); j++) {
        // compare list.get(i) and list.get(j)
        Song secondSong = filteredSongs.get(j);
        int weight = compareTwoSongs(firstSong, secondSong);
        songRelations.insertEdge(firstSong, secondSong, weight);
      }
    }
  }

  /**
   * This helper method compares two songs based on year released and genre and returns a cumulative
   * difference value between the two songs that will be the weight in the CS400Graph object.
   * 
   * @param first  - an arbitrary name for one of the songs to be compared
   * @param second - an arbitrary name for the other song to be compared
   * @return - an int representing the cumulative "difference value" between the two songs
   */
  private static int compareTwoSongs(Song first, Song second) {
    int differenceInYears = Math.abs(first.getYear() - second.getYear());
    int differenceInGenre = compareTwoGenres(first, second);
    return differenceInYears + differenceInGenre;
  }

  /**
   * This helper method is called from compareTwoSongs to implement the comparison of the genres of
   * two songs. In order to compare the two genres, each genre is associated with a row and column
   * in a 2D array-- accomplished by associating each genre with an index-- and the "difference
   * value" between the two genres stored in each cell. "Difference values" range from 1 to 5.
   * 
   * Because genre A compared to B is the same as B to A, only half of the cells of the table need
   * be filled. Should the comparison result in an "empty cell"-- i.e. the comparison is valid but
   * fell in the empty half of the table-- the search is inverted guaranteeing a value found.
   * 
   * If one of the songs' rigid genres is uncategorized i.e. is Genre.NA, then the middle difference
   * value of 3 is assigned to the songs. If the songs are of the same genre, then a value of 0 is
   * assigned (there is no difference between the songs genre wise).
   * 
   * @param first  - an arbitrary name for one of the songs to be compared
   * @param second - an arbitrary name for the other song to be compared
   * @return an int representing the difference value of the two songs' genres
   */
  private static int compareTwoGenres(Song first, Song second) {
    if (getSongRigidGenre(first).equals(getSongRigidGenre(second))) {
      return 0; // genres are the same... no difference
    }

    if (getSongRigidGenre(first).equals(Genre.NA) || getSongRigidGenre(second).equals(Genre.NA))
      return 3; // one of the songs does not contain an identifiable genre... cannot tell
                // difference, but there does exist a difference and 3 is the average difference.

    int[][] genreRelations = new int[8][8];
    genreRelations[0][1] = 3; // pop to other genres
    genreRelations[0][2] = 1;
    genreRelations[0][3] = 5;
    genreRelations[0][4] = 5;
    genreRelations[0][5] = 3;
    genreRelations[0][6] = 2;
    genreRelations[0][7] = 4;

    genreRelations[1][2] = 5; // rock to other genres (except pop as it has already be compared)
    genreRelations[1][3] = 3;
    genreRelations[1][4] = 4;
    genreRelations[1][5] = 5;
    genreRelations[1][6] = 5;
    genreRelations[1][7] = 2;

    genreRelations[2][3] = 5; // dance to other genres not yet compared to
    genreRelations[2][4] = 5;
    genreRelations[2][5] = 2;
    genreRelations[2][6] = 1;
    genreRelations[2][7] = 4;

    genreRelations[3][4] = 2; // country to remaining genres
    genreRelations[3][5] = 5;
    genreRelations[3][6] = 5;
    genreRelations[3][7] = 3;

    genreRelations[4][5] = 4; // folk to remaining genres
    genreRelations[4][6] = 5;
    genreRelations[4][7] = 2;

    genreRelations[5][6] = 1; // RnB to remaining genres
    genreRelations[5][7] = 3;

    genreRelations[6][7] = 4; // HipHop to Indie... now indie effectively compared to all genres

    int firstIndex = getGenreIndex(first.getRigidGenre());
    int secondIndex = getGenreIndex(second.getRigidGenre());

    if (genreRelations[firstIndex][secondIndex] == 0) // accessed invalid part of table, invert
      return genreRelations[secondIndex][firstIndex];
    else {
      return genreRelations[firstIndex][secondIndex];
    }
  }

  /**
   * Simple helper method to assign rigid genre of song with corresponding index int
   * 
   * @param genre - the genre to find the corresponding index for
   * @return an int representing the index for the passed genre
   */
  private static int getGenreIndex(Genre genre) {
    if (genre.equals(Genre.POP))
      return 0;
    if (genre.equals(Genre.ROCK))
      return 1;
    if (genre.equals(Genre.DANCE))
      return 2;
    if (genre.equals(Genre.COUNTRY))
      return 3;
    if (genre.equals(Genre.FOLK))
      return 4;
    if (genre.equals(Genre.RNB))
      return 5;
    if (genre.equals(Genre.HIPHOP))
      return 6;
    else { // must be INDIE
      return 7;
    }
  }

  /**
   * This method is for exclusive use within the BackEnd class to get the rigid genre of a song, and
   * if such has not yet been assigned, to assign such via a call to setRigidGenre()
   * 
   * @param song - the song object to get the rigid genre from, or assign to and then retrieve
   * @return the rigidGenre of the song
   */
  private static Genre getSongRigidGenre(Song song) {
    if (song.getRigidGenre() == null) // song does not yet have rigid genre assigned, do such now
      setRigidGenre(song);
    return song.getRigidGenre();
  }

  /**
   * This method utilizes regexes to categorize the multitude of genres listed in a user supplied
   * CSV to a handful of manageable genres that can be compared against one another.
   * 
   * @param song - the song object to assign a "rigid genre" to based upon its more specific genre.
   *             If no match can be made using the regexes the rigid genre is set to "NA" and will
   *             not be accounted for when comparing to other songs.
   */
  private static void setRigidGenre(Song song) {
    String fluidGenre = song.getGenre();

    String popPattern = ".*[Pp]op.*|.*[Cc]ontemporary.*";
    String indiePattern = ".*[Ii]ndie.*";
    String rockPattern = ".*[Rr]ock.*|.*[Pp]unk.*|.*[Mm]etal.*";
    String dancePattern = ".*[Dd]ance.*|.*[Ee]lectronic.*|.*[Hh]ouse.*";
    String hiphopPattern = ".*[Hh]ip.*|.*[Hh]op.*";
    String rnbPattern = ".*[Rr][Nn][Bb].*|.*[Rr]hythm.*";
    String countryPattern = ".*[Cc]ountry.*";
    String folkPattern = ".*[Ff]olk.*";

    if (fluidGenre == null) // account for possible bad data where genre field is null
      song.setRigidGenre(Genre.NA);
    else if (Pattern.matches(popPattern, fluidGenre))
      song.setRigidGenre(Genre.POP);
    else if (Pattern.matches(indiePattern, fluidGenre))
      song.setRigidGenre(Genre.INDIE);
    else if (Pattern.matches(dancePattern, fluidGenre))
      song.setRigidGenre(Genre.DANCE);
    else if (Pattern.matches(rockPattern, fluidGenre))
      song.setRigidGenre(Genre.ROCK);
    else if (Pattern.matches(countryPattern, fluidGenre))
      song.setRigidGenre(Genre.COUNTRY);
    else if (Pattern.matches(rnbPattern, fluidGenre))
      song.setRigidGenre(Genre.RNB);
    else if (Pattern.matches(hiphopPattern, fluidGenre))
      song.setRigidGenre(Genre.HIPHOP);
    else if (Pattern.matches(folkPattern, fluidGenre))
      song.setRigidGenre(Genre.FOLK);
    else {
      song.setRigidGenre(Genre.NA);
    }
  }

  // TEMPORARY!!! HERE TO SHOWCASE FUNCTIONALITY, COMMENT OUT WHEN UTILIZING THIS CLASS
  public static void main(String[] args) {
    BackEndFinalProject.initializeLibrary(null);
    Song backInBlack = new Song("Back in Black", "ACDC", "mEtAl rock", 137, 1980, "Back in Black");
    Song highwayToHell =
        new Song("Highway to Hell", "ACDC", "HARD rock", 160, 1979, "Highway to Hell");
    Song dontGo = new Song("Don't Go", "Yaz", "Synthpop", 180, 1982, "Upstairs at Eric's");
    Song justCantGetEnough =
        new Song("Just Can't Get Enough", "Depeche Mode", "dance", 170, 1981, "Violator");

    BackEndFinalProject.songLibrary.put(getKey(backInBlack), backInBlack);
    BackEndFinalProject.songLibrary.put(getKey(highwayToHell), highwayToHell);
    BackEndFinalProject.songLibrary.put(getKey(dontGo), dontGo);
    BackEndFinalProject.songLibrary.put(getKey(justCantGetEnough), justCantGetEnough);

    System.out.println("testing kruskal implementation");
    System.out.println(BackEndFinalProject.getOrganizedPlaylist());

    Filter.setStartYear(1981);
    System.out.println(BackEndFinalProject.getOrganizedPlaylist());
  }
}
