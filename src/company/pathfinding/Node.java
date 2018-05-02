package company.pathfinding;

import org.lwjgl.util.vector.Vector3f;

public class Node {

    private Vector3f position;
    private Node parent;
    private double fCost;
    private double gCost;
    private double hCost;

    public Node(Vector3f position, Node parent,double gCost, double hCost) {
        this.position = position;
        this.parent = parent;
        this.gCost = gCost;
        this.hCost = hCost;
        this.fCost = gCost + hCost;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Node getParent() {
        return parent;
    }

    public double getfCost() {
        return fCost;
    }

    public double getgCost() {
        return gCost;
    }

    public double gethCost() {
        return hCost;
    }
}
