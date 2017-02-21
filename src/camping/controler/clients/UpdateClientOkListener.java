package camping.controler.clients;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import camping.model.Client;
import camping.model.database.ClientDB;
import camping.view.database.ClientPanel;
import camping.view.dialog.DetailsClient;

public class UpdateClientOkListener implements ActionListener{

	private DetailsClient d;

	public UpdateClientOkListener(DetailsClient dc){
		super();
		d=dc;
	}

	public void actionPerformed(ActionEvent e) {
		Client c = d.updatedClient();
		//modif dans la bdd
		int id =  c.getIDClient();
		ClientDB.modifierClient(ClientDB.NOM, c.getNom(),id);
		ClientDB.modifierClient(ClientDB.PRENOM, c.getPrenom(), id);
		ClientDB.modifierClient(ClientDB.ADRESSE, c.getAdresse(), id);
		ClientDB.modifierClient(ClientDB.VILLE, c.getVille(), id);
		ClientDB.modifierClient(ClientDB.CODE, c.getCodePostal(), id);
		ClientDB.modifierClient(ClientDB.TEL, c.getTelephone(), id);
		ClientDB.modifierClient(ClientDB.DATE, c.getDateNaissance(), id);
		//maj dans le tableau d'affichage
		ClientPanel.updateClient(c);
	}

}
