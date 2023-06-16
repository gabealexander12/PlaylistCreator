//--== CS400 File Header Information ==--
//Name: Ryan Cope
//Email: rlcope@wisc.edu
//Team: BB
//Role: Test Engineer 2
//TA: Bri
//Lecturer: Gary
//Notes to Grader: <optional extra notes>
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class TestSuitePlaylist {

	@Test
	void testInitialization() {
		// uses a similar method as written in the front end to create a database
		// initialized with the correct songs
		if (!BackEndFinalProject.initializeLibrary("music.csv")) {
			fail("Failed initialization, load playlist");
		}
		if(BackEndFinalProject.songLibrary == null) {
			fail("Failed initialization, empty library");
		}
		if(!BackEndFinalProject.songLibrary.containsKey("AerosmithCrazy")) {
			fail("Failed initialization, does not contain randomly selected song");
		}
		if(!BackEndFinalProject.initializeLibrary(null)) {
			fail("Failed initialization, create empty playlist");
		}

	}
	
	@Test
	void testInsertSong() {
		// checks that a song is correctly added to the hashtable
		BackEndFinalProject.initializeLibrary("music.csv");
		BackEndFinalProject.songLibrary.put("Glass TidesForever", new Song("Forever", "Glass Tides", "punk", 3.39, 2017, "Thoughts"));
		if(!BackEndFinalProject.songLibrary.containsKey("Glass TidesForever")) {
			fail("Song not added correctly");
		}
	}

	@Test
	void testRemoveSong() {
		// // checks that a song is correctly removed from the hashtable
		BackEndFinalProject.initializeLibrary("music.csv");
		if(BackEndFinalProject.songLibrary.remove("AerosmithCrazy") == null) {
			fail("Song not removed correctly");
		}	
	}
	
	@Test
	void testCreatePlaylist() {
		// calls the methods to create a playlist given the current songs in the database
		
		BackEndFinalProject.initializeLibrary("music.csv");
		BackEndFinalProject.Filter.addGenre(Genre.ROCK);
		String playlist = BackEndFinalProject.getOrganizedPlaylist();
		if(!playlist.contains("I Don't Wanna be a War Hero")) {
			fail("Playlist not correctly created");
		}		
	}

}
