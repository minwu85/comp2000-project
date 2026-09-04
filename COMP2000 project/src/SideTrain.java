import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

// The narrow left panel: a short scrolling list of train cards with a
// hover scrollbar. When another view (the timetable) is open it draws
// itself dark grey instead, via draw(..., dimmed = true).
public class SideTrain {

    private final int width;   // panel width in pixels
    private final int top;     // y where the panel starts (below the top bar)

    int cardSpacing = 124;     // vertical gap from one card to the next
    int maxVisibleCards = 3;   // how many cards fit before you must scroll

    int scrollbarWidth = 8;
    int scrollbarWidthHot = 12;
    int hoverMargin = 28;      // how far from the bar still counts as "near"

    int scrollY = 0;           // pixels scrolled down
    int contentHeight = 0;     // total height of all cards (set while drawing)
    int viewHeight = 0;        // visible height of the list (set while drawing)

    boolean pointerNearBar = false;
    boolean thumbHover = false;
    boolean thumbDragging = false;

    Color panelBody = new Color(120, 120, 120);
    Color dimBody = new Color(70, 70, 70);
    Color cardBackground = new Color(225, 225, 225);
    Color scrollTrack = new Color(80, 80, 80);
    Color thumbNormal = new Color(150, 150, 150);
    Color thumbHoverColour = new Color(195, 195, 195);
    Color thumbDragColour = new Color(230, 230, 230);

    public SideTrain(int width, int top) {
        this.width = width;
        this.top = top;
    }

    public void draw(Graphics g, int panelHeight, Vehicles[] trains, boolean dimmed) {
        int fullHeight = panelHeight - top;

        if (dimmed) {
            // Timetable is open: show this panel as a dark grey stub.
            g.setColor(dimBody);
            g.fillRect(0, top, width, fullHeight);
            g.setColor(new Color(155, 155, 155));
            g.setFont(new Font("SansSerif", Font.BOLD, 13));
            int y = top + 34;
            for (Vehicles train : trains) {
                g.drawString(train.getName(), 16, y);
                y += 22;
            }
            return;
        }

        contentHeight = trains.length * cardSpacing + 12;
        viewHeight = Math.min(fullHeight, maxVisibleCards * cardSpacing + 12);
        scrollY = clampScroll(scrollY);

        g.setColor(panelBody);
        g.fillRect(0, top, width, fullHeight);

        // Clip so scrolled cards never paint over the top bar or past the list.
        Graphics clipped = g.create();
        clipped.clipRect(0, top, width, viewHeight);
        int cardY = top + 12 - scrollY;
        for (Vehicles train : trains) {
            drawTrainCard(clipped, cardY, train);
            cardY += cardSpacing;
        }
        clipped.dispose();

        if (viewHeight < fullHeight) {
            g.setColor(scrollTrack);
            g.fillRect(0, top + viewHeight, width, 2);
        }

        drawScrollbar(g);
    }

    // --- scrolling ----------------------------------------------------------

    private int maxScroll() {
        return Math.max(0, contentHeight - viewHeight);
    }

    private int clampScroll(int value) {
        if (value < 0) return 0;
        if (value > maxScroll()) return maxScroll();
        return value;
    }

    private int thumbHeight() {
        if (contentHeight <= 0) return 30;
        int h = (int) ((long) viewHeight * viewHeight / contentHeight);
        return Math.max(30, Math.min(h, viewHeight));
    }

    private int thumbY() {
        if (maxScroll() == 0) return top;
        return top + (int) ((long) (viewHeight - thumbHeight()) * scrollY / maxScroll());
    }

    public void scrollBy(int amount) {
        scrollY = clampScroll(scrollY + amount);
    }

    // Returns true if something visible changed (so the caller repaints).
    public boolean setPointer(int x, int y) {
        boolean near = x >= 0 && x <= width + hoverMargin
                && y >= top && y <= top + viewHeight;
        int tX = width - currentWidth();
        int tY = thumbY();
        boolean overThumb = near && x >= tX - hoverMargin && y >= tY && y <= tY + thumbHeight();

        boolean changed = (near != pointerNearBar) || (overThumb != thumbHover);
        pointerNearBar = near;
        thumbHover = overThumb;
        return changed;
    }

    public void setThumbDragging(boolean dragging) {
        thumbDragging = dragging;
    }

    public boolean isOverScrollbar(int x, int y) {
        if (maxScroll() == 0) return false;
        return x >= width - scrollbarWidthHot - 4 && x <= width + 4
                && y >= top && y <= top + viewHeight;
    }

    public void dragScrollTo(int mouseY) {
        int travel = viewHeight - thumbHeight();
        if (travel <= 0) {
            scrollY = 0;
            return;
        }
        int rel = mouseY - top - thumbHeight() / 2;
        scrollY = clampScroll((int) ((long) rel * maxScroll() / travel));
    }

    private boolean scrollbarShowing() {
        return maxScroll() > 0 && (pointerNearBar || thumbHover || thumbDragging);
    }

    private int currentWidth() {
        if (thumbHover || thumbDragging) {
            return scrollbarWidthHot;
        }
        return scrollbarWidth;
    }

    private void drawScrollbar(Graphics g) {
        if (!scrollbarShowing()) return;
        int barW = currentWidth();
        int trackX = width - barW;

        g.setColor(scrollTrack);
        g.fillRect(trackX, top, barW, viewHeight);

        int thumbH = thumbHeight();
        int ty = thumbY();
        if (thumbDragging) {
            g.setColor(thumbDragColour);
        } else if (thumbHover) {
            g.setColor(thumbHoverColour);
        } else {
            g.setColor(thumbNormal);
        }
        g.fillRect(trackX, ty, barW, thumbH);
        g.setColor(Color.black);
        g.drawRect(trackX, ty, barW - 1, thumbH - 1);
    }

    // --- one train card ---------------------------------------------------

    private void drawTrainCard(Graphics g, int cardY, Vehicles train) {
        int cardX = 10;
        int cardWidth = width - 20;
        int cardHeight = 112;

        g.setColor(cardBackground);
        g.fillRect(cardX, cardY, cardWidth, cardHeight);
        g.setColor(Color.black);
        g.drawRect(cardX, cardY, cardWidth, cardHeight);

        drawTrainIcon(g, cardX + 10, cardY + 10, 46, train.getName(), lineColour(train.getName()));

        int textX = cardX + 10 + 46 + 10;
        int textY = cardY + 20;

        g.setColor(Color.black);
        g.setFont(new Font("SansSerif", Font.BOLD, 13));
        g.drawString(train.getName() + " (" + train.route.name + ")", textX, textY);

        g.setFont(new Font("SansSerif", Font.PLAIN, 12));
        g.drawString("- On train: " + train.getPassengers().size(), textX, textY + 20);
        g.drawString("- Current: " + train.getCurStop().getName(), textX, textY + 38);

        Stops next = train.getNextStop();
        String nextName;
        if (next == null) {
            nextName = "-";
        } else {
            nextName = next.getName();
        }
        g.drawString("- Next: " + nextName, textX, textY + 56);
    }

    private void drawTrainIcon(Graphics g, int x, int y, int size, String label, Color colour) {
        g.setColor(colour);
        g.fillRect(x, y, size, size);
        g.setColor(Color.black);
        g.drawRect(x, y, size, size);
        g.setColor(Color.white);
        g.setFont(new Font("SansSerif", Font.BOLD, 13));
        g.drawString(label, x + 8, y + size / 2 + 5);
    }

    // Colour each train shares with its line on the map. Shared with SideTable.
    public static Color lineColour(String trainName) {
        if (trainName.equals("T1")) return new Color(255, 0, 0);   // Red Line
        if (trainName.equals("T2")) return new Color(0, 0, 255);   // Blue Line
        if (trainName.equals("T3")) return new Color(191, 0, 255); // Purple Line
        if (trainName.equals("T4")) return new Color(0, 160, 0);   // Green Line
        return Color.gray;
    }
}
