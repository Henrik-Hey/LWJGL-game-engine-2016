package company.postProcessing.contrastChanger;


import company.shaders.ShaderProgram;

public class ContrastShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/company/postProcessing/contrastChanger/contrastVertex.txt";
	private static final String FRAGMENT_FILE = "src/company/postProcessing/contrastChanger/contrastFragment.txt";
	
	public ContrastShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {	
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

}
