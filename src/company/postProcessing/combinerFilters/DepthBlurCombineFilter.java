package company.postProcessing.combinerFilters;

import company.postProcessing.ImageRenderer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class DepthBlurCombineFilter {
	
	private ImageRenderer renderer;
	private DepthBlurCombineShader shader;

	public DepthBlurCombineFilter(int width, int height){
		shader = new DepthBlurCombineShader();
		shader.start();
		shader.connectTextureUnits();
		shader.stop();
		renderer = new ImageRenderer(width, height);
	}
	
	public void render(int colourTexture, int highlightTexture, int depthTexture){
		shader.start();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, colourTexture);
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, highlightTexture);
		GL13.glActiveTexture(GL13.GL_TEXTURE2);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, depthTexture);
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
