package main;

public class PuzzleVertex {

    private final int id;

    public PuzzleVertex(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        PuzzleVertex vertex = (PuzzleVertex) obj;
        return (vertex.id == (this.id));
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Vertex [label=" + id + "]";
    }

}
