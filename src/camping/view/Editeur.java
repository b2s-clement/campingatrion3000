package camping.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import camping.controler.editeur.UpdateValuesListener;
import camping.view.database.EmplacementPanel;
import camping.view.tools.CampingColor;
import camping.view.tools.CustomDarkButton;
import camping.view.tools.CustomTabbedPaneUI;

public class Editeur extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = -7578487123499611483L;
	private DrawableCamping carte;
	private EmplacementPanel ep;

	public Editeur(Color c){
		this.setBackground(c);
		this.setLayout(new BorderLayout());
		this.createEditeur();
	}
	public void createEditeur(){
		JPanel core = new JPanel();
		core.setLayout(new BorderLayout());



				//affichage du camping en carte ou liste

		JTabbedPane infos = new JTabbedPane();
		infos.setUI(new CustomTabbedPaneUI());

		infos.setForeground(CampingColor.LIGHTGREEN);
		infos.setBackground(CampingColor.GREEN);
		infos.setOpaque(true);

		//MODE CARTE
		carte=new DrawableCamping();

		//MODE LISTE

		JPanel liste  = new JPanel();
		liste.setLayout(new BorderLayout());
		liste.setOpaque(true);
		liste.setBackground(CampingColor.LIGHTGREEN);
		liste.setBorder(BorderFactory.createMatteBorder(5,5,5,5, CampingColor.GREEN));
		ep = new EmplacementPanel();
		liste.add(ep,BorderLayout.CENTER);

		JPanel menuliste = new JPanel();
		menuliste.setPreferredSize(new Dimension(100,600));
		menuliste.setBackground(CampingColor.GREEN);
		menuliste.setOpaque(true);
		menuliste.setBorder(new EmptyBorder(25, 0, 0, 0));

		ImageIcon refresh = new ImageIcon("img/editeur/refresh.png");
		ImageIcon refreshSel = new ImageIcon("img/editeur/refresh-pushed.png");

		CustomDarkButton refreshTool = new CustomDarkButton(refresh);
		refreshTool.setPressedIcon(refreshSel);
		refreshTool.setPreferredSize(new Dimension(50,50));
		refreshTool.setToolTipText("Rafraîchir les données");
		refreshTool.addActionListener(new UpdateValuesListener(ep));

		menuliste.add(refreshTool);
		liste.add(menuliste,BorderLayout.EAST);

		infos.addTab("Carte du camping",carte);
		infos.addTab("Liste des emplacements", liste);
		infos.setForegroundAt(0, CampingColor.GREEN);
		infos.addChangeListener(new MapTabListener());

				//Implémentation dans la fenetre

		core.setOpaque(false);
		core.setBorder(new EmptyBorder(20, 20, 20, 20));
		core.add(infos,BorderLayout.CENTER);

		this.add(core,BorderLayout.CENTER);
	}

	public class MapTabListener implements ChangeListener{
		public void stateChanged(ChangeEvent e) {
			JTabbedPane pane = (JTabbedPane) e.getSource();
			int ind = pane.getSelectedIndex();
			switch(ind){
			case 0:
				pane.setBackgroundAt(0, CampingColor.MIDDLEGREEN);
				pane.setForegroundAt(0, CampingColor.GREEN);
				pane.setBackgroundAt(1, CampingColor.GREEN);
				pane.setForegroundAt(1, CampingColor.MIDDLEGREEN);
				break;
			case 1:
				pane.setBackgroundAt(1, CampingColor.MIDDLEGREEN);
				pane.setForegroundAt(1, CampingColor.GREEN);
				pane.setBackgroundAt(0, CampingColor.GREEN);
				pane.setForegroundAt(0, CampingColor.MIDDLEGREEN);
				break;
			default:
				break;
			}
		}
	}


}