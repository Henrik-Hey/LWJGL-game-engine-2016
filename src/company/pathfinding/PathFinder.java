package company.pathfinding;

import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PathFinder {

    public PathFinder(){

    }

    private Comparator<Node> nodeSorter = new Comparator<Node> () {
        public int compare(Node n0, Node n1) {
            if(n1.getfCost() < n0.getfCost())return + 1;
            if(n1.getfCost() > n0.getfCost())return - 1;
            return 0;
        }
    };

    public List<Node> findPath(Vector3f start, Vector3f goal) {
        List<Node> openList   = new ArrayList<Node>();
        List<Node> closedList = new ArrayList<Node>();
        Node current = new Node(start, null, 0, getDistance(start, goal));
        openList.add(current);
        while(openList.size() > 0){
            Collections.sort(openList, nodeSorter);
            current = openList.get(0);
            if(current.getPosition().equals(goal)){
                List<Node> path = new ArrayList<>();
                while(current.getParent() != null){
                    path.add(current);
                    current = current.getParent();
                }

                closedList.clear();
                openList.clear();
                return path;
            }
            openList.remove(current);
            closedList.add(current);
            for(int i = 0; i < 9; i++){
                if(i == 4)continue;
                int x = (int)current.getPosition().x;
                int z = (int)current.getPosition().z;
                int xi = ((i % 3) - 1);
                int zi = ((i / 3) - 1);
                Vector3f a = new Vector3f(x + xi, 0, z + zi);
                double gCost = current.getgCost() + getDistance(current.getPosition(), a);
                double hCost = getDistance(a, goal);
                Node node = new Node(a, current, gCost, hCost);
                if(vecInList(closedList, a) && gCost >= current.getgCost())continue;
                if(!vecInList(openList, a) || gCost < current.getgCost())openList.add(node);
            }
        }
        return null;
    }

    private boolean vecInList(List<Node> list, Vector3f vector){
        for(Node n: list){
            if(n.getPosition().equals(vector)){
                return true;
            }
        }
        return false;
    }

    private double getDistance(Vector3f start, Vector3f goal){
        double dx = start.x - goal.x;
        double dz = start.z - goal.z;
        return Math.sqrt((dz * dz) + (dx * dx));
    }

}
