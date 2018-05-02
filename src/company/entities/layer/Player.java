package company.entities.layer;

import company.CollisionDetection.BoundingBox;
import company.Terrians.Terrian;
import company.entities.Entity;
import company.game.GameEngineLoop;
import company.models.TexturedModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Player extends Entity {

    public static final float RUN_SPEED = 2.5f, GRAVITY = -0.5f, JUMP_POWER = .18f;

    public static boolean isMoving = false;

    private boolean isStraffing = false;

    public static float currentSpeed = 0, currentTurnSpeed = 0, upwardsSpeed = 0;
    private boolean isInAir = false;

    public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    public void move(Terrian terrian) {
        checkInputs();
        super.increaseRotation(0, currentTurnSpeed, 0);
        float distance = currentSpeed;
        if(currentSpeed > 2){
            currentSpeed=2;
        }

        float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
        float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
        float strafeDx = (float) (distance * Math.sin(Math.toRadians(super.getRotY() - 90)));
        float strafeDz = (float) (distance * Math.cos(Math.toRadians(super.getRotY() - 90)));

        float terrainHeight = terrian.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
        if(super.getPosition().y<terrainHeight) {
            upwardsSpeed = 0;
            super.getPosition().y = terrainHeight;
            isInAir = false;
        } else if(super.getPosition().y > terrainHeight) {
            super.getPosition().y = terrainHeight;
        }

        if (isStraffing){
            dx = strafeDx;
            dz = strafeDz;
        }

        if (!BoundingBox.collisionDetected(new BoundingBox(
                new Vector2f(this.getPosition().x + (dx * 2),this.getPosition().z+ (dz * 2)), new Vector2f(5f, 5f)), GameEngineLoop.boundingBoxes)){
            super.increasePosition(dx, upwardsSpeed, dz);
        }
    }

    private void jump() {
        if (!isInAir) {
            upwardsSpeed = JUMP_POWER;
            isInAir = true;
        }
    }

    private void checkInputs() {
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            this.currentSpeed = -RUN_SPEED;
            isMoving = true;
        }else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            this.currentSpeed = RUN_SPEED;
            isMoving = true;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            this.currentSpeed = -RUN_SPEED;
            isStraffing = true;
            isMoving = true;
        }else if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            this.currentSpeed = RUN_SPEED;
            isStraffing = true;
            isMoving = true;
        }else{
            isStraffing = false;
        }

        if (!Keyboard.isKeyDown(Keyboard.KEY_S)&&!Keyboard.isKeyDown(Keyboard.KEY_W)&&
                !Keyboard.isKeyDown(Keyboard.KEY_A)&&!Keyboard.isKeyDown(Keyboard.KEY_D)){
            this.currentSpeed = 0;
            isMoving = false;
        }

        if(Mouse.isButtonDown(0)) {
            this.currentTurnSpeed = (float) (Mouse.getDX() * -0.075);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            jump();
        }
    }

}
