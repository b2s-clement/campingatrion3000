package camping.view.dialog;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;

import camping.model.Reservation;
import camping.model.database.ReservDB;
import camping.view.tools.CampingColor;

public class DetailsReservation extends JDialog{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Reservation res;

	public DetailsReservation(int id){
		res=ReservDB.getResa(id);
		setTitle("Détails réservation");
		setSize(500,500);
		setResizable(false);
		setModal(true);
		createGUI();
		setLocationRelativeTo(null);
	}

	public void createGUI(){
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());
		content.setBackground(CampingColor.LIGHTGREEN);

		JPanel infoResa = new JPanel();
		infoResa.setBackground(CampingColor.LIGHTGREY);
		infoResa.setLayout(new BoxLayout(infoResa,BoxLayout.Y_AXIS));
		
		
		
		
		JPanel middlePanel = new JPanel();
		middlePanel.setLayout(new BoxLayout(middlePanel,BoxLayout.X_AXIS));

		JPanel infoEmp = new JPanel();
		infoEmp.setBackground(CampingColor.GREEN);

		JPanel infoCli = new JPanel();
		infoCli.setBackground(CampingColor.GREY);

		middlePanel.add(infoEmp);
		middlePanel.add(infoCli);
		JPanel buttons = new JPanel();
		buttons.setBackground(CampingColor.MIDDLEGREEN);

		content.add(infoResa,BorderLayout.NORTH);
		content.add(middlePanel,BorderLayout.CENTER);
		content.add(buttons,BorderLayout.SOUTH);
		this.setContentPane(content);
	}

	public static void main(String[] args){
		DetailsReservation d = new DetailsReservation(1);
		d.setVisible(true);
	}
}
