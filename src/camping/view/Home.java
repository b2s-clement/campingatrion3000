package camping.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import camping.controler.home.*;
import camping.controler.reservations.AddResaListener;
import camping.view.database.ReservPanel;
import camping.view.tools.CampingColor;
import camping.view.tools.CustomButton;
import camping.view.tools.CustomHomeTitleButton;

public class Home extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = -8471660133083373596L;

	//parent
	public Container container;

	//Elements
	private ImageIcon iconRename = new ImageIcon("img/rename.png");
	private ImageIcon iconDelete = new ImageIcon("img/delete.png");

	private static JLabel title;
	private CustomHomeTitleButton edit;
	private CustomHomeTitleButton supp;

	private CustomButton resa;
	private CustomButton arrivee;
	private CustomButton depart;

	private static CustomButton save;
	private CustomButton close;

	private static boolean saved = false;

	public Home(Container parent, Color c,String s){
		this.container=parent;
		this.setBackground(c);
		this.createHome(s);
	}

	public void createHome(String s){
		this.setLayout(new BorderLayout());

		//Nom du camping
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BorderLayout());

		title = new JLabel(s);
		title.setBorder(new EmptyBorder(10,20,10,20));
		title.setForeground(CampingColor.GREEN);
		title.setFont(new Font("Montserrat",Font.PLAIN,30));

		edit = new CustomHomeTitleButton(iconRename);
		edit.setToolTipText("Renommer");
		edit.addActionListener(new EditTitleListener());

		supp = new CustomHomeTitleButton(iconDelete);
		supp.setToolTipText("Supprimer");
		supp.addActionListener(new SuppTitleListener());

		JPanel buttons = new JPanel();
		buttons.add(edit);
		buttons.add(supp);
		buttons.setBorder(new EmptyBorder(5,20,5,20));
		buttons.setOpaque(false);

		titlePanel.add(title,BorderLayout.WEST);
		titlePanel.add(buttons,BorderLayout.EAST);
		titlePanel.setBackground(CampingColor.LIGHTGREEN);
		titlePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, CampingColor.GREEN ));

		this.add(titlePanel,BorderLayout.NORTH);

		//Coeur de la page : liste des arrivï¿½es et dï¿½parts + menu

		JPanel core = new JPanel();
		core.setLayout(new BorderLayout());

				//menu de droite

		JPanel menu = new JPanel();
		menu.setLayout(new BorderLayout());
		menu.setPreferredSize(new Dimension(250,600));

		JPanel topButtons = new JPanel();
		topButtons.setPreferredSize(new Dimension(250,300));

		resa = new CustomButton("Nouvelle réservation...");
		resa.setPreferredSize(new Dimension(200, 40));
		resa.addActionListener(new AddResaListener());
		arrivee = new CustomButton("Arrivée client...");
		arrivee.setPreferredSize(new Dimension(200, 40));
		depart = new CustomButton("Départ Client...");
		depart.setPreferredSize(new Dimension(200, 40));
		topButtons.add(resa);
		topButtons.add(arrivee);
		topButtons.add(depart);
		topButtons.setOpaque(false);
		topButtons.setBorder(new EmptyBorder(5,5,5,5));

		JPanel bottomButtons = new JPanel();

		close = new CustomButton("Fermer");
		close.setPreferredSize(new Dimension(200, 40));
		close.addActionListener(new CloseListener());
		bottomButtons.add(close);
		bottomButtons.setPreferredSize(new Dimension(250,60));
		bottomButtons.setOpaque(false);

		menu.add(topButtons,BorderLayout.NORTH);
		menu.add(bottomButtons,BorderLayout.SOUTH);
		menu.setBackground(CampingColor.GREEN);
		menu.setOpaque(true);
		menu.setBorder(new EmptyBorder(25, 0, 0, 0));

				//Liste des informations

		JPanel infos = new JPanel();
		infos.setBorder(BorderFactory.createMatteBorder(0, 5, 5, 0, CampingColor.GREEN ));

		infos.setLayout(new BoxLayout(infos,BoxLayout.Y_AXIS));

		JPanel arrivees = new JPanel();
		arrivees.setLayout(new BorderLayout());

		JLabel arrTitle = new JLabel("Arrivées aujourd'hui :");
		arrTitle.setBackground(CampingColor.GREEN);
		arrTitle.setForeground(CampingColor.LIGHTGREY);
		arrTitle.setBorder(new EmptyBorder(5,5,5,5));
		arrTitle.setOpaque(true);

		ReservPanel arrContent = new ReservPanel(ReservPanel.ARRIVEES);
		arrContent.setOpaque(true);

		arrivees.add(arrTitle,BorderLayout.NORTH);
		arrivees.add(arrContent,BorderLayout.CENTER);

		JPanel departs = new JPanel();
		departs.setLayout(new BorderLayout());

		JLabel depTitle = new JLabel("Départs aujourd'hui :");
		depTitle.setBackground(CampingColor.GREEN);
		depTitle.setForeground(CampingColor.LIGHTGREY);
		depTitle.setBorder(new EmptyBorder(5,5,5,5));
		depTitle.setOpaque(true);

		ReservPanel depContent = new ReservPanel(ReservPanel.DEPARTS);
		depContent.setBackground(CampingColor.LIGHTGREEN);
		depContent.setOpaque(true);

		departs.add(depTitle,BorderLayout.NORTH);
		departs.add(depContent,BorderLayout.CENTER);

		infos.add(arrivees);
		infos.add(departs);

				//Implï¿½mentation dans la fenetre

		core.setOpaque(false);
		core.setBorder(new EmptyBorder(20, 20, 20, 20));
		core.add(menu,BorderLayout.EAST);
		core.add(infos,BorderLayout.CENTER);


		this.add(core,BorderLayout.CENTER);
	}

	public static void disableSaveButton(){
		save.setEnabled(false);
		saved=true;
	}
	public static void enableSaveButton(){
		save.setEnabled(true);
		saved=false;
	}
	public static String getTitle(){
		return title.getText();
	}
	public static void setTitle(String s){
		title.setText(s);
	}
	public static boolean isSaved(){
		return saved;
	}
}
