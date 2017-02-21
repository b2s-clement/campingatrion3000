package camping.view.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Calendar;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import camping.view.tools.CampingColor;
import camping.view.tools.CustomButton;
import camping.controler.clients.AddClientOkListener;
import camping.model.Client;

public class AddClient extends JDialog {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	//elements
	protected CustomButton ok;
	private CustomButton annuler;

	protected String genreStr="";
	protected JRadioButton monsieur;
	protected JRadioButton madame;

	protected JTextField nomData;
	protected JTextField prenomData;

	protected JTextField adr;
	protected JTextField code;
	protected JTextField town;

	protected JTextField telData;

	protected JComboBox<String> jours;
	protected JComboBox<String> mois;
	protected JComboBox<String> annee;

	public AddClient(){
		this.setSize(new Dimension(400,350));
		this.setResizable(false);
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setTitle("Nouveau Client");
		this.createGUI();
	}

	public void createGUI(){
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());
		content.setBackground(CampingColor.LIGHTGREY);
		content.setBorder(new EmptyBorder(5,5,5,5));

		//Contenu de la boite de dialogue

		//Boutons ok/annuler
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons,BoxLayout.X_AXIS));
		ok = new CustomButton("OK");
		ok.setPreferredSize(new Dimension(60,30));
		ok.addActionListener(new CloseDialogListener());
		ok.addActionListener(new AddClientOkListener(this));
		ok.setEnabled(false);
		annuler = new CustomButton("Annuler");
		annuler.setPreferredSize(new Dimension(80,30));
		annuler.addActionListener(new CloseDialogListener());
		buttons.add(Box.createHorizontalGlue());
		buttons.add(ok);
		buttons.add(Box.createRigidArea(new Dimension(5,5)));
		buttons.add(annuler);
		buttons.setOpaque(false);


		//Saisie des champs
		JPanel champs = new JPanel();
		champs.setLayout(new BoxLayout(champs,BoxLayout.Y_AXIS));
		champs.setOpaque(false);


		JPanel genre = new JPanel(new FlowLayout(FlowLayout.LEFT));
		genre.setOpaque(false);
		JPanel genreInfo = new JPanel();
		genreInfo.setOpaque(false);
		genreInfo.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JLabel genreInfoL = new JLabel("M/Mme : ");
		genreInfo.add(genreInfoL);
		monsieur = new JRadioButton("M");
		monsieur.setOpaque(false);
		madame = new JRadioButton("Mme");
		madame.setOpaque(false);
		ButtonGroup group = new ButtonGroup();
		monsieur.addActionListener(new InternalGenreListener());
		madame.addActionListener(new InternalGenreListener());
		group.add(monsieur);
		group.add(madame);
		genre.add(genreInfo);
		genre.add(monsieur);
		genre.add(madame);

		JPanel nom = new JPanel(new FlowLayout(FlowLayout.LEFT));
		nom.setOpaque(false);
		JLabel nomInfoL = new JLabel("Nom : ");
		JPanel nomInfo = new JPanel();
		nomInfo.setOpaque(false);
		nomInfo.setLayout(new FlowLayout(FlowLayout.RIGHT));
		nomInfo.add(nomInfoL);
		nomData = new JTextField(15);
		nomData.addKeyListener(new DialogFieldListener());
		nom.add(nomInfo);
		nom.add(nomData);

		JPanel prenom = new JPanel(new FlowLayout(FlowLayout.LEFT));
		prenom.setOpaque(false);
		JLabel prenomInfoL = new JLabel("Prenom : ");
		JPanel prenomInfo = new JPanel();
		prenomInfo.setOpaque(false);
		prenomInfo.setLayout(new FlowLayout(FlowLayout.RIGHT));
		prenomInfo.add(prenomInfoL);
		prenomData = new JTextField(15);
		prenomData.addKeyListener(new DialogFieldListener());
		prenom.add(prenomInfo);
		prenom.add(prenomData);

		JPanel adresse = new JPanel(new FlowLayout(FlowLayout.LEFT));
		adresse.setOpaque(false);
		JLabel adresseInfoL = new JLabel("Adresse : ");
		JPanel adresseInfo = new JPanel();
		adresseInfo.add(adresseInfoL);
		adresseInfo.setOpaque(false);
		adresseInfo.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JPanel adresseFields = new JPanel();
		adresseFields.setLayout(new BorderLayout());
		adr = new JTextField(20);
		adr.setToolTipText("Adresse");
		adr.addKeyListener(new DialogFieldListener());
		code = new JTextField(5);
		code.setToolTipText("CodePostal");
		code.addKeyListener(new DialogFieldListener());
		town = new JTextField(15);
		town.setToolTipText("VILLE");
		town.addKeyListener(new DialogFieldListener());
		adresseFields.add(adr,BorderLayout.NORTH);
		adresseFields.add(code,BorderLayout.CENTER);
		adresseFields.add(town,BorderLayout.EAST);
		adresse.add(adresseInfo);
		adresse.add(adresseFields);

		JPanel telephone = new JPanel(new FlowLayout(FlowLayout.LEFT));
		telephone.setOpaque(false);
		JLabel telInfoL  = new JLabel("Tel. : ");
		JPanel telInfo = new JPanel();
		telInfo.setOpaque(false);
		telInfo.setLayout(new FlowLayout(FlowLayout.RIGHT));
		telInfo.add(telInfoL);
		telData = new JTextField(10);
		telData.addKeyListener(new DialogFieldListener());
		telephone.add(telInfo);
		telephone.add(telData);

		JPanel dateNaissance = new JPanel(new FlowLayout(FlowLayout.LEFT));
		dateNaissance.setOpaque(false);
		JLabel dateInfoL = new JLabel("Date de Naissance : ");
		JPanel dateInfo = new JPanel();
		dateInfo.setOpaque(false);
		dateInfo.setLayout(new FlowLayout(FlowLayout.RIGHT));
		dateInfo.add(dateInfoL);
		String[] joursData = new String[32];
		joursData[0]="jj";
		for(int i=1;i<32;i++){
			joursData[i]=Integer.toString(i);
			if(i<10){
				joursData[i]="0"+joursData[i];
			}
		}
		jours = new JComboBox<String>(joursData);
		String[] moisData = new String[13];
		moisData[0]="mm";
		for(int i=1;i<13;i++){
			moisData[i]=Integer.toString(i);
			if(i<10){
				moisData[i]="0"+moisData[i];
			}
		}
		mois = new JComboBox<String>(moisData);

		Calendar r = Calendar.getInstance();
		int y = r.get(Calendar.YEAR);
		String[] anneeData = new String[100];
		anneeData[0]="aaaa";
		for(int i=0;i<99;i++){
			anneeData[i+1]=Integer.toString(y-i);
		}

		annee = new JComboBox<String>(anneeData);
		dateNaissance.add(dateInfo);
		jours.addItemListener(new InternalDateListener());
		mois.addItemListener(new InternalDateListener());
		annee.addItemListener(new InternalDateListener());
		dateNaissance.add(jours);
		dateNaissance.add(mois);
		dateNaissance.add(annee);

		//set size for every label :
		JPanel[] panels = {genreInfo, nomInfo,prenomInfo,adresseInfo,telInfo,dateInfo};
		int maxW = 0;
		for(JPanel l:panels){
			int cL = l.getPreferredSize().width;
			if(cL>maxW){
				maxW=cL;
			}
		}
		for(JPanel l:panels){
			l.setPreferredSize(new Dimension(maxW,l.getPreferredSize().height));
		}

		champs.add(genre);
		champs.add(nom);
		champs.add(prenom);
		champs.add(adresse);
		champs.add(telephone);
		champs.add(dateNaissance);

		//Implémentation finale
		content.add(champs,BorderLayout.CENTER);
		content.add(buttons,BorderLayout.SOUTH);
		this.setContentPane(content);
	}

	public class InternalGenreListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			genreStr=e.getActionCommand()+".";
			AddClient.this.enableOk();
		}
	}

	public class InternalDateListener implements ItemListener{
		public void itemStateChanged(ItemEvent e) {
			AddClient.this.enableOk();
		}
	}

	public class CloseDialogListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			dispose();
		}
	}

	public class DialogFieldListener implements KeyListener{

		public void keyPressed(KeyEvent arg0) {
		}

		public void keyReleased(KeyEvent arg0) {
			AddClient.this.enableOk();
		}

		public void keyTyped(KeyEvent arg0) {
		}

	}

	public Client createClient(){
		Client res = new Client(genreStr,
								nomData.getText().toUpperCase(),
								prenomData.getText(),
								adr.getText(),
								town.getText().toUpperCase(),
								code.getText(),
								telData.getText(),
								annee.getSelectedItem()+"-"+mois.getSelectedItem()+"-"+jours.getSelectedItem(),0);
		return res;
	}

	public void enableOk(){
		boolean currentState = !genreStr.equals("") &&
							   !nomData.getText().equals("") &&
							   !prenomData.getText().equals("") &&
							   !adr.getText().equals("") &&
							   !town.getText().equals("") &&
							   !code.getText().equals("") &&
							   !telData.getText().equals("") &&
							   !annee.getSelectedItem().equals("aaaa") &&
							   !mois.getSelectedItem().equals("mm") &&
							   !jours.getSelectedItem().equals("jj");
		if(currentState){
			ok.setEnabled(true);
		}else{
			ok.setEnabled(false);
		}
	}

}
