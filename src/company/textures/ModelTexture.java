package company.textures;

public class ModelTexture 
{
    
    private int textureID;
    private int specularMap;
    private int reflectiveMap;

    private float shineDamper = 1;
    private float reflectivity = 0;

    private int numberOfRows = 1;

    private boolean hasTransparency = false;
    private boolean useFakeLighting = false;
    private boolean hasSpecularMap  = false;
    private boolean isStatic        = false;
    private boolean hasReflectivity = false;

    public void setSpecularMap(int specularMap){
        this.specularMap = specularMap;
        this.hasSpecularMap = true;
    }

    public void setReflectiveMap(int reflectiveMap){
        this.reflectiveMap = reflectiveMap;
        this.hasReflectivity = true;
    }

    public int getReflectiveMap() {
        return reflectiveMap;
    }

    public boolean isHasReflectivity() {
        return hasReflectivity;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public void setAsNonStatic(){
        this.isStatic = true;
    }

    public boolean isStatic(){
        return isStatic;
    }

    public int getSpecularMap() {
        return specularMap;
    }

    public boolean isHasSpecularMap() {
        return hasSpecularMap;
    }

    public ModelTexture(int id)
    {
        this.textureID = id;
    }
    
    public int getID()
    {
        return this.textureID;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public boolean isUseFakeLighting() {
        return useFakeLighting;
    }

    public void setUseFakeLighting(boolean useFakeLighting) {
        this.useFakeLighting = useFakeLighting;
    }

    public boolean isHasTransparency() {
        return hasTransparency;
    }

    public void setHasTransparency(boolean hasTransparency) {
        this.hasTransparency = hasTransparency;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }

    public float getShineDamper() {
        return shineDamper;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }
}
