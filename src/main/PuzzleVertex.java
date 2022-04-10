package main;

public class PuzzleVertex {

    private int label;

    public PuzzleVertex(int label) {
        this.label = label;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        PuzzleVertex vertex = (PuzzleVertex) obj;
        return (vertex.label == (this.label));
    }

    @Override
    public int hashCode() {
        return this.label;
    }

    @Override
    public String toString() {
        return "Vertex [label=" + label + "]";
    }

}
