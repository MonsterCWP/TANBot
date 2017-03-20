import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

import javax.swing.JPanel;

public class TrianglePanel extends JPanel {
    private static final int SIZE = 600;
    private static final int BORDER = 20;
    double ax, ay, bx, by, cx, cy;
    double centerX, centerY;
    Path2D myPath;

    static void drawString(Graphics2D g2, String s, double x, double y) {
        FontMetrics fm = g2.getFontMetrics();

        g2.drawString(s, (float) x - fm.stringWidth(s) / 2, (float) y - fm.getHeight() / 2 + fm.getAscent());
    }

    static void drawEdgeString(Graphics2D g2, String s, double x,double dx,  double y, double dy) {
        
        FontMetrics fm = g2.getFontMetrics();
        double scale = fm.stringWidth(s)*2.5/Math.sqrt(dx*dx+dy*dy);
        drawString(g2, s, (float) x -dx*scale - fm.stringWidth(s) / 2, (float) y -dy*scale - fm.getHeight() / 2 + fm.getAscent());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.draw(myPath);

        FontMetrics fm = g2.getFontMetrics();

        drawString(g2, "A", (5 * ax + centerX) / 6, (5 * ay + centerY) / 6);
        drawString(g2, "B", (5 * bx + centerX) / 6, (5 * by + centerY) / 6);
        drawString(g2, "C", (5 * cx + centerX) / 6, (5 * cy + centerY) / 6);
        
        
        drawEdgeString(g2, "a", (bx+cx)/2,(ax-centerX), (by+cy)/2, (ay-centerY));
        drawEdgeString(g2, "b", (ax+cx)/2, (bx-centerX), (ay+cy)/2, (by-centerY));
        
        drawEdgeString(g2, "c", (bx+ax)/2, (cx-centerX), (by+ay)/2, (cy-centerY));
        
        
    }

    @Override
    public Dimension getPreferredSize() {
        if (isPreferredSizeSet()) {
            return super.getPreferredSize();
        }
        double height = ay + 2*BORDER;
        
        return new Dimension((int)(Double.max(bx, cx) + 2*BORDER), (int)height );
    }

    double max(double... values) {
        double result = Double.MIN_VALUE;
        for (double d : values)
            if (result < d)
                result = d;
        return result;
    }

    public TrianglePanel(double ax, double ay, double bx, double by, double cx, double cy) {
        super();
        setBackground(Color.WHITE);
        double scale = SIZE / max(ax, ay, bx, by, cx, cy);

        this.ax = ax * scale + BORDER;
        this.ay = cy * scale + BORDER;
        this.bx = bx * scale + BORDER;
        this.by = cy * scale + BORDER;
        this.cx = cx * scale + BORDER;
        this.cy =  BORDER;
        this.centerX = (this.ax+this.bx+this.cx)/3;
        this.centerY = (this.ay+this.by+this.cy)/3;
        
        
        myPath = new Path2D.Double();
        myPath.moveTo(this.ax, this.ay);
        myPath.lineTo(this.bx, this.by);
        myPath.lineTo(this.cx, this.cy);
        myPath.closePath();
    }

}
