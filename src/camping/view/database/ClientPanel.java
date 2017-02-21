package camping.view.database;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import camping.controler.clients.TableSelectionListener;
import camping.model.Client;
import camping.model.database.ClientDB;
import camping.model.database.CustomTableModel;
import camping.view.Clients;

public class ClientPanel extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private static Object[][] data;
	private static String  title[] = {"Genre", "Nom", "Prenom", "Adresse", "Ville", "Telephone","ID"};
	private static CustomTableModel  model;
	private static JTable tableau;

	private static int rowSelected=-1;

	public ClientPanel(){
		this.setLayout(new BorderLayout());
		data = ClientDB.getData();
		//Les titres des colonnes
		model = new CustomTableModel(data, title);
		tableau = new JTable(model);
		tableau.setSelectionMode(0);
		tableau.getTableHeader().setReorderingAllowed(false);
		//On autorise ici le tri des colonnes
		tableau.setAutoCreateRowSorter(true);
		tableau.addMouseListener(new TableSelectionListener());

		tableau.getColumnModel().getColumn(0).setPreferredWidth(5);
		tableau.getColumnModel().getColumn(6).setPreferredWidth(5);

		//La Classe XTableRenderer permet de centrer les �l�ments dans les cases du tableau
		XTableRenderer rend = new XTableRenderer();
		tableau.getColumnModel().getColumn(1).setCellRenderer(rend);
		for(int i=0; i<tableau.getColumnModel().getColumnCount(); i++)
			tableau.getColumnModel().getColumn(i).setCellRenderer(rend);
		this.add(new JScrollPane(tableau),BorderLayout.CENTER);
	}

	public static void ajoutClient(Client c){
		Object[] add = {c.getGenre(),
						c.getNom(),
						c.getPrenom(),
						c.getAdresse(),
						c.getVille(),
						c.getTelephone(),
						c.getIDClient()};
		model.addRow(add);
	}

	public static Client getClientRow(int r){
		Client res = ClientDB.getClient(Integer.parseInt(tableau.getValueAt(r, 6).toString()));
		return res;
	}

	public static int getRowSelected(){
		return rowSelected;
	}

	public static void updateClient(Client c){
		model.setValueAt(c.getGenre(), rowSelected, 0);
		model.setValueAt(c.getNom(), rowSelected, 1);
		model.setValueAt(c.getPrenom(), rowSelected, 2);
		model.setValueAt(c.getAdresse(), rowSelected, 3);
		model.setValueAt(c.getVille(), rowSelected, 4);
		model.setValueAt(c.getTelephone(), rowSelected, 5);
	}

	public static void supprimerClient(){
		model.removeRow(rowSelected);
		selectRow(-1);
	}

	public static void selectRow(int r){
		rowSelected = r;
		if(rowSelected!=-1){
			Clients.enableButtons(true);
		}else{
			Clients.enableButtons(false);
		}
	}
}
