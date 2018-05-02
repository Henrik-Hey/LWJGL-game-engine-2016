package company.AI;

import company.CollisionDetection.BoundingBox;
import company.models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

public class EnemyChaser extends FollowerAI{

    public EnemyChaser(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale,
                       BoundingBox boundingBox, int health, String type, float hearingRange) {
        super(model, position, rotX, rotY, rotZ, scale, boundingBox, health, type, hearingRange);
    }

}
