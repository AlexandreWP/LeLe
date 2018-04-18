package Project;

import java.util.LinkedHashSet;
import java.util.Set;

public class Vertex<E> {

	private E value;

	private Set<Edge> neighbors = new LinkedHashSet<>();

	public Vertex(E value) {
		this.value = value;
	}

	public void addEdge(Vertex<E> destination) {
		neighbors.add(new Edge(this, destination));
	}

	public void addEdge(Vertex<E> destination, int weight) {
		neighbors.add(new Edge(this, destination, weight));
	}

	public Set<Edge> getNeighbors() {
		return neighbors;
	}

	public E getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return value.toString();
	}

	class Edge {

		private transient Vertex<E> source;

		private Vertex<E> destination;

		private int weight = 1;

		public Edge(Vertex<E> source, Vertex<E> destination) {
			this.source = source;
			this.destination = destination;
		}

		public Edge(Vertex<E> source, Vertex<E> destination, int weight) {
			this.source = source;
			this.destination = destination;
			this.weight = weight;
		}

		public Vertex<E> getSource() {
			return source;
		}

		public Vertex<E> getDestination() {
			return destination;
		}

		public int getWeight() {
			return weight;
		}

	}
}