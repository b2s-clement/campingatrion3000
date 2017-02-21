package camping.controler.editeur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import camping.model.database.CampingData;
import camping.view.DrawableCamping;
import camping.view.dialog.CustomDialog;

public class SaveDataListener implements ActionListener {

	DrawableCamping parent;

	public SaveDataListener(DrawableCamping d){
		super();
		parent=d;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try{
			CampingData.saveCamping(parent.getCamping(),"camping.c3k");
			CampingData.saveEmplacements(parent.getEmplacements(),"emplacements.c3k");
			CustomDialog.infoDialog("Sauvegarde", "Les données du camping ont bien été sauvegardées.");
		}catch(Exception e){
			CustomDialog.errorDialog("Erreur", "Une erreur est survenue lors de la sauvegarde des données.");
		}

	}

}
