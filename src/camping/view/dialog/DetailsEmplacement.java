package camping.view.dialog;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import camping.controler.editeur.UpdateEmpListener;
import camping.model.Emplacement;
import camping.model.database.EmplacementDB;
import camping.view.DrawableCamping;
import camping.view.tools.CampingColor;

public class DetailsEmplacement extends AddEmplacement{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Emplacement empl;
	private DrawableCamping d;

	public DetailsEmplacement(DrawableCamping dc, int n) {
		super(dc, n);
		d=dc;
		rentrerInfos(n);
		addBandeauTop();
		updateListeners();
	}

	public void rentrerInfos(int n){
		this.setTitle("Détails emplacement");
		empl = EmplacementDB.getEmp(n);
		numEmpData.setText(Integer.toString(empl.getNumeroEmplacement()));
		if(empl.getType().equals("Location")){
			loc.setSelected(true);
		}else{
			emp.setSelected(true);
		}
		nbPersData.setSelectedItem(empl.getNbPlaces());
		prixData.setText(Double.toString(empl.getPrixEmplacement()));
		prixValue=empl.getPrixEmplacement();
		ok.setEnabled(true);
		prixCorrect=true;
		typeSelected=true;
	}

	public void addBandeauTop(){
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());
		content.add(this.getContentPane(),BorderLayout.CENTER);

		JLabel infoOccupe = new JLabel();
		infoOccupe.setBackground(CampingColor.LIGHTGREEN);
		infoOccupe.setForeground(CampingColor.GREEN);
		infoOccupe.setHorizontalAlignment(SwingConstants.CENTER);
		infoOccupe.setBorder(new EmptyBorder(5,5,5,5));
		if(empl.isOccupe()){
			infoOccupe.setText("Etat : occupé");
		}else{
			infoOccupe.setText("Etat : libre");
		}
		content.add(infoOccupe,BorderLayout.NORTH);
		this.setContentPane(content);
	}

	public void updateListeners(){
		for(ActionListener a : ok.getActionListeners()){
			ok.removeActionListener(a);
		}
		for(ActionListener a : annuler.getActionListeners()){
			annuler.removeActionListener(a);
		}
		ok.addActionListener(new CloseDialogListener());
		annuler.addActionListener(new CloseDialogListener());
		ok.addActionListener(new UpdateEmpListener(d,this));

	}

}
