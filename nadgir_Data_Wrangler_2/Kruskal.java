// --== CS400 File Header Information ==--
// Name: Frank Slavinsky
// Email: fslavinsky@wisc.edu
// Team: Team: BB
// TA: Bri Cochran
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * This class implements Kruskal's MST on a CS400Graph<Song> graph. This class contains one visible
 * method which takes as a parameter a CS400Graph<Song> object and returns the level order traversal
 * of the tree as a LinkedList. Because "Edge" objects need to be compared in Kruskal's MST
 * algorithm but they are not comparable in the CS400Graph, there is a sub-class ComparableEdge
 * which itself stores an edge object, the vertex the edge extends from, and is comparable to other
 * ComparableEdges.
 * 
 * @author Frank Slavinsky
 */
public class Kruskal extends CS400Graph<Song> {
  private static PriorityQueue<ComparableEdge> orderedEdges;
  private static List<List<Song>> tempSets = new LinkedList<List<Song>>();

  /**
   * This sub-class exists so that edge weights can be compared. In addition to being comparable,
   * this class also keeps track of the song/ vertex from which the edge extends-- this is used when
   * "combining" sets in the implementation of Kruskal's algorithm
   *
   */
  protected static class ComparableEdge implements Comparable<ComparableEdge> {
    protected Edge edge;
    protected Song startSong;

    public ComparableEdge(Edge edge, Song startSong) {
      this.edge = edge;
      this.startSong = startSong;
    }

    @Override
    public int compareTo(ComparableEdge o) { // compare edges based on weight
      Integer thisEdge = this.edge.weight;
      Integer thatEdge = o.edge.weight;
      return thisEdge.compareTo(thatEdge);
    }
  }

  /**
   * This method adds every edge to a PriorityQueue object "orderedEdges" to be parsed through in
   * kruskalMST(). Also, each song object is added as the sole element in a new List<Song> and that
   * list is itself added to a List<List<Song>> object field called tempSets. These "temporary sets"
   * will be combined with one another in kruskalMST().
   * 
   * @param graphToSort - the songs that met the user's filters on a graph with relations to one
   *                    another
   * @return a priority queue with the edges in order from smallest to greatest
   */
  private static PriorityQueue<ComparableEdge> initializeSort(CS400Graph<Song> graphToSort) {
    tempSets = new LinkedList<List<Song>>(); // reset to new set of sets
    orderedEdges = new PriorityQueue<ComparableEdge>(); // reset to new queue
    for (Song song : graphToSort.vertices.keySet()) {
      for (Edge edge : graphToSort.vertices.get(song).edgesLeaving) {
        orderedEdges.add(new ComparableEdge(edge, song));
      }
      List<Song> newSubset = new LinkedList<Song>();
      newSubset.add(song);
      tempSets.add(newSubset); // each song (vertex) is in its own set to begin
    }
    return orderedEdges;
  }

  /**
   * This method is the implementation of Kruskal's Algorithm. Edges are parsed through from
   * smallest to greatest with the "target" song and its associated set being appended to the "start
   * song" set. Once a "target song" set is appended, its now defunct sub-set is removed from
   * tempSets. If the "target song" and is already apart of the "start song" set than nothing other
   * than decrementing to the next shortest edge happens. The entire tree has been spanned when
   * either a) there is only one remaining sub-list (i.e. the sub-list contains all elements) in
   * tempSets or b) there were no songs to begin with, and a blank list is returned.
   * 
   * @param graphToSort - a CS400Graph<Song> object to be spanned greedily
   * @return a string of song objects in level order of the MST created through Kruskal's algorithm
   */
  protected static List<Song> kruskalMST(CS400Graph<Song> graphToSort) {
    orderedEdges = initializeSort(graphToSort);
    if (tempSets.isEmpty()) // the filters the user selected resulted in no songs matching
      return new LinkedList<Song>();

    while (tempSets.size() > 1) { // if tempSets == 1, then only one song in the tree...
      Song startSong = orderedEdges.peek().startSong;
      Song targetSong = orderedEdges.peek().edge.target.data;
      if (!getSubset(startSong).contains(targetSong)) {
        List<Song> subsetToMerge = getSubset(targetSong);
        tempSets.remove(getSubset(targetSong));
        getSubset(startSong).addAll(subsetToMerge);
      }
      orderedEdges.poll();
    }
    return tempSets.get(0);
  }

  /**
   * This helper method returns the subset that a "target song" (i.e. songToFind) exists in.
   * 
   * @param songToFind - a song object for which the subset it belongs to will be returned
   * @return the subset (List<Song>) object containing songToFind
   */
  private static List<Song> getSubset(Song songToFind) {
    for (List<Song> subset : tempSets) {
      for (Song contents : subset) {
        if (songToFind.equals(contents)) {
          return subset;
        }
      }
    }
    return null;
  }
}