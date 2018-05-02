package company.postProcessing;

import company.models.RawModel;
import company.postProcessing.bloom.BrightFilter;
import company.postProcessing.bloom.CombineFilter;
import company.postProcessing.combinerFilters.DepthBlurCombineFilter;
import company.postProcessing.contrastChanger.ContrastChanger;
import company.postProcessing.depthBlurring.DepthBlurFilter;
import company.postProcessing.gaussianBlur.HorizontalBlur;
import company.postProcessing.gaussianBlur.VerticalBlur;
import company.renderer.Loader;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class PostProcessing {
	
	private static final float[] POSITIONS = { -1, 1, -1, -1, 1, 1, 1, -1 };	
	private static RawModel quad;
	private static ContrastChanger contrastChanger;
	private static HorizontalBlur horizontalBlur;
	private static VerticalBlur verticalBlur;
	private static BrightFilter brightFilter;
	private static CombineFilter combineFilter;
	private static DepthBlurFilter depthBlurFilter;
	private static DepthBlurCombineFilter depthBlurCombineFilter;

	public static void init(Loader loader){
		quad = loader.loadToVAO(POSITIONS, 2);
		contrastChanger = new ContrastChanger();
		horizontalBlur = new HorizontalBlur(Display.getWidth()/4, Display.getHeight()/4);
		verticalBlur = new VerticalBlur(Display.getWidth()/4, Display.getHeight()/4);
		brightFilter = new BrightFilter(Display.getWidth()/2, Display.getHeight()/2);
		combineFilter = new CombineFilter(Display.getWidth(), Display.getHeight());
		depthBlurFilter = new DepthBlurFilter(Display.getWidth(), Display.getHeight());
		depthBlurCombineFilter = new DepthBlurCombineFilter(Display.getWidth(), Display.getHeight());
	}

	static boolean isNear = false;

	public static void doPostProcessing(int colourTexture, int brightTexture, int depthTexture){
		start();

		horizontalBlur.render(brightTexture);
		verticalBlur.render(horizontalBlur.getOutputTexture());
		combineFilter.render(colourTexture, verticalBlur.getOutputTexture());

		if(Keyboard.isKeyDown(Keyboard.KEY_Q)){
			isNear = true;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_E)){
			isNear = false;
		}

		if(isNear){
			horizontalBlur.render(combineFilter.getOutputTexture());
			verticalBlur.render(horizontalBlur.getOutputTexture());
			depthBlurFilter.render(verticalBlur.getOutputTexture(), depthTexture);
			depthBlurCombineFilter.render(combineFilter.getOutputTexture(), verticalBlur.getOutputTexture(), depthTexture);
			contrastChanger.render(depthBlurCombineFilter.getOutputTexture());
		}else{
			contrastChanger.render(combineFilter.getOutputTexture());
		}

		end();
	}
	
	public static void cleanUp(){
		horizontalBlur.cleanUp();
		verticalBlur.cleanUp();
		contrastChanger.cleanup();
		brightFilter.cleanUp();
		combineFilter.cleanUp();
	}
	
	private static void start(){
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}
	
	private static void end(){
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}


}
