package camping.controler.editeur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import camping.model.database.EmplacementDB;
import camping.view.DrawableCamping;
import camping.view.dialog.DetailsEmplacement;

public class UpdateEmpListener implements ActionListener{

	private DetailsEmplacement parent;
	private DrawableCamping grandparent;

	public UpdateEmpListener(DrawableCamping dc,DetailsEmplacement a){
		super();
		parent=a;
		grandparent=dc;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		EmplacementDB.modifierEmplacementNumero(parent.getNumEmp(), parent.getPreviousNB());
		EmplacementDB.modifierEmplacementType(parent.getTypeSel(), parent.getPreviousNB());
		EmplacementDB.modifierEmplacementNbPlaces(parent.getNbPers(), parent.getPreviousNB());
		EmplacementDB.modifierEmplacementPrix(parent.getPrix(), parent.getPreviousNB());
		if(parent.getPreviousNB()!=parent.getNumEmp())grandparent.setNewNum(parent.getPreviousNB(),parent.getNumEmp());
	}

}
