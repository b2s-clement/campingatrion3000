package camping.controler.clients;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import camping.model.database.ClientDB;
import camping.model.database.Connexion;
import camping.model.database.CustomTableModel;
import camping.view.Clients;
import camping.view.database.ClientPanel;
import camping.view.dialog.DetailsClient;

public class FiltreClientListener implements ActionListener{

	private Clients fenetre;
	private String  title[] = {"Genre", "Nom", "Prenom", "Adresse", "Ville", "Telephone","ID"};

	public FiltreClientListener(Clients c){
		super();
		fenetre=c;
	}

	public void actionPerformed(ActionEvent e) {
		String filtre=fenetre.getFiltre();
		ClientDB.rechercheBaseClient2("nom",filtre);
		Object[][] data =new Object[ClientDB.nbClient()][10];
		data = ClientDB.getDataFiltre("nom",filtre);
		//CustomTableModel model = new CustomTableModel(data, title);
		//ClientPanel.updateModel(data);
    }

}
