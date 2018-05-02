package company.AI;

import company.Terrians.Terrian;
import company.entities.Entity;
import company.models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

import java.util.Random;

public class BasicAI extends Entity{

    private static Terrian terrian;
    private static boolean hitEnd = false, hit20 = true;

    private static Random random = new Random(2);
    private static int counter = 0;

    public BasicAI(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    public  void moveAI(Terrian terrian) {
        if(super.getPosition().x < terrian.SIZE && !hitEnd) {
            super.getPosition().x += random.nextFloat();
        } else if (super.getPosition().x > terrian.SIZE){
            hitEnd = true;
        }
        if(hitEnd){
            super.getPosition().x -= random.nextFloat();
        }
        if(super.getPosition().x < 0) {
            hitEnd = false;
        }
        if (!hit20) {
            super.getPosition().z += random.nextFloat();
            counter ++;
        } else if(hit20){
            super.getPosition().z -= random.nextFloat();
            counter --;
        }
        if (counter >= 100) {
            hit20 = true;
        }else if (counter <= 0){
            hit20 = false;
        }
        super.getPosition().y = terrian.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
    }

}
