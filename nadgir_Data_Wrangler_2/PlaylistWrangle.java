// --== CS400 File Header Information ==--
// Name: Rohan Nadgir
// Email: nadgir@wisc.edu
// Team: BB
// Role: Data Wrangler 2
// TA: Bri Cochran
// Lecturer: Gary Dahl
// Notes to Grader:

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Hashtable;

public class PlaylistWrangle {
    protected static Hashtable<String, Song> hashTable;
    protected static Song song;
    
    /*
     * Creates the new hashtable when the class is called.
     */
    public PlaylistWrangle() {
        hashTable = new Hashtable<String, Song>();
    }

    /*
     * @param File's path (CSV file)
     * @return The hashtable containing the values in the file.
     * Reads the CSV (with the file's path as the parameter). 
     * The values are split by each comma, and the data is stored 
     * in the hashtable.
     */
    public static Hashtable<String, Song> readCSV(String filePath) {
        try {
            String DELIMITER = ",";

            // Create the Buffered Reader
            BufferedReader csvReader = Files.newBufferedReader(
                Paths.get(filePath));

            // Read the file line by line
            String fileLine = csvReader.readLine();

            while (fileLine != null) {
                // Convert line into the various song data
                String[] songObjects = fileLine.split(DELIMITER);

                song =
                    new Song(songObjects[0], songObjects[1], 
                        songObjects[2], Double.parseDouble(songObjects[4]), 
                        Integer.parseInt(songObjects[3]),
                        songObjects[5]);

                hashTable.put(song.toString(), song);
                fileLine = csvReader.readLine();
            }

            // Close the Buffered Reader
            csvReader.close();

        } catch (IOException ioException) {
            //Prints the error message that the file was not located.
            ioException.printStackTrace();
        }

        return hashTable;
    }
    
    /*
     * Song variable is reset. This allows the user to change the various
     * parts of the song (name, artist, year, genre, length) after 
     * the reset, if desired.
     */
    public static void resetSong() {
        song = new Song(null, null, null, 0, 0, null);
    }
}
