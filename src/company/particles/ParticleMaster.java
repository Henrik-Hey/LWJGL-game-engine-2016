package company.particles;

import company.entities.Camera;
import company.renderer.Loader;
import org.lwjgl.util.vector.Matrix4f;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParticleMaster {

    private static List<Particle>particles = new ArrayList<>();
    private static ParticleRenderer renderer;

    public static void init(Loader loader, Matrix4f projectionMatrix){
        renderer = new ParticleRenderer(loader, projectionMatrix);
    }

    public static void update(){
        Iterator<Particle> iterator = particles.iterator();
        while(iterator.hasNext()){
            Particle p = iterator.next();
            boolean isAlive =  p.update();
            if(!isAlive){
                iterator.remove();
            }
        }
    }

    public static void render(Camera camera){
        renderer.render(particles, camera);
    }

    public static void cleanUp(){
        renderer.cleanUp();
    }

    public static void addParticle(Particle particle){
        particles.add(particle);
    }
}
