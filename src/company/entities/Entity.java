package company.entities;

import company.models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

public class Entity 
{
    
    private TexturedModel model;
    private Vector3f position;
    private float rotX, rotY, rotZ;
    private float scale;
    private int textureIndex = 0;

    public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale)
    {
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
    }

    public Entity(TexturedModel model, int index, Vector3f position, float rotX, float rotY, float rotZ, float scale)
    {
        this.model = model;
        this.textureIndex = index;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
    }

    public void setTextureIndex(int index){
        textureIndex = index;
    }

    public float getTextureXOffset(){
        int column = textureIndex % model.getTexture().getNumberOfRows();
        return (float)column / model.getTexture().getNumberOfRows();
    }

    public float getTextureYOffset(){
        int column = textureIndex / model.getTexture().getNumberOfRows();
        return (float)column / model.getTexture().getNumberOfRows();
    }
    
    public void increasePosition(float dx, float dy, float dz)
    {
        this.position.x+=dx;
        this.position.y+=dy;
        this.position.z+=dz;
    }

    public void increaseRotation(float dx, float dy, float dz)
    {
        this.rotX+=dx;
        this.rotY+=dy;
        this.rotZ+=dz;
    }
    
    public TexturedModel getModel() {
        return model;
    }

    public void setRotation(Vector3f rotation){
        this.rotX = rotation.x;
        this.rotY = rotation.y;
        this.rotZ = rotation.z;
    }

    public Vector3f getRotation(){
        return new Vector3f(this.rotX, this.rotY, this.rotZ);
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getRotX() {
        return rotX;
    }

    public float getRotY() {
        return rotY;
    }

    public float getRotZ() {
        return rotZ;
    }

    public float getScale() {
        return scale;
    }
      
    
    
}
