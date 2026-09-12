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

// The title screen shown when the app starts: a map placeholder plus two
// buttons, one into the simulation and one into the instructions.
public class Home extends Frame {

    int width = 1280;
    int height = 720;

    int buttonWidth = 200;
    int buttonHeight = 50;
    int buttonGap = 16;
    int buttonMargin = 40;

    Color background = new Color(238, 238, 238);
    Color mapText = new Color(90, 90, 90);
    Color buttonFill = new Color(120, 120, 120);

    public Home() {
        setTitle("Transit Sim - Home");
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
                if (isOverButton(e.getX(), e.getY(), startY())) {
                    dispose();
                    new Panel();
                } else if (isOverButton(e.getX(), e.getY(), instructionY())) {
                    dispose();
                    new Instruction();
                }
            }
        });
    }

    private int buttonX() {
        return width - buttonMargin - buttonWidth;
    }

    private int startY() {
        return height - buttonMargin - buttonHeight * 2 - buttonGap;
    }

    private int instructionY() {
        return height - buttonMargin - buttonHeight;
    }

    private boolean isOverButton(int mouseX, int mouseY, int buttonY) {
        int x = buttonX();
        return mouseX >= x && mouseX <= x + buttonWidth
                && mouseY >= buttonY && mouseY <= buttonY + buttonHeight;
    }

    public void paint(Graphics g) {
        g.setColor(background);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.black);
        g.drawRect(0, 0, width - 1, height - 1);

        g.setColor(mapText);
        g.setFont(new Font("SansSerif", Font.PLAIN, 90));
        g.drawString("MAP", width / 2 - 150, height / 2);

        drawButton(g, startY(), "START");
        drawButton(g, instructionY(), "INSTRUCTION");
    }

    private void drawButton(Graphics g, int y, String label) {
        int x = buttonX();
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
        new Home();
    }
}
