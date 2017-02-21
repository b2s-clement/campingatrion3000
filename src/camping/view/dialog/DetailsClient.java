package camping.view.dialog;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import camping.controler.clients.UpdateClientOkListener;
import camping.model.Client;


public class DetailsClient extends AddClient{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Client c1;

	public DetailsClient(Client c){
		super();
		c1=c;
		rentrerInfos(c);
		addBandeauTop(c);
		updateListeners();
	}

	public void rentrerInfos(Client c){
		this.setTitle("Details Client");

		nomData.setText(c.getNom());
		prenomData.setText(c.getPrenom());
		adr.setText(c.getAdresse());
		code.setText(c.getCodePostal());
		telData.setText(c.getTelephone());
		town.setText(c.getVille());
		genreStr=c.getGenre();
		if(c.getGenre().equals("M.")){
			monsieur.setSelected(true);
		}else{
			madame.setSelected(true);
		}
		String[] date = c.getDateNaissance().split("-");
		try{
			jours.setSelectedItem(date[2]);
		}catch(NullPointerException e){}
		try{
			mois.setSelectedItem(date[1]);
		}catch(NullPointerException e){}
		try{
			annee.setSelectedItem(date[0]);
		}catch(NullPointerException e){}

		this.enableOk();
	}

	public void addBandeauTop(Client c){
		JPanel main = new JPanel();
		main.setLayout(new BorderLayout());
		main.add(this.getContentPane(),BorderLayout.CENTER);

		JLabel infosClients = new JLabel();
		infosClients.setHorizontalAlignment(SwingConstants.CENTER);
		infosClients.setBorder(new EmptyBorder(5,5,5,5));
		infosClients.setText("ID Client : "+c.getIDClient()+" - Nombre de réservations : "+c.getNbReservation());
		main.add(infosClients,BorderLayout.NORTH);

		this.setContentPane(main);
	}

	public void updateListeners(){
		//réécriture des listeners du bouton ok
		for( ActionListener al : ok.getActionListeners() ) {
			ok.removeActionListener( al );
		}
		ok.addActionListener(new CloseDialogListener());
		ok.addActionListener(new UpdateClientOkListener(this));
	}

	public Client updatedClient(){
		Client res = c1;
		res.setGenre(genreStr);
		res.setNom(nomData.getText());
		res.setPrenom(prenomData.getText());
		res.setAdresse(adr.getText());
		res.setCodePostal(code.getText());
		res.setVille(town.getText());
		res.setTelephone(telData.getText());
		res.setDateNaissance(annee.getSelectedItem()+"-"+mois.getSelectedItem()+"-"+jours.getSelectedItem());
		return res;
	}
}
