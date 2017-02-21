package camping.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import camping.controler.reservations.AddResaListener;
import camping.view.database.ReservPanel;
import camping.view.tools.CampingColor;
import camping.view.tools.CustomButton;

public class Reservations extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = 4209739691598213733L;

	private CustomButton addReserv;
	private CustomButton detailsReserv;
	private CustomButton delReserv;
	private ReservPanel rp;

	//private JList<Client> listReserv;

	public Reservations(Color c){
		this.setBackground(c);
		this.setLayout(new BorderLayout());
		this.createReserv();
	}

	public void createReserv(){

		JPanel core = new JPanel();
		core.setLayout(new BorderLayout());

				//menu de droite

		JPanel menu = new JPanel();
		menu.setPreferredSize(new Dimension(250,600));

		addReserv = new CustomButton("Nouvelle réservation...");
		addReserv.setPreferredSize(new Dimension(200, 40));
		addReserv.addActionListener(new AddResaListener());
		detailsReserv = new CustomButton("Détails...");
		detailsReserv.setPreferredSize(new Dimension(200, 40));
		delReserv = new CustomButton("Supprimer");
		delReserv.setPreferredSize(new Dimension(200, 40));
		menu.add(addReserv);
		menu.add(detailsReserv);
		menu.add(delReserv);
		menu.setOpaque(false);
		menu.setBorder(new EmptyBorder(5,5,5,5));

		menu.setBackground(CampingColor.GREEN);
		menu.setOpaque(true);
		menu.setBorder(new EmptyBorder(25, 0, 0, 0));

				//Liste des informations

		JPanel infos = new JPanel();
		infos.setBorder(BorderFactory.createMatteBorder(0, 5, 5, 0, CampingColor.GREEN ));

		infos.setLayout(new BorderLayout());

		JPanel filtres = new JPanel();
		filtres.setLayout(new BoxLayout(filtres,BoxLayout.X_AXIS));
		filtres.setBackground(CampingColor.GREEN);
		filtres.setBorder(new EmptyBorder(5, 5, 5, 5));
		filtres.setOpaque(true);

		JLabel filTitle = new JLabel("Filtrer :");
		filTitle.setForeground(CampingColor.LIGHTGREY);

		JLabel nom = new JLabel("Nom :");
		nom.setForeground(CampingColor.LIGHTGREY);
		JTextField nomField = new JTextField();
		nomField.setMaximumSize(new Dimension(150,20));
		nomField.setPreferredSize(new Dimension(150,20));

		JRadioButton locButton = new JRadioButton();
		locButton.setOpaque(false);
		JLabel loc = new JLabel("Loc. en cours");
		loc.setForeground(CampingColor.LIGHTGREY);

		CustomButton submit = new CustomButton("Filtrer");
		submit.setPreferredSize(new Dimension(60,20));

		filtres.add(filTitle);
		filtres.add(Box.createHorizontalGlue());
		filtres.add(nom);
		filtres.add(nomField);
		filtres.add(Box.createRigidArea(new Dimension(10,0)));
		filtres.add(locButton);
		filtres.add(loc);
		filtres.add(Box.createRigidArea(new Dimension(10,0)));
		filtres.add(submit);

		infos.add(filtres,BorderLayout.NORTH);

		rp = new ReservPanel();
		infos.add(rp,BorderLayout.CENTER);


				//Implémentation dans la fenetre

		core.setOpaque(false);
		core.setBorder(new EmptyBorder(20, 20, 20, 20));
		core.add(menu,BorderLayout.EAST);
		core.add(infos,BorderLayout.CENTER);


		this.add(core,BorderLayout.CENTER);

	}

}