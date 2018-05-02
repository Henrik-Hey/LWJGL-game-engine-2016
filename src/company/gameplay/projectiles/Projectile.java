package company.gameplay.projectiles;

import company.CollisionDetection.BoundingBox;
import company.entities.Entity;
import company.game.GameEngineLoop;
import company.models.TexturedModel;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;

public class Projectile extends Entity{

    public static ArrayList<Projectile> projectiles = new ArrayList<>();
    private static ArrayList<BoundingBox> projectileBoundingBoxes = new ArrayList<>();

    private static TexturedModel texturedModel;

    private float lifeLength;
    private BoundingBox projectileBox;

    private Projectile(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, int lifeLength, BoundingBox projectileBox) {
        super(model, position, rotX, rotY, rotZ, scale);
        this.lifeLength = lifeLength;
        this.projectileBox = projectileBox;
        projectileBoundingBoxes.add(projectileBox);
    }

    public static void init(TexturedModel model){
        texturedModel = model;
    }

    public static void fire(Entity weapon){
        float dx = weapon.getPosition().x + (float)(0 * Math.sin(Math.toRadians(weapon.getRotY())));
        float dy = weapon.getPosition().y + 1;
        float dz = weapon.getPosition().z + (float)(0 * Math.cos(Math.toRadians(weapon.getRotY())));
        Projectile projectile = new Projectile(texturedModel, new Vector3f(dx, dy, dz), weapon.getRotX(), weapon.getRotY() + 90,
                weapon.getRotZ(), 1, 60, new BoundingBox(new Vector2f(dx,dz), new Vector2f(1, 1)));
        projectiles.add(projectile);
    }

    public static void cleanUp(){
        ArrayList<Projectile>deadProjectiles = new ArrayList<>();
        ArrayList<BoundingBox>deadBoxes = new ArrayList<>();
        for(int i = 0; i < projectiles.size(); i++){
            if(projectiles.get(i).getLifeLength() <= 0) {
                deadProjectiles.add(projectiles.get(i));
                deadBoxes.add(projectileBoundingBoxes.get(i));
            }
        }
        for(int i = 0; i < deadProjectiles.size(); i++){
            projectiles.remove(deadProjectiles.get(i));
            //deadBoxes.remove(deadBoxes.get(i));
        }
    }

    public void update(){
        projectileBox.setPosition(new Vector2f(super.getPosition().getX(), super.getPosition().getZ()));
        if (lifeLength > 0) {
            float speed = 5f;
            float dx = (float) (speed * Math.sin(Math.toRadians(super.getRotY())));
            float dy = 0;
            float dz = (float) (speed * Math.cos(Math.toRadians(super.getRotY())));
            super.increasePosition(-dx, dy, -dz);
            lifeLength--;
            if (BoundingBox.collisionDetected(projectileBox, GameEngineLoop.boundingBoxes)) lifeLength = -100;
        }
    }

    private float getLifeLength() {
        return lifeLength;
    }
}
