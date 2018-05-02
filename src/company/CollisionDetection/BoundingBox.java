package company.CollisionDetection;

import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;

public class BoundingBox {

    private float x0, y0, boxWidth, boxHeight;

    public BoundingBox(Vector2f position, Vector2f size){
        this.x0 = position.x - (size.x/2);
        this.y0 = position.y - (size.y/2);
        this.boxWidth = size.x;
        this.boxHeight = size.y;
    }

    private boolean Intersects(BoundingBox box){
        int tw = (int)this.boxWidth;
        int th = (int)this.boxHeight;
        int rw = (int)box.getBoxWidth();
        int rh = (int)box.getBoxHeight();
        if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
            return false;
        }
        int tx = (int)this.x0;
        int ty = (int)this.y0;
        int rx = box.getX0();
        int ry = box.getY0();
        rw += rx;
        rh += ry;
        tw += tx;
        th += ty;
        return ((rw < rx || rw > tx) &&
                (rh < ry || rh > ty) &&
                (tw < tx || tw > rx) &&
                (th < ty || th > ry));
    }

    public static boolean collisionDetected(BoundingBox box, ArrayList<BoundingBox>boundingBoxes){
        for (BoundingBox boundingBox : boundingBoxes) {
            if (boundingBox != box) {
                if (box.Intersects(boundingBox)) {
                    return true;
                }
            }
        }
        return false;
    }

    private float getBoxWidth() {
        return boxWidth;
    }

    private float getBoxHeight() {
        return boxHeight;
    }

    public int getX0() {
        return (int)x0;
    }

    public int getY0() {
        return (int)y0;
    }

    public void setPosition(Vector2f position) {
        this.x0 = position.x;
        this.y0 = position.y;
    }
}
