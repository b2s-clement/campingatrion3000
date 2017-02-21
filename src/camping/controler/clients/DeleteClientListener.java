package camping.controler.clients;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import camping.model.Client;
import camping.model.database.ClientDB;
import camping.view.Clients;
import camping.view.database.ClientPanel;
import camping.view.dialog.CustomDialog;

public class DeleteClientListener implements ActionListener{

	public void actionPerformed(ActionEvent e) {
		int row = ClientPanel.getRowSelected();
		Client victime = ClientPanel.getClientRow(row);
        if (row!=-1) {
            int choix = CustomDialog.confirmDialog("Supprimer", "Vous êtes sur le point de supprimer "+victime.getGenre()+" "+victime.getNom()+" "+victime.getPrenom());
            if(choix==CustomDialog.commited){
            	ClientDB.supprimerClient(victime);
            	ClientPanel.supprimerClient();
            	Clients.correctifDelButton();
            	CustomDialog.infoDialog("Suppression réussie", "Le client a été supprimé de la base de données.");
            }
        }
	}

}
