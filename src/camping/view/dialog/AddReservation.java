package camping.view.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Properties;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import camping.model.Reservation;
import camping.view.database.EmplacementPanel;
import camping.view.tools.CampingColor;
import camping.view.tools.CustomButton;
import camping.view.tools.DateLabelFormatter;

public class AddReservation extends JDialog{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	Reservation resa;

	public AddReservation(){
		setTitle("Nouvelle réservation");
		setSize(500,500);
		setResizable(false);
		setModal(true);
		createGUI();
		setLocationRelativeTo(null);
	}

	public void createGUI(){
		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
		content.setBackground(CampingColor.LIGHTGREY);

		//CREATION de l'objet reservation(vide au début)
		resa = new Reservation(null,null,null,null);

		//PREMIER ETAGE
		JPanel firstFloor = new JPanel();
		firstFloor.setLayout(new BorderLayout());
		firstFloor.setBackground(CampingColor.LIGHTGREEN);
		firstFloor.setBorder(new EmptyBorder(5,5,5,5));
		JPanel numLocation = new JPanel();
		numLocation.setBackground(CampingColor.LIGHTGREY);
		numLocation.setForeground(CampingColor.GREEN);
		numLocation.setBorder(new EmptyBorder(10,10,10,10));
		JLabel numInfo = new JLabel("NumLoc : ");
		JLabel num = new JLabel(Integer.toString(resa.getID()));
		numLocation.add(numInfo);
		numLocation.add(num);
		firstFloor.add(numLocation,BorderLayout.EAST);

		JPanel dates = new JPanel();
		dates.setLayout(new BoxLayout(dates,BoxLayout.Y_AXIS));

		JPanel dArrPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
		dArrPan.setBackground(CampingColor.LIGHTGREY);
		JPanel contArrLab = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		contArrLab.setBackground(CampingColor.LIGHTGREY);
		JLabel dArrLabel = new JLabel("Date d'arrivée : ");
		contArrLab.add(dArrLabel);
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Auj. ");
		p.put("text.month", "Mois");
		p.put("text.year", "Année");
		JDatePanelImpl datePanelD = new JDatePanelImpl(model, p);
		JDatePickerImpl datePickerDebut = new JDatePickerImpl(datePanelD, new DateLabelFormatter());
		dArrPan.add(contArrLab);
		dArrPan.add(datePickerDebut);

		JPanel dDepPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
		dDepPan.setBackground(CampingColor.LIGHTGREY);
		JPanel contDepLab = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		contDepLab.setBackground(CampingColor.LIGHTGREY);
		JLabel dDepLabel = new JLabel("Date de départ : ");
		contDepLab.add(dDepLabel);
		UtilDateModel model2 = new UtilDateModel();
		Properties p2 = new Properties();
		p2.put("text.today", "Auj. ");
		p2.put("text.month", "Mois");
		p2.put("text.year", "Année");
		JDatePanelImpl datePanelF = new JDatePanelImpl(model2, p2);
		JDatePickerImpl datePickerFin = new JDatePickerImpl(datePanelF, new DateLabelFormatter());
		dDepPan.add(contDepLab);
		dDepPan.add(datePickerFin);

		JPanel typePicker = new JPanel(new FlowLayout(FlowLayout.LEFT));
		typePicker.setBackground(CampingColor.LIGHTGREY);
		JPanel contTypLab = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		contTypLab.setBackground(CampingColor.LIGHTGREY);
		JLabel typeLabel = new JLabel("Type de bien : ");
		contTypLab.add(typeLabel);
		JRadioButton locButton = new JRadioButton("Location");
		locButton.setOpaque(false);
		JRadioButton empButton = new JRadioButton("Emplacement");
		empButton.setOpaque(false);
		ButtonGroup typeLoc = new ButtonGroup();
		typeLoc.add(locButton);
		typeLoc.add(empButton);

		typePicker.add(contTypLab);
		typePicker.add(locButton);
		typePicker.add(empButton);

		dates.add(dArrPan);
		dates.add(dDepPan);
		dates.add(typePicker);
		firstFloor.add(dates,BorderLayout.CENTER);

		//DEUXIEME ETAGE
		JPanel secondFloor = new JPanel();
		secondFloor.setLayout(new BorderLayout());
		secondFloor.setBackground(CampingColor.LIGHTGREEN);
		secondFloor.setBorder(new EmptyBorder(5,5,5,5));
		EmplacementPanel empDispo = new EmplacementPanel(EmplacementPanel.LIBRE);
		JPanel infoSel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel empSel = new JLabel(" Emplacement sélectionné : MORDS MES COUILLES LOL");
		infoSel.add(empSel);
		infoSel.setBackground(CampingColor.LIGHTGREY);
		secondFloor.add(empDispo,BorderLayout.CENTER);
		secondFloor.add(infoSel,BorderLayout.SOUTH);

		//TROISIEME ETAGE
		JPanel thirdFloor = new JPanel(new BorderLayout());
		thirdFloor.setBackground(CampingColor.LIGHTGREEN);
		thirdFloor.setBorder(new EmptyBorder(5,5,5,5));
		JPanel clientCont = new JPanel(new FlowLayout(FlowLayout.LEFT));
		clientCont.setBackground(CampingColor.LIGHTGREY);
		JPanel clicliContLab = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		clicliContLab.setOpaque(false);
		JLabel clicliLabel = new JLabel("Client : ");
		clicliContLab.add(clicliLabel);
		JPanel buttonCliCont = new JPanel();
		buttonCliCont.setOpaque(false);
		CustomButton selCli = new CustomButton("Selectionner...");
		CustomButton newCli = new CustomButton("Nouveau client...");
		buttonCliCont.add(selCli);
		buttonCliCont.add(newCli);
		clientCont.add(clicliContLab);
		clientCont.add(buttonCliCont);
		thirdFloor.add(clientCont,BorderLayout.CENTER);

		JPanel lastFloor = new JPanel();
		lastFloor.setOpaque(false);
		lastFloor.setLayout(new BoxLayout(lastFloor,BoxLayout.X_AXIS));
		CustomButton commit = new CustomButton("Enregistrer");
		CustomButton cancel = new CustomButton("Annuler");
		lastFloor.add(Box.createHorizontalGlue());
		lastFloor.add(commit);
		lastFloor.add(Box.createRigidArea(new Dimension(10,10)));
		lastFloor.add(cancel);
		lastFloor.setBorder(new EmptyBorder(5,5,5,5));

		JPanel[] l = {contArrLab,contDepLab,contTypLab,clicliContLab};
		int maxW = 0;
		for(JPanel j : l){
			if(j.getPreferredSize().width>maxW){
				maxW=j.getPreferredSize().width;
			}
		}
		for(JPanel j : l){
			j.setPreferredSize(new Dimension(maxW,j.getPreferredSize().height));
		}

		content.add(firstFloor);
		content.add(secondFloor);
		content.add(thirdFloor);
		content.add(lastFloor);
		this.setContentPane(content);
	}

	public static void main(String[] args){
		AddReservation da = new AddReservation();
		da.setVisible(true);
	}

}
