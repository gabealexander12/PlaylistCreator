// --== CS400 File Header Information ==--
// Name: Shutong Lin
// Email: slin326@wisc.edu
// Team: BB
// Role: Back End Developer 1
// TA: Brianna (Bri) Cochran
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class Kruskal {

	static class SortEdge {
		CS400Graph<Song>.Vertex start, end;
		int weight;

		public CS400Graph<Song>.Vertex getStart() {
			return start;
		}

		public void setStart(CS400Graph<Song>.Vertex start) {
			this.start = start;
		}

		public CS400Graph<Song>.Vertex getEnd() {
			return end;
		}

		public void setEnd(CS400Graph<Song>.Vertex end) {
			this.end = end;
		}

		public int getWeight() {
			return weight;
		}

		public void setWeight(int weight) {
			this.weight = weight;
		}

		public SortEdge(CS400Graph<Song>.Vertex start, CS400Graph<Song>.Vertex end, int weight) {
			super();
			this.start = start;
			this.end = end;
			this.weight = weight;
		}

	}

	public static Song[] kruskal(CS400Graph<Song> g) {
		List<SortEdge> edges = new ArrayList<SortEdge>();
		CS400Graph<Song> ng = new CS400Graph<Song>();

		for (Song s : g.vertices.keySet()) {
			CS400Graph<Song>.Vertex start = g.vertices.get(s);
			ng.insertVertex(s);
			for (CS400Graph<Song>.Edge e : start.edgesLeaving) {
				edges.add(new SortEdge(start, e.target, e.weight));
			}
		}
		edges = edges.stream().sorted(Comparator.comparing(SortEdge::getWeight)).collect(Collectors.toList());
		List<Song> tempSong = new ArrayList<Song>();
		for (SortEdge e : edges) {
			ng.insertEdge(e.start.data, e.end.data, e.weight);
			if (isCirle(ng)) {
				ng.removeEdge(e.start.data, e.end.data);
			} else {
				if (!tempSong.contains(e.start.data)) {
					tempSong.add(e.start.data);
				}
				if (!tempSong.contains(e.end.data)) {
					tempSong.add(e.end.data);
				}
			}
		}
		Song[] songs = new Song[g.vertices.keySet().size()];
		tempSong.toArray(songs);
		return songs;
	}

	/**
	 * DFS check the Graph is have a 
	 * @param g
	 * @return
	 */
	public static boolean isCirle(CS400Graph<Song> g) {
		
		List<CS400Graph<Song>.Vertex>visited=new ArrayList<CS400Graph<Song>.Vertex>();
		for(Song key:g.vertices.keySet()) {
			CS400Graph<Song>.Vertex v = g.vertices.get(key);
			if(visited.contains(v)) {
				continue;
			}
			visited.add(v);
			Stack<CS400Graph<Song>.Vertex> s=new Stack<CS400Graph<Song>.Vertex>();
			s.add(v);
			List<CS400Graph<Song>.Vertex>o=new ArrayList<CS400Graph<Song>.Vertex>();
			while(!s.isEmpty()) {
				CS400Graph<Song>.Vertex t = s.pop();
				//has a cirle
				if(o.contains(t))
					return true;
				o.add(t);
				for(CS400Graph<Song>.Edge e:t.edgesLeaving) {
					s.push(e.target);
				}
			}
			visited.addAll(o);
		}
		return false;
	}
}
