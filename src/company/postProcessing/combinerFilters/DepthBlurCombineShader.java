package company.postProcessing.combinerFilters;

import company.shaders.ShaderProgram;

public class DepthBlurCombineShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/company/postProcessing/combinerFilters/simpleVertex.txt";
	private static final String FRAGMENT_FILE = "src/company/postProcessing/combinerFilters/combineFragment.txt";
	
	private int location_colourTexture;
	private int location_blurTexture;
	private int location_depthTexture;
	
	protected DepthBlurCombineShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	@Override
	protected void getAllUniformLocations() {
		location_colourTexture = super.getUniformLocation("colourTexture");
		location_blurTexture = super.getUniformLocation("blurTexture");
		location_depthTexture = super.getUniformLocation("depthTexture");
	}
	
	protected void connectTextureUnits(){
		super.loadInt(location_colourTexture, 0);
		super.loadInt(location_blurTexture, 1);
		super.loadInt(location_depthTexture, 2);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
	
}
