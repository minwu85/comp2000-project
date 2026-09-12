import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;

// Draws a yellow/red diagonal caution-stripe border around the outside of a
// box. Used to flag a delayed train, both on its side-panel card and on its
// map icon.
public class HazardStripe {

    public static void draw(Graphics g, int x, int y, int width, int height, int thickness) {
        Graphics2D g2d = (Graphics2D) g.create();

        Rectangle outer = new Rectangle(x - thickness, y - thickness,
                width + thickness * 2, height + thickness * 2);
        Rectangle inner = new Rectangle(x, y, width, height);

        Area frame = new Area(outer);
        frame.subtract(new Area(inner));
        g2d.clip(frame);

        int stripeWidth = 10;
        int slant = outer.height;
        boolean yellow = true;
        for (int sx = outer.x - slant; sx < outer.x + outer.width + slant; sx = sx + stripeWidth) {
            if (yellow) {
                g2d.setColor(Color.yellow);
            } else {
                g2d.setColor(Color.red);
            }
            int[] xs = {sx, sx + stripeWidth, sx + stripeWidth - slant, sx - slant};
            int[] ys = {outer.y, outer.y, outer.y + outer.height, outer.y + outer.height};
            g2d.fillPolygon(xs, ys, 4);
            yellow = !yellow;
        }

        g2d.dispose();
    }
}
