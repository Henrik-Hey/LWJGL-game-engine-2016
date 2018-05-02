//Henrik Hey
//Last revised Feb 25 2016
//3D Java Game Engine
package company.game;

import company.AI.BasicAI;
import company.AI.FollowerAI;
import company.CollisionDetection.BoundingBox;
import company.GUIs.GUIRenderer;
import company.GUIs.GUITexture;
import company.Terrians.Terrian;
import company.entities.Camera;
import company.entities.Entity;
import company.entities.Light;
import company.entities.layer.Player;
import company.fontMeshCreator.FontType;
import company.fontMeshCreator.GUIText;
import company.fontRendering.TextMaster;
import company.gameplay.projectiles.Projectile;
import company.gameplay.weapons.Gun;
import company.models.RawModel;
import company.models.TexturedModel;
import company.objLoading.ModelData;
import company.objLoading.OBJFileLoader;
import company.objLoading.OBJLoader;
import company.particles.Particle;
import company.particles.ParticleMaster;
import company.particles.ParticleSystem;
import company.pathfinding.Node;
import company.pathfinding.PathFinder;
import company.postProcessing.Fbo;
import company.postProcessing.PostProcessing;
import company.renderer.DisplayManager;
import company.renderer.Loader;
import company.renderer.MasterRenderer;
import company.textures.ModelTexture;
import company.textures.TerrainTexture;
import company.textures.TerrainTexturePack;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameEngineLoop 
{
//is used to check if the menu is open
    public static boolean menuIsOpen = true;

//are public and static to allow the menu class to access them
    public static List<Entity> entities = new ArrayList<Entity>();
    public static List<GUITexture> GUIs = new ArrayList<GUITexture>();
    public static ArrayList<BoundingBox> boundingBoxes = new ArrayList<>();

    public static void main(String[] args) 
    {
        DisplayManager.createDisplay();
        Loader loader = new Loader();
        TextMaster.init(loader);

        /*FONT GENERATION**/

        FontType Font_Tahoma = new FontType(loader.loadTexture("tahoma"), new File("res/tahoma.fnt"));
        GUIText text_1;

        /*END OF FONT GENERATION**/

        /*MODEL CREATION STUFF**/

        TexturedModel staticModel = new TexturedModel(OBJLoader.loadOBJModel("cherry", loader),
                new ModelTexture(loader.loadTexture("cherry")));
        TexturedModel staticModel1 = new TexturedModel(OBJLoader.loadOBJModel("wall", loader),
                new ModelTexture(loader.loadTexture("wallSet2")));
        TexturedModel staticModel2 = new TexturedModel(OBJLoader.loadOBJModel("cube", loader),
                new ModelTexture(loader.loadTexture("tree")));
        TexturedModel staticModel3 = new TexturedModel(OBJLoader.loadOBJModel("v_pist_deagle", loader),
                new ModelTexture(loader.loadTexture("DEAGLE__1")));
        TexturedModel cube = new TexturedModel(OBJLoader.loadOBJModel("cube", loader),
                new ModelTexture(loader.loadTexture("projectile")));


        ModelTexture texture = staticModel.getTexture();
        staticModel.getTexture().setHasTransparency(true);
        texture.setSpecularMap(loader.loadTexture("cherryS"));
        texture.setAsNonStatic();
        ModelTexture cubeTexture = cube.getTexture();
        staticModel.getTexture().setHasTransparency(true);
        cubeTexture.setSpecularMap(loader.loadTexture("projectileS"));
        cubeTexture.setAsNonStatic();
        ModelTexture texture1 = staticModel1.getTexture();
        texture1.setSpecularMap(loader.loadTexture("projectileS"));
        texture1.setReflectiveMap(loader.loadTexture("wallSet2RM"));
        texture1.setNumberOfRows(2);
        staticModel1.getTexture().setHasTransparency(true);
        ModelTexture texture2 = staticModel2.getTexture();
        texture2.setAsNonStatic();
        staticModel2.getTexture().setHasTransparency(true);
        ModelTexture texture3 = staticModel3.getTexture();
        texture3.setSpecularMap(loader.loadTexture("DEAGLE__1S"));
        staticModel3.getTexture().setHasTransparency(true);

        texture.setShineDamper(10);
        texture.setReflectivity(0.1f);
        texture1.setShineDamper(10);
        texture1.setReflectivity(0.6f);
        texture2.setShineDamper(1000000);
        texture2.setReflectivity(0.0f);
        texture3.setShineDamper(10);
        texture3.setReflectivity(0.1f);
        cubeTexture.setShineDamper(10);
        cubeTexture.setReflectivity(0.1f);

        /**END OF MODEL CREATION STUFF**/


        /**TERRAIN LOADER STUFF**/

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("TileImage"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture,gTexture,bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("black"));
        Terrian terrian = new Terrian( 0, 0, loader, texturePack, blendMap, "heightmapFlat");

        /**END OF TERRAIN LOADER STUFF**/


        /**ENTITY LISTS AND CREATORS**/

        Random random = new Random();

        for(int i = 0; i < 20; i ++ ){
            float x = random.nextFloat() * 800;
            float z = random.nextFloat() * 800;
            float y = terrian.getHeightOfTerrain(x, z);
            entities.add(new Entity(staticModel, new Vector3f(x,y, z+2),0,360 * random.nextFloat(),0,7f));
            boundingBoxes.add(new BoundingBox(new Vector2f(x,z), new Vector2f(13, 13)));
        }

        for(int i = 0; i < 7; i ++ ){
            float x = 518;
            float z = (i * 24) + 400;
            float y = terrian.getHeightOfTerrain(x, z);
            entities.add(new Entity(staticModel1, 0, new Vector3f(x,y, z),0,0,90,2f));
            boundingBoxes.add(new BoundingBox(new Vector2f(x,z), new Vector2f(10, 24)));
        }
        for(int i = 0; i < 7; i ++ ){
            float x = 550;
            float z = (i * 24) + 400;
            float y = terrian.getHeightOfTerrain(x, z);
            entities.add(new Entity(staticModel1, 0, new Vector3f(x,y, z),0,180,90,2f));
            boundingBoxes.add(new BoundingBox(new Vector2f(x,z), new Vector2f(10, 24)));
        }

        for(int i = 0; i < 7; i ++ ){
            float x = 521;
            float z = (i * 24) + 400;
            float y = terrian.getHeightOfTerrain(x, z) + 23;
            entities.add(new Entity(staticModel1, 2, new Vector3f(x,y, z),0,0,0,2f));
        }

        Entity playerGun1 = new Entity(staticModel3, new Vector3f(1,1, 1 + random.nextFloat()),0,0,0,2.5f);
        entities.add(playerGun1);
        Entity playerGun2 = new Entity(staticModel3, new Vector3f(1,1, 1 + random.nextFloat()),0,0,0,2.5f);
        entities.add(playerGun2);

        /**AI**/

            BasicAI AI = new BasicAI(staticModel2, new Vector3f(500, 0, 400), 0,0,0,1f);
            entities.add(AI);

        /**END OF AI**/

        /**END OF ENTITY LISTS AND CREATORS**/

        Player player = new Player(staticModel2, new Vector3f(400, 0, 400), 0,0,0,0.1f);

        Light sun = new Light(new Vector3f(1000000, 1500000, -1000000), new Vector3f(1f, 1f, 1f));
        Camera camera = new Camera(player);
        MasterRenderer renderer = new MasterRenderer(loader, camera);
        GUIRenderer guiRenderer = new GUIRenderer(loader);

        Fbo fbo = new Fbo(Display.getWidth(), Display.getHeight());
        Fbo outputFbo = new Fbo(Display.getWidth(), Display.getHeight(), Fbo.DEPTH_RENDER_BUFFER);
        Fbo outputFbo2 = new Fbo(Display.getWidth(), Display.getHeight(), Fbo.DEPTH_TEXTURE);
        PostProcessing.init(loader);

        //GUITexture GUI1 = new GUITexture(renderer.getShadowMapTexture(), new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
        //GUIs.add(GUI1);

        Gun gun1 = new Gun(playerGun1, player, -3.5f, 0, new Vector3f(-6,7,-6));
        Gun gun2 = new Gun(playerGun2, player,  3.5f, 0, new Vector3f(-6,7,-6));

        Projectile.init(cube);

        int choice = 0;
        BoundingBox AIBox = new BoundingBox(new Vector2f(500,300), new Vector2f(13, 13));
        FollowerAI followerAI = new FollowerAI(staticModel, new Vector3f(500, terrian.getHeightOfTerrain(500, 500), 500), 0, 0, 0, 7f,
                AIBox,100,"lol",20);
        boundingBoxes.add(AIBox);
        entities.add(followerAI);

        ParticleMaster.init(loader, renderer.getProjectionMatrix());

        ParticleSystem particleSystem = new ParticleSystem(40, 5, -0.0f, 1, 2);
        particleSystem.setScaleError(1f);
        particleSystem.setSpeedError(4);
        particleSystem.setDirection(new Vector3f(1,3,1), 2.5f);
        particleSystem.randomizeRotation();

        while(!Display.isCloseRequested()) {

            //AIBox.setPosition(new Vector2f(followerAI.getPosition().x, followerAI.getPosition().z + 2));
            ParticleMaster.update();
            //followerAI.moveTo(player.getPosition());

            if (Mouse.isButtonDown(1)){
                if (choice == 0){
                    Projectile.fire(gun1.getWeapon());
                    choice = 1;
                }else{
                    Projectile.fire(gun2.getWeapon());
                    choice = 0;
                }
            }

            //playerGun1.increaseRotation(0,1,0);
            gun1.update(player, camera);
            gun2.update(player, camera);

            particleSystem.generateParticles(new Vector3f(400, 40, 400));

            //renders everything
            fbo.bindFrameBuffer();

            ParticleMaster.render(camera);
            for(Entity entity: entities){
                renderer.processEntity(entity);
            }
            for(Projectile projectile: Projectile.projectiles){
                renderer.processEntity(projectile);
                projectile.update();
            }
            renderer.processEntity(AI);
            renderer.processTerrians(terrian);
            renderer.processEntity(player);
            renderer.render(camera, sun);
            renderer.renderShadowMap(entities, sun);

            ParticleMaster.render(camera);

            fbo.unbindFrameBuffer();

            fbo.resloveToFBO(GL30.GL_COLOR_ATTACHMENT0, outputFbo);
            fbo.resloveToFBO(GL30.GL_COLOR_ATTACHMENT1, outputFbo2);

            //post-processing
            PostProcessing.doPostProcessing(
                    outputFbo.getColourTexture(),
                    outputFbo2.getColourTexture(),
                    outputFbo2.getDepthTexture()
            );

            //AI
            AI.moveAI(terrian);

            //core renderer
            guiRenderer.Render(GUIs);

            //player mechanics and such
            player.move(terrian);
            camera.move();

            //renders GUI Text!
            String text = "Is Colliding: "
                    + BoundingBox.collisionDetected(new BoundingBox(new Vector2f(player.getPosition().x,player.getPosition().z),
                    new Vector2f(5f, 5f)), boundingBoxes);
            text_1 = new GUIText(text, 1, Font_Tahoma, new Vector2f(0.001f, 0.001f), 1f, false);
            text_1.setColour(0.5f,1f,0.5f);
            TextMaster.loadText(text_1);
            TextMaster.render();
            TextMaster.removeText(text_1);

            Projectile.cleanUp();

            //updates the display
            DisplayManager.updateDisplay();
        }
        PostProcessing.cleanUp();
        fbo.cleanUp();
        outputFbo.cleanUp();
        guiRenderer.cleanUp();
        renderer.cleanUp();
        loader.cleanUP();
        DisplayManager.destroyDisplay();
    }
}