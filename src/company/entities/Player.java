package company.entities;

import company.Terrians.Terrian;
import company.models.TexturedModel;
import company.renderer.DisplayManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Player extends Entity{

    public static final float RUN_SPEED = 2.5f, TURN_SPEED = 0.2f,
                               GRAVITY = -50f, JUMP_POWER = 10f,
                               TERRAIN_HEIGHT = 0;

    private float currentSpeed = 0, currentTurnSpeed = 0, upwardsSpeed = 0;
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
        float terrainHeight = terrian.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
        if(super.getPosition().y<terrainHeight) {
            upwardsSpeed = 0;
            super.getPosition().y = terrainHeight;
            isInAir = false;
        } else if(super.getPosition().y > terrainHeight) {
            upwardsSpeed = 0;
            upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds()/0.5;
            isInAir = true;
        }
        super.increasePosition(dx, upwardsSpeed ,dz);
    }

    private void jump() {
        if (!isInAir) {
            this.upwardsSpeed = JUMP_POWER;
            isInAir = true;
        }
    }

    private void checkInputs() {
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            this.currentSpeed = -RUN_SPEED;
        }else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            this.currentSpeed = RUN_SPEED;
        }if (!Keyboard.isKeyDown(Keyboard.KEY_S)&&!Keyboard.isKeyDown(Keyboard.KEY_W)){
            this.currentSpeed = 0;
        }

        if(Mouse.isButtonDown(0)) {
            this.currentTurnSpeed = (float) (Mouse.getDX() * -0.075);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            jump();
        }
    }

}
