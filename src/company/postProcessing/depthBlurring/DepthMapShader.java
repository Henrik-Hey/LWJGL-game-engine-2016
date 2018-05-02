package company.postProcessing.depthBlurring;

import company.shaders.ShaderProgram;

public class DepthMapShader extends ShaderProgram {

    private static final String VERTEX_FILE = "src/company/postProcessing/depthBlurring/DepthVertexShader.txt";
    private static final String FRAGMENT_FILE = "src/company/postProcessing/depthBlurring/DepthFragmentShader.txt";

    private int location_colourTexture;
    private int location_depthMapTexture;

    protected DepthMapShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        location_colourTexture = super.getUniformLocation("colourTexture");
        location_depthMapTexture = super.getUniformLocation("depthMapTexture");
    }

    protected void connectTextureUnits(){
        super.loadInt(location_colourTexture, 0);
        super.loadInt(location_depthMapTexture, 1);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }

}
