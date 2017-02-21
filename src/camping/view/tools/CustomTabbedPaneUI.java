package camping.view.tools;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;

public class CustomTabbedPaneUI extends BasicTabbedPaneUI {

	private Color colorBG = CampingColor.LIGHTGREY;
	private Color colorMenu = CampingColor.GREEN;

    private int inclTab = 10;
    private Polygon shape;


    public static ComponentUI createUI(JComponent c) {
        return new CustomTabbedPaneUI();
    }

    @Override
    protected void installDefaults() {
        super.installDefaults();
        contentBorderInsets=new Insets(0,0,0,0);
    }


    @Override
    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        Graphics2D g2D = (Graphics2D) g;
        GradientPaint gradientShadow;
        int[] xp = new int[]{x, x, x + w, x + w, x};
        int[] yp = new int[]{y, y + h - 3, y + h - 3, y, y};
        gradientShadow = new GradientPaint(x, y, colorBG, x, y + h, colorBG);
        shape = new Polygon(xp, yp, xp.length);
        if (isSelected) {
            g2D.setPaint(gradientShadow);
        }else{
            GradientPaint gradientShadowTmp = new GradientPaint(0, 0, colorMenu, 0, y + h / 2, colorMenu);
            g2D.setPaint(gradientShadowTmp);
        }
        g2D.fill(shape);
    }

    @Override
    protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
        return 20 + inclTab + super.calculateTabWidth(tabPlacement, tabIndex, metrics);
    }

    @Override
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
    }

    @Override
    protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
        if (tabPane.hasFocus() && isSelected) {
            g.setColor(UIManager.getColor("ScrollBar.thumbShadow"));
            g.drawPolygon(shape);
        }
    }
}