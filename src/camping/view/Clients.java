package camping.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import camping.controler.clients.AddClientListener;
import camping.controler.clients.DeleteClientListener;
import camping.controler.clients.DetailsClientListener;
import camping.controler.clients.FiltreClientListener;
import camping.view.database.ClientPanel;
import camping.view.tools.CampingColor;
import camping.view.tools.CustomButton;

public class Clients extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = 1531265966980672439L;

	private static JPanel menu;
	private CustomButton addClient;
	private static CustomButton detailsClient;
	private static CustomButton delClient;

	private static ClientPanel dataClient;

	private JTextField nomField;

	public Clients(Color c){
		this.setBackground(c);
		this.setLayout(new BorderLayout());
		this.createClients();
	}

	public void createClients(){

		JPanel core = new JPanel();
		core.setLayout(new BorderLayout());

				//menu de droite

		menu = new JPanel();
		menu.setPreferredSize(new Dimension(250,600));

		addClient = new CustomButton("Nouveau client...");
		addClient.setPreferredSize(new Dimension(200, 40));
		addClient.addActionListener(new AddClientListener());
		detailsClient = new CustomButton("Dï¿½tails...");
		detailsClient.setPreferredSize(new Dimension(200, 40));
		detailsClient.setEnabled(false);
		detailsClient.addActionListener(new DetailsClientListener());
		delClient = new CustomButton("Supprimer");
		delClient.setPreferredSize(new Dimension(200, 40));
		delClient.addActionListener(new DeleteClientListener());
		delClient.setEnabled(false);
		menu.add(addClient);
		menu.add(detailsClient);
		menu.add(delClient);
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

		JLabel nom = new JLabel("Nom :  ");
		nom.setForeground(CampingColor.LIGHTGREY);
		nomField = new JTextField();
		nomField.setMaximumSize(new Dimension(150,20));
		nomField.setPreferredSize(new Dimension(150,20));

		/*JCheckBox locButton = new JCheckBox();
		locButton.setOpaque(false);
		JLabel loc = new JLabel("Loc. en cours");
		loc.setForeground(CampingColor.LIGHTGREY);*/

		CustomButton submit = new CustomButton("Filtrer");
		submit.setPreferredSize(new Dimension(60,20));
		submit.addActionListener(new FiltreClientListener(this));

		filtres.add(filTitle);
		filtres.add(Box.createHorizontalGlue());
		filtres.add(nom);
		filtres.add(nomField);
		filtres.add(Box.createRigidArea(new Dimension(10,0)));
		//filtres.add(locButton);
		//filtres.add(loc);
		filtres.add(Box.createRigidArea(new Dimension(10,0)));
		filtres.add(submit);

		infos.add(filtres,BorderLayout.NORTH);

		//Implémentation du panel de clients
		dataClient = new ClientPanel();
		infos.add(dataClient,BorderLayout.CENTER);


				//Implï¿½mentation dans la fenetre

		core.setOpaque(false);
		core.setBorder(new EmptyBorder(20, 20, 20, 20));
		core.add(menu,BorderLayout.EAST);
		core.add(infos,BorderLayout.CENTER);
		//en attendant de trouver mieux ...
		this.addMouseMotionListener(new bricoleListener());
		this.add(core,BorderLayout.CENTER);

	}

	public static void correctifDelButton(){
		delClient.setEnabled(true);
	}

	public static void enableButtons(boolean b){
		detailsClient.setEnabled(b);
		delClient.setEnabled(b);
		delClient.setFocusable(b);
	}

	public String getFiltre(){
		return nomField.getText();
	}

	//Bricole de bas ï¿½tage
	public class bricoleListener implements MouseMotionListener{
		public void mouseDragged(MouseEvent arg0) {}
		public void mouseMoved(MouseEvent arg0) {
			if(ClientPanel.getRowSelected()==-1){
				delClient.setEnabled(false);
			}
		}

	}
}