package company.shaders;

import company.entities.Camera;
import company.entities.Light;
import company.toolBox.Maths;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class StaticShader extends ShaderProgram{

    private static final String VERTEX_FILE = "src/company/shaders/vertexShader.txt";
    private static final String FRAGMENT_FILE = "src/company/shaders/fragmentShader.txt";

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightPosition;
    private int location_lightColor;
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_useFakeLighting;
    private int location_skyColor;
    private int location_toShadowMapSpace;
    private int location_shadowMapSampler;
    private int location_specularMap;
    private int location_usesSpecularMap;
    private int location_modelTexture;
    private int location_isNonStatic;
    private int location_numberOfRows;
    private int location_offset;
    private int location_cameraPosition;
    private int location_enviromentMap;
    private int location_reflectivityMap;
    private int location_usesReflectivityMap;

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity = super.getUniformLocation("reflectivity");
        location_useFakeLighting = super.getUniformLocation("useFakeLighting");
        location_skyColor = super.getUniformLocation("skyColor");
        location_lightPosition = super.getUniformLocation("lightPosition");
        location_lightColor = super.getUniformLocation("lightColor");
        location_toShadowMapSpace = super.getUniformLocation("toShadowMapSpace");
        location_shadowMapSampler = super.getUniformLocation("shadowMap");
        location_specularMap = super.getUniformLocation("specularMap");
        location_usesSpecularMap = super.getUniformLocation("usesSpecularMap");
        location_modelTexture = super.getUniformLocation("modelTexture");
        location_isNonStatic = super.getUniformLocation("isNonStatic");
        location_numberOfRows = super.getUniformLocation("numberOfRows");
        location_offset = super.getUniformLocation("offset");
        location_cameraPosition = super.getUniformLocation("cameraPosition");
        location_enviromentMap = super.getUniformLocation("enviromentMap");
        location_reflectivityMap = super.getUniformLocation("reflectivityMap");
        location_usesReflectivityMap = super.getUniformLocation("usesReflectivityMap");
    }

    public void connectTextureUnits() {
        super.bindFragOutput(0, "out_Colour");
        super.bindFragOutput(1, "out_BrightColour");
        super.loadInt(location_shadowMapSampler, 5);
        super.loadInt(location_modelTexture, 0);
        super.loadInt(location_specularMap, 1);
        super.loadInt(location_enviromentMap, 6);
        super.loadInt(location_reflectivityMap, 7);
    }

    public void usesReflectivityMap(boolean usesReflectivityMap){
        super.loadBoolean(location_usesReflectivityMap, usesReflectivityMap);
    }

    public void loadCameraPosition(Vector3f vector3f){
        super.loadVector(location_cameraPosition, vector3f);
    }

    public void loadNumberOfRows(float numberOfRows){
        super.loadFloat(location_numberOfRows, numberOfRows);
    }

    public void loadOffset(float x, float y){
        super.load2DVector(location_offset, new Vector2f(x, y));
    }

    public void usesSpecularMap(boolean usesMap){
        super.loadBoolean(location_usesSpecularMap, usesMap);
    }

    public void isNonStatic(boolean isStatic){
        super.loadBoolean(location_isNonStatic, isStatic);
    }

    public void loadSkyColor(float r, float g, float b) {
        super.loadVector(location_skyColor, new Vector3f(r,g,b));
    }

    public void loadUseFakeLighting(boolean useFake) {
        super.loadBoolean(location_useFakeLighting, useFake);
    }

    public void loadShineVariables(float shineDamper, float reflectivity) {
        super.loadFloat(location_shineDamper, shineDamper);
        super.loadFloat(location_reflectivity, reflectivity);
    }
    
    public void loadTransformationMatrix(Matrix4f matrix)
    {
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    public void loadLights(Light light) {
        super.loadVector(location_lightPosition, light.getPosition());
        super.loadVector(location_lightColor, light.getColor());
    }
    
    public void loadViewMatrix(Camera camera)
    {
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(location_viewMatrix, viewMatrix);
    }

    public void loadToShadowMapSpaceMatrix(Matrix4f matrix){
        super.loadMatrix(location_toShadowMapSpace, matrix);
    }


    public void loadProjectionMatrix(Matrix4f projection)
    {
        super.loadMatrix(location_projectionMatrix, projection);
    }
}
