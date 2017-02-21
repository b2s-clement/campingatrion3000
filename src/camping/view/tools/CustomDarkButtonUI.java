package camping.view.tools;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;

public class CustomDarkButtonUI extends BasicButtonUI implements java.io.Serializable,
MouseListener{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private final static CustomDarkButtonUI m_buttonUI = new CustomDarkButtonUI();

	private Border m_borderRaised = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(CampingColor.LIGHTGREEN,2), new EmptyBorder(10,10,10,10));
	private Border m_borderLowered = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(CampingColor.GREEN,2), new EmptyBorder(10,10,10,10));
	private Color m_backgroundNormal = CampingColor.GREEN;
	private Color m_backgroundPressed = CampingColor.LIGHTGREEN;
	private Color m_backgroundHover = CampingColor.MIDDLEGREEN;
	private Color m_foregroundNormal = CampingColor.LIGHTGREY;
	private Color m_foregroundActive = CampingColor.GREEN;


  public static ComponentUI createUI(JComponent c){
	  return m_buttonUI;
  }

  public void installUI(JComponent c){
    super.installUI(c);
    c.addMouseListener(this);
    c.setBorder(m_borderRaised);
    c.setBackground(m_backgroundNormal);
  }

  public void uninstallUI(JComponent c){
    super.uninstallUI(c);
    c.removeMouseListener(this);
  }

  public void paint(Graphics g, JComponent c){
	  super.paint(g, c);
  }

  public Dimension getPreferredSize(JComponent c){
	  return super.getPreferredSize(c);
  }

  public void mouseClicked(MouseEvent e){
  }

  public void mousePressed(MouseEvent e){
    CustomDarkButton c = (CustomDarkButton) e.getComponent();
    if(c.isEnabled()){
	    c.setBorder(m_borderLowered);
	    c.setBackground(m_backgroundPressed);
    }
  }

  public void mouseReleased(MouseEvent e){
    CustomDarkButton c = (CustomDarkButton) e.getComponent();
    if(c.isEnabled()){
    	c.setBorder(m_borderRaised);
        c.setBackground(m_backgroundNormal);
    }
  }

  public void mouseEntered(MouseEvent e){
    CustomDarkButton c = (CustomDarkButton) e.getComponent();
    if(c.isEnabled()){
    	c.setForeground(m_foregroundActive);
    	c.setBackground(m_backgroundHover);
    	c.repaint();
    }
  }

  public void mouseExited(MouseEvent e){
    CustomDarkButton c = (CustomDarkButton) e.getComponent();
    if(c.isEnabled()){
    	c.setForeground(m_foregroundNormal);
        c.setBackground(m_backgroundNormal);
        c.repaint();
    }
  }
}