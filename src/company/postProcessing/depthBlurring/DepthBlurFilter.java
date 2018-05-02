package company.postProcessing.depthBlurring;

import company.postProcessing.ImageRenderer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class DepthBlurFilter {

    private ImageRenderer renderer;
    private DepthMapShader shader;

    public DepthBlurFilter(int width, int height){
        shader = new DepthMapShader();
        shader.start();
        shader.connectTextureUnits();
        shader.stop();
        renderer = new ImageRenderer(width, height);
    }

    public void render(int texture, int depthmapTexture){
        shader.start();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, depthmapTexture);
        renderer.renderQuad();
        shader.stop();
    }

    public int getOutputTexture(){
        return renderer.getOutputTexture();
    }

    public void cleanUp(){
        renderer.cleanUp();
        shader.cleanUp();
    }

}
