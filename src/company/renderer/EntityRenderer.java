package company.renderer;

import company.Skybox.SkyboxRenderer;
import company.entities.Camera;
import company.entities.Entity;
import company.models.RawModel;
import company.models.TexturedModel;
import company.shaders.StaticShader;
import company.textures.ModelTexture;
import company.toolBox.Maths;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import java.util.List;
import java.util.Map;

public class EntityRenderer {

    private StaticShader shader;
    private SkyboxRenderer cubeMap;
    
    public EntityRenderer(StaticShader shader, Matrix4f projectionMatrix, SkyboxRenderer cubeMap)
    {
        this.shader = shader;
        this.cubeMap = cubeMap;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.connectTextureUnits();
        shader.stop();
    }

    public void render(Map<TexturedModel, List<Entity>> entities, Matrix4f matrix4f, Camera camera) {
        shader.loadToShadowMapSpaceMatrix(matrix4f);
        bindEnviromentMap();
        for(TexturedModel model: entities.keySet()) {
            prepareTexturedModel(model, camera);
            List<Entity> batch = entities.get(model);
            for(Entity entity:batch) {
                prepareInstance(entity);
                GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }
            unbindTexturedModel();
        }
    }

    private void bindEnviromentMap(){
        GL13.glActiveTexture(GL13.GL_TEXTURE6);
        GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, cubeMap.getTexture());
    }

    private void prepareTexturedModel(TexturedModel model, Camera camera) {
        RawModel rawModel = model.getRawModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        ModelTexture texture = model.getTexture();
        shader.loadCameraPosition(camera.getPosition());
        shader.loadNumberOfRows(texture.getNumberOfRows());
        if(texture.isHasTransparency()){
            MasterRenderer.disableCulling();
        }
        shader.loadUseFakeLighting(texture.isUseFakeLighting());
        shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
        shader.usesSpecularMap(texture.isHasSpecularMap());
        shader.isNonStatic(texture.isStatic());
        if(texture.isHasSpecularMap()){
            GL13.glActiveTexture(GL13.GL_TEXTURE1);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getSpecularMap());
        }
        shader.usesReflectivityMap(texture.isHasReflectivity());
        if(texture.isHasReflectivity()){
            GL13.glActiveTexture(GL13.GL_TEXTURE7);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getReflectiveMap());
        }
    }

    private void unbindTexturedModel() {
        MasterRenderer.enableCulling();
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void prepareInstance(Entity entity) {
        Matrix4f translationMatrix = Maths.createTransformationMatrix(entity.getPosition(),
                entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
        shader.loadTransformationMatrix(translationMatrix);
        shader.loadOffset(entity.getTextureXOffset(), entity.getTextureYOffset());
    }
    
}
