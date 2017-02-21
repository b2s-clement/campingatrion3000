package camping.controler.clients;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import camping.model.Client;
import camping.model.database.ClientDB;
import camping.view.database.ClientPanel;
import camping.view.dialog.AddClient;

public class AddClientOkListener implements ActionListener{

	private AddClient ac;

	public AddClientOkListener(AddClient a){
		super();
		ac=a;
	}

	public void actionPerformed(ActionEvent arg0) {
		Client c = ac.createClient();
		ClientDB.ajouterNouveauClient(c);
		ClientPanel.ajoutClient(c);
	}
}
