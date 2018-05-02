package company.renderer;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.*;

public class DisplayManager {
    private static final int WIDTH = 1280, HEIGHT = 720, FPS = 120;

    private static long lastFrameTime;
    private static float delta;

    public static void createDisplay() {
        ContextAttribs attribs = new ContextAttribs(3, 3)
                .withForwardCompatible(true)
                .withProfileCore(true);

        System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.setTitle("3D game engine V.0.0.1");
            Display.create(new PixelFormat().withDepthBits(24), attribs);
            Display.setFullscreen(true);
            GL11.glEnable(GL13.GL_MULTISAMPLE);
        } catch (LWJGLException e) {
            System.err.println("ERROR: Could not create window!");
        }

        GL11.glViewport(0, 0, WIDTH, HEIGHT);
        lastFrameTime = currentTime();
    }

    public static void updateDisplay() {
        Display.sync(FPS);
        Display.update();
        long currentFrameTime = currentTime();
        delta = (currentFrameTime - lastFrameTime) / 1000f;
        lastFrameTime = currentFrameTime;
    }

    public static float getFrameTimeSeconds() {
        return delta;
    }

    public static void destroyDisplay() {
        Display.destroy();
    }

    private static long currentTime() {
        return Sys.getTime() * 1000 / Sys.getTimerResolution();
    }
}
