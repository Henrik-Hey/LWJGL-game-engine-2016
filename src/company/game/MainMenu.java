package company.game;

import company.GUIs.GUIRenderer;
import company.GUIs.GUITexture;
import company.Terrians.Terrian;
import company.entities.Camera;
import company.entities.Entity;
import company.entities.Light;
import company.entities.layer.Player;
import company.renderer.DisplayManager;
import company.renderer.Loader;
import company.renderer.MasterRenderer;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

public class MainMenu {

    public static void CreateMenu(Camera camera, MasterRenderer renderer, Loader loader, GUIRenderer guiRenderer, Terrian terrian
                                    , Player player, Light sun) {
        //is a counter for the map edge
        int mapEdge = 0;
        //image textures for the GUI
        GUITexture menuItem1 = new GUITexture(loader.loadTexture("Play"), new Vector2f(0.5f, 0.50f), new Vector2f(0.25f, 0.25f));
        GUITexture menuItem2 = new GUITexture(loader.loadTexture("Help"), new Vector2f(0.5f, 0.20f), new Vector2f(0.25f, 0.25f));
        GUITexture menuItem3 = new GUITexture(loader.loadTexture("Close"), new Vector2f(0.5f, -0.10f), new Vector2f(0.25f, 0.25f));
        GameEngineLoop.GUIs.add(menuItem1);
        GameEngineLoop.GUIs.add(menuItem2);
        GameEngineLoop.GUIs.add(menuItem3);

        while(GameEngineLoop.menuIsOpen) {
            camera.move();
            camera.pitch = 0;//keeps the camera still in terms of pitch
            for(Entity tree: GameEngineLoop.entities){
                renderer.processEntity(tree);
            }

            renderer.processTerrians(terrian);
            renderer.processEntity(player);
            renderer.render(camera, sun);

            guiRenderer.Render(GameEngineLoop.GUIs);
            DisplayManager.updateDisplay();

            //sets the player(camera, because its a fps) equal to the height of the terrain for the left to right staffing of the menu
            player.getPosition().y = terrian.getHeightOfTerrain(player.getPosition().x, player.getPosition().z);

            //checks if the camera has it the edge of the map and if so reverses its direction
            if (mapEdge < 300) {
                player.getPosition().x++;
                mapEdge++;
            } else {
                player.getPosition().x--;
                if (player.getPosition().x == 100) {
                    mapEdge = 0;
                }
            }
            //checks if the mouse is over the location of the play button
            if(Mouse.getX()>820 && Mouse.getX()<975 && Mouse.getY()>505 && Mouse.getY()<600) {
                if(Mouse.isButtonDown(0)) {//if the mouse is left clicked the GUI is removed and the menu is closed
                    GameEngineLoop.GUIs.remove(menuItem1);
                    GameEngineLoop.GUIs.remove(menuItem2);
                    GameEngineLoop.GUIs.remove(menuItem3);
                    startGame(player);
                }
            }
        }

    }

    //closes Menu thus starting game
    private static void startGame(Player player) {
        GameEngineLoop.menuIsOpen = false;
    }

}
