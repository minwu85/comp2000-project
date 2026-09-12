import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// The instructions screen: explains the goal of the simulation and the
// controls in the panel, with Back (to Home) and Next (into the simulation).
public class Instruction extends Frame {

    int width = 1280;
    int height = 720;

    int buttonWidth = 160;
    int buttonHeight = 50;
    int buttonMargin = 40;

    Color background = new Color(238, 238, 238);
    Color buttonFill = new Color(120, 120, 120);

    String[] lines = {
        "Goal",
        "Four trains (T1-T4) each run their own coloured line through a shared",
        "network of stations, and passengers board and get off along the way.",
        "Press space, or the play button, to watch the network run.",
        "",
        "Buttons in the simulation",
        "Space / play button (top right): start, pause and resume the clock.",
        "House button (top right): come back to this home screen.",
        "Drag the map: pan the network. The side panel stays fixed on screen.",
        "Mouse wheel, or drag the scrollbar, over the left panel: scroll the train list.",
        "The two tabs on the right of the side panel: switch between the train",
        "list (\"Train current\") and the timetable (\"Train table\").",
        "",
        "Buttons on this screen",
        "Back: return to the home screen.",
        "Next: start the simulation."
    };

    public Instruction() {
        setTitle("Transit Sim - Instructions");
        setSize(width, height);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }

            public void windowOpened(WindowEvent we) {
                Insets insets = getInsets();
                setSize(width + insets.left + insets.right, height + insets.top + insets.bottom);
                repaint();
            }
        });

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (isOverButton(e.getX(), e.getY(), backX())) {
                    dispose();
                    new Home();
                } else if (isOverButton(e.getX(), e.getY(), nextX())) {
                    dispose();
                    new Panel();
                }
            }
        });
    }

    private int buttonY() {
        return height - buttonMargin - buttonHeight;
    }

    private int backX() {
        return buttonMargin;
    }

    private int nextX() {
        return width - buttonMargin - buttonWidth;
    }

    private boolean isOverButton(int mouseX, int mouseY, int buttonX) {
        int y = buttonY();
        return mouseX >= buttonX && mouseX <= buttonX + buttonWidth
                && mouseY >= y && mouseY <= y + buttonHeight;
    }

    public void paint(Graphics g) {
        g.setColor(background);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.black);
        g.drawRect(0, 0, width - 1, height - 1);

        g.setFont(new Font("SansSerif", Font.BOLD, 32));
        g.drawString("Instructions", 40, 60);

        int y = 110;
        for (String line : lines) {
            if (isHeading(line)) {
                g.setFont(new Font("SansSerif", Font.BOLD, 18));
            } else {
                g.setFont(new Font("SansSerif", Font.PLAIN, 15));
            }
            g.drawString(line, 40, y);
            y = y + 26;
        }

        drawButton(g, backX(), "BACK");
        drawButton(g, nextX(), "NEXT");
    }

    private boolean isHeading(String line) {
        if (line.equals("Goal")) {
            return true;
        }
        if (line.equals("Buttons in the simulation")) {
            return true;
        }
        if (line.equals("Buttons on this screen")) {
            return true;
        }
        return false;
    }

    private void drawButton(Graphics g, int x, String label) {
        int y = buttonY();
        g.setColor(buttonFill);
        g.fillRect(x, y, buttonWidth, buttonHeight);

        g.setColor(Color.white);
        g.setFont(new Font("SansSerif", Font.BOLD, 16));
        FontMetrics fm = g.getFontMetrics();
        int textX = x + (buttonWidth - fm.stringWidth(label)) / 2;
        int textY = y + (buttonHeight + fm.getAscent()) / 2 - 3;
        g.drawString(label, textX, textY);
    }

    public static void main(String[] args) {
        new Instruction();
    }
}
