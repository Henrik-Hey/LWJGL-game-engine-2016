package company.AI;

import company.CollisionDetection.BoundingBox;
import company.entities.Entity;
import company.entities.layer.Player;
import company.gameplay.weapons.Gun;
import company.models.TexturedModel;
import company.pathfinding.Node;
import company.pathfinding.PathFinder;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;

public class FollowerAI extends Entity{

    private List<Node> path;
    private PathFinder pathFinder;
    private Gun gun;
    private float hearingRange;

    public FollowerAI(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale,
                      BoundingBox boundingBox, int health, String type, float hearingRange) {
        super(model, position, rotX, rotY, rotZ, scale);
        pathFinder = new PathFinder();
        this.hearingRange = hearingRange;
    }


    public void moveTo(Vector3f goal){
        Vector3f goalPosition = new Vector3f((int)goal.x, 0, (int)goal.z);
        path = pathFinder.findPath(super.getPosition(), goalPosition);
        if(path != null){
            if(path.size() > 0){
                Vector3f pos = new Vector3f((int)path.get(path.size() - 1).getPosition().x, 0, (int)path.get(path.size() - 1).getPosition().z);
                if(super.getPosition().x < pos.x){
                    super.increasePosition(1f, 0, 0);
                }
                if(super.getPosition().x > pos.x){
                    super.increasePosition(-1f, 0, 0);
                }
                if(super.getPosition().z < pos.z){
                    super.increasePosition(0, 0, 1f);
                }
                if(super.getPosition().z > pos.z){
                    super.increasePosition(0, 0, -1f);
                }
            }
        }
    }

    public void shoot(Vector3f target){

    }

    public void findPlayer(){

    }

    public boolean CanHearPlayer(Player player){
        return isInCircle(super.getPosition().x, super.getPosition().z, player.getPosition().x, player.getPosition().z, hearingRange);
    }

    private boolean isInCircle(float xCenter, float zCenter, float x, float z, float radius){
        x+=1;
        z+=1;
        if(Math.pow((x - xCenter), 2) + Math.pow((z - zCenter), 2) < Math.pow(radius, 2)){
            return true;
        }
        return false;
    }

    public void setGun(Gun gun){
        this.gun = gun;
    }

}
