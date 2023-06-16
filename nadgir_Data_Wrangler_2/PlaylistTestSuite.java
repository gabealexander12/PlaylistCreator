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

class PlaylistTestSuite {

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
		if(!BackEndFinalProject.songLibrary.containsKey("Photograph")) {
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
		BackEndFinalProject.songLibrary.put("CS 400 Song", new Song("CS 400 Song", "Team BB", "punk", 3.39, 2017, "Fall Sem"));
		if(!BackEndFinalProject.songLibrary.containsKey("CS 400 Song")) {
			fail("Song not added correctly");
		}
	}

	@Test
	void testRemoveSong() {
		// // checks that a song is correctly removed from the hashtable
		BackEndFinalProject.initializeLibrary("music.csv");
		if(BackEndFinalProject.songLibrary.remove("Photograph") == null) {
			fail("Song not removed correctly");
		}	
	}
	
	@Test
	void testCreatePlaylist() {
		// calls the methods to create a playlist given the current songs in the database
		
		BackEndFinalProject.initializeLibrary("music.csv");
		BackEndFinalProject.Filter.addGenre(Genre.ROCK);
		String playlist = BackEndFinalProject.getOrganizedPlaylist();
		if(!playlist.equals("String")) {
			fail("Playlist not correctly created");
		}		
	}

}
