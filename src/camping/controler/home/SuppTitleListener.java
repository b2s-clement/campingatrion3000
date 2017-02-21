package camping.controler.home;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import camping.view.dialog.CustomDialog;

public class SuppTitleListener implements ActionListener{

	public void actionPerformed(ActionEvent arg0) {
		int confirm = CustomDialog.confirmDialog("Suppression", "Voulez-vous supprimer ce camping et en démarrer un nouveau ?");
		if(confirm==CustomDialog.commited){
			CustomDialog.infoDialog("CONNARDO", "Tu es un bel enculé, bravo !");
		}
	}
}