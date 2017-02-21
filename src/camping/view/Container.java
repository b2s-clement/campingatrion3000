package camping.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import camping.view.tools.CampingColor;
import camping.view.tools.CustomTabbedPaneUI;

public class Container extends JFrame{

	/**
	 *
	 */
	private static final long serialVersionUID = -66277993592152486L;

	private JTabbedPane onglets;
	private Home home;
	private Editeur editeur;
	private Reservations reserv;
	private Clients clients;

	public Container(){
		this.setSize(1200,700);
		this.setMinimumSize(new Dimension(1200,700));
		this.setLayout(new BorderLayout());
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		this.setTitle("Campingatron3000 - Accueil");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.createContainer();
	}

	public void createContainer(){
		//ICONE DE LA FRAME
		ImageIcon frameIcon = new ImageIcon("img/frameIcon.png");
		this.setIconImage(frameIcon.getImage());

		JPanel subContainer = new JPanel();
		subContainer.setLayout(new BorderLayout());
		onglets = new JTabbedPane(SwingConstants.LEFT);
		home = new Home(this,CampingColor.LIGHTGREY,"Camping des Landes");
		editeur = new Editeur(CampingColor.LIGHTGREY);
		reserv = new Reservations(CampingColor.LIGHTGREY);
		clients = new Clients(CampingColor.LIGHTGREY);

		onglets.setUI(new CustomTabbedPaneUI());

		//logo logiciel
		ImageIcon logoIcon = new ImageIcon("img/home-logo.png");
		onglets.addTab("", logoIcon, null);
		onglets.setEnabledAt(0, false);

		//Onglet Accueil
		ImageIcon icon1 = new ImageIcon("img/home.png");
		onglets.addTab("  Accueil              ", icon1, home, "Accueil");

		//Onglet Editeur
		ImageIcon icon2 = new ImageIcon("img/edit.png");
		onglets.addTab("  Emplacements", icon2, editeur, "Editeur d'emplacements");

		//Onglet Reservations
		ImageIcon icon3 = new ImageIcon("img/loc.png");
		onglets.addTab("  Réservations   ", icon3, reserv, "Gestionnaire des réservations");

		//Onglet Clients
		ImageIcon icon4 = new ImageIcon("img/clients.png");
		onglets.addTab("  Clients               ", icon4, clients, "Gestionnaire des clients");




		//Listener de changement d'onglet
		onglets.addChangeListener(new tabbedPaneListener());
		onglets.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				for (int i = 1; i < onglets.getTabCount(); i++){
					if (onglets.getBoundsAt(i).contains(e.getPoint())) {
						ImageIcon icon;
						switch(i){
						case 1:
							icon = new ImageIcon("img/home-selected.png");
							break;
						case 2:
							icon = new ImageIcon("img/edit-selected.png");
							break;
						case 3:
							icon = new ImageIcon("img/loc-selected.png");
							break;
						case 4:
							icon = new ImageIcon("img/clients-selected.png");
							break;
						default:
							icon = new ImageIcon("img/home-selected.png");
							break;
						}
						onglets.setIconAt(i,icon);
					}else if(onglets.getSelectedIndex()!=i){
						ImageIcon icon;
						switch(i){
						case 1:
							icon = new ImageIcon("img/home.png");
							break;
						case 2:
							icon = new ImageIcon("img/edit.png");
							break;
						case 3:
							icon = new ImageIcon("img/loc.png");
							break;
						case 4:
							icon = new ImageIcon("img/clients.png");
							break;
						default:
							icon = new ImageIcon("img/home.png");
							break;
						}
						onglets.setIconAt(i,icon);

					}
				}
			}
		});

		onglets.setBackground(CampingColor.GREEN);
		onglets.setForeground(CampingColor.LIGHTGREY);
		onglets.setOpaque(true);
		onglets.setSelectedIndex(1);
		subContainer.add(onglets, BorderLayout.CENTER);

		//Creation du bandeau du bas
		JPanel bandeau = new JPanel();
		bandeau.setLayout(new BoxLayout(bandeau,BoxLayout.X_AXIS));

		JLabel aide = new JLabel("Besoin d'aide ? Contacter l'équipe de développement : support.campingatron3000@barnab2studio.fr");
		aide.setBackground(CampingColor.GREEN);
		aide.setOpaque(true);
		aide.setForeground(CampingColor.LIGHTGREY);
		aide.setBorder(new EmptyBorder(5,5,5,5));


		bandeau.add(aide);
		bandeau.setBackground(CampingColor.GREEN);

		subContainer.add(bandeau, BorderLayout.SOUTH);



		this.add(subContainer, BorderLayout.CENTER);
	}

	public class tabbedPaneListener implements ChangeListener{

		public void stateChanged(ChangeEvent e) {

			JTabbedPane pane = (JTabbedPane) e.getSource();
			String[] strComp = pane.getSelectedComponent().toString().split("\\.");
			char title = strComp[2].charAt(0);
			String res;
			//mise en valeur de l'onglet actif

			//active icons
			ImageIcon activeHome = new ImageIcon("img/home-selected.png");
			ImageIcon activeEdit = new ImageIcon("img/edit-selected.png");
			ImageIcon activeReserv = new ImageIcon("img/loc-selected.png");
			ImageIcon activeClient = new ImageIcon("img/clients-selected.png");

			//regular icons
			ImageIcon regularHome = new ImageIcon("img/home.png");
			ImageIcon regularEdit = new ImageIcon("img/edit.png");
			ImageIcon regularReserv = new ImageIcon("img/loc.png");
			ImageIcon regularClient = new ImageIcon("img/clients.png");

			switch(title){
			case 'H':
				res = "Accueil";
				onglets.setIconAt(1, activeHome);
				onglets.setIconAt(2, regularEdit);
				onglets.setIconAt(3, regularReserv);
				onglets.setIconAt(4, regularClient);
				onglets.setForegroundAt(1,CampingColor.GREEN);
				onglets.setForegroundAt(2,CampingColor.LIGHTGREY);
				onglets.setForegroundAt(3,CampingColor.LIGHTGREY);
				onglets.setForegroundAt(4,CampingColor.LIGHTGREY);
				break;
			case 'E':
				res = "Editeur d'emplacements";
				onglets.setIconAt(1, regularHome);
				onglets.setIconAt(2, activeEdit);
				onglets.setIconAt(3, regularReserv);
				onglets.setIconAt(4, regularClient);
				onglets.setForegroundAt(1,CampingColor.LIGHTGREY);
				onglets.setForegroundAt(2,CampingColor.GREEN);
				onglets.setForegroundAt(3,CampingColor.LIGHTGREY);
				onglets.setForegroundAt(4,CampingColor.LIGHTGREY);
				break;
			case 'R':
				res = "Gestionnaire des réservations";
				onglets.setIconAt(1, regularHome);
				onglets.setIconAt(2, regularEdit);
				onglets.setIconAt(3, activeReserv);
				onglets.setIconAt(4, regularClient);
				onglets.setForegroundAt(1,CampingColor.LIGHTGREY);
				onglets.setForegroundAt(2,CampingColor.LIGHTGREY);
				onglets.setForegroundAt(3,CampingColor.GREEN);
				onglets.setForegroundAt(4,CampingColor.LIGHTGREY);
				break;
			case 'C':
				res = "Gestionnaire des clients";
				onglets.setIconAt(1, regularHome);
				onglets.setIconAt(2, regularEdit);
				onglets.setIconAt(3, regularReserv);
				onglets.setIconAt(4, activeClient);
				onglets.setForegroundAt(1,CampingColor.LIGHTGREY);
				onglets.setForegroundAt(2,CampingColor.LIGHTGREY);
				onglets.setForegroundAt(3,CampingColor.LIGHTGREY);
				onglets.setForegroundAt(4,CampingColor.GREEN);
				break;
			default:
				res = "Unexpected Error";
				break;

			}
            Container.this.setTitle("Campingatron3000 - "+res);
		}
	}
}
