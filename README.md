# PlaylistCreator
Project Title: Playlist Builder Helper

Brief Project Description:
Our app will read from a csv file the user’s “music library” and store each song within a hashtable, the key being created from the concatenation of the song title and artist name. From there, we will use streams to iterate through the entire contents of the hashtable based on the user’s search key (eg I want all of my “Queen” music or I want all of my music released in 1984 or any combination of fields. Then, after the user has “refined” their library, a “organized” playlist will be created from those selected songs by using a MST algorithm. Each song will branch to each other song with the weight determined by an algorithm we will write. Thus, the playlist (MST) will play songs in a “sorted” order-- the playlist will have a “flow” to it. 

Four Chosen Requirements that this Project Fulfills:

Hash Table data structure: Stores all the songs and under the key using concatenation of the song title and artist name. It will store an object class that holds all the information needed. 

Streams: used to iterate through the contents of the hashtable with a specified user key to create a playlist(type array)

CSV: file that holds all the data for this project(songs, artists, year, genre, length)

Prim's or Kruskal's minimum spanning tree algorithm: Used to organize the playlist using an algorithm to create an ordered playlist based on similarity. 
