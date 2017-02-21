package camping.controler.editeur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import camping.model.Emplacement;
import camping.model.database.EmplacementDB;
import camping.view.DrawableCamping;
import camping.view.dialog.AddEmplacement;

public class AddEmpListener implements ActionListener{

	private AddEmplacement parent;
	private DrawableCamping grandparent;

	public AddEmpListener(DrawableCamping dc,AddEmplacement a){
		super();
		parent=a;
		grandparent=dc;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//AJOUT DANS LA BDD
		Emplacement emp = new Emplacement(parent.getNumEmp(),parent.getTypeSel(),false,parent.getNbPers(),parent.getPrix());
		EmplacementDB.ajouterNouveauEmplacement(emp);
		//MAJ de l'affichage
		if(parent.getPreviousNB()!=parent.getNumEmp())grandparent.setNewNum(parent.getPreviousNB(), parent.getNumEmp());
	}

}
