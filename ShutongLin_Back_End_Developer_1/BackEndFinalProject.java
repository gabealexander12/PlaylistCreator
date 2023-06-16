
// --== CS400 File Header Information ==--
// Name: Shutong Lin
// Email: slin326@wisc.edu
// Team: BB
// Role: Back End Developer 1
// TA: Brianna (Bri) Cochran
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Providing calls to the front end
 * 1.filter method : get the songs that fit for the  someRegex
 * 2.getOrganizedList method: return the MST in the order
 * 3.mapPlaylist method : create the Graph
 */
public class BackEndFinalProject {

	static class Filter {

		private Genre pop;
		private Integer startYear;
		private Integer endYear;

		private List<String> artists = new ArrayList<String>();
		private int selectYear;

		public void addGenre(Genre pop) {
			this.pop = pop;
		}

		public void setStartYear(Integer startYear) {
			this.startYear = startYear;
		}

		public void setEndYear(Integer endYear) {
			this.endYear = endYear;
		}

		public void addArtist(String artist) {
			artists.add(artist);
		}

		public void addSelectYear(int selectYear) {
			this.selectYear = selectYear;
		}

		public Genre getPop() {
			return pop;
		}

		public void setPop(Genre pop) {
			this.pop = pop;
		}

		public List<String> getArtists() {
			return artists;
		}

		public void setArtists(List<String> artists) {
			this.artists = artists;
		}

		public int getSelectYear() {
			return selectYear;
		}

		public void setSelectYear(int selectYear) {
			this.selectYear = selectYear;
		}

		public Integer getStartYear() {
			return startYear;
		}

		public Integer getEndYear() {
			return endYear;
		}

	}

	static Filter Filter = new Filter();
	public static Hashtable<String, Song> songLibrary;
        /**
	 * filter the song where math the someRegex
	 * @param someRegex
	 * @param currentFilteredList
	 * @return
	 */
	protected static Song[] filter(String someRegex, Song[] currentFilteredList) {
		return (Song[]) Arrays
				.asList(currentFilteredList).stream().filter(i -> i.getArtist().matches(someRegex)
						|| i.getSong().matches(someRegex) || i.getGenre().matches(someRegex))
				.collect(Collectors.toList()).toArray();
	}

	// returns the MST in the order it was iterated through.
	protected static Song[] getOrganizedList(CS400Graph<Song> mappedPlaylist) {
		return Kruskal.kruskal(mappedPlaylist);
	}

	protected static CS400Graph<Song> mapPlaylist(Song[] playlist) {
		CS400Graph<Song> g = new CS400Graph<Song>();

		for (Song s : playlist) {
			g.insertVertex(s);
		}

		for (int i = 0; i < playlist.length - 1; i++) {
			for (int j = i + 1; j < playlist.length; j++) {
				g.insertEdge(playlist[i], playlist[j], getSongSimilarity(playlist[i].getSong(), playlist[j].getSong()));
				g.insertEdge(playlist[j], playlist[i], getSongSimilarity(playlist[i].getSong(), playlist[j].getSong()));
			}
		}

		return g;
	}

	/**
	 * get songs similarity for the weight
	 * 
	 * @param song1
	 * @param song2
	 * @return
	 */
	public static int getSongSimilarity(String song1, String song2) {
		int sourceLen = song1.length();
		int targetLen = song2.length();

		if (sourceLen == 0) {
			return targetLen;
		}
		if (targetLen == 0) {
			return sourceLen;
		}

		int[][] arr = new int[sourceLen + 1][targetLen + 1];

		for (int i = 0; i < sourceLen + 1; i++) {
			arr[i][0] = i;
		}
		for (int j = 0; j < targetLen + 1; j++) {
			arr[0][j] = j;
		}

		Character sourceChar = null;
		Character targetChar = null;

		for (int i = 1; i < sourceLen + 1; i++) {
			sourceChar = song1.charAt(i - 1);

			for (int j = 1; j < targetLen + 1; j++) {
				targetChar = song2.charAt(j - 1);

				if (sourceChar.equals(targetChar)) {

					arr[i][j] = arr[i - 1][j - 1];
				} else {
					arr[i][j] = (Math.min(Math.min(arr[i - 1][j], arr[i][j - 1]), arr[i - 1][j - 1])) + 1;
				}
			}
		}

		return arr[sourceLen][targetLen];
	}

	public static void main(String[] args) {
		Song[] s = new Song[10];
		for (int i = 0; i < s.length; i++) {
			s[i] = new Song();
			s[i].setSong(('A' + i) + "");
		}
		CS400Graph<Song> g = mapPlaylist(s);
		getOrganizedList(g);
	}

	public static boolean initializeLibrary(String string) {
		return false;
	}

	public static Song findSong(String song, String artist) {
		// TODO Auto-generated method stub
		if (songLibrary == null)
			return null;
		for (Song s : songLibrary.values()) {
			if (s.getSong().equalsIgnoreCase(song) && s.getArtist().equalsIgnoreCase(artist))
				return s;
		}
		return null;

	}

	public static boolean addSong(Song song) {
		if (songLibrary == null)
			return false;
		songLibrary.put(song.getArtist() + song.getSong(), song);
		return true;
	}

	public static boolean removeSong(String string) {
		songLibrary.remove(string);
		return true;
	}

	public static boolean createPlaylist() {
		return true;
	}

	public static String getOrganizedPlaylist() {
		if (songLibrary == null)
			return null;
		return String.join(",", songLibrary.values().stream().filter(song -> {
			return Filter.getArtists().contains(song.getArtist()) || Filter.getPop() == song.getRigidGenre()
					|| Filter.getSelectYear() == song.getYear();
		}).map(song -> song.getSong()).collect(Collectors.toList()));
	}
}
