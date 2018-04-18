package Project;

import java.util.LinkedHashSet;
import java.util.Set;

public class Path<E> {

	private Set<Vertex<E>> vertices= new LinkedHashSet<>();
	
	private Integer weight = 0;

	public Set<Vertex<E>> getVertices() {
		return vertices;
	}

	public void setVertices(Set<Vertex<E>> vertices) {
		this.vertices = vertices;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Path<E> getCopy() {
		Path<E> newPath = new Path<>();
		newPath.setWeight(getWeight());
		for (Vertex<E> vertex : vertices) {
			newPath.getVertices().add(vertex);
		}
		return newPath;
	}

	@Override
	public String toString() {
		return vertices.toString() + " -> weight: " + weight;
	}
	
}