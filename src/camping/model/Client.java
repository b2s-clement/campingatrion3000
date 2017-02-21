package camping.model;

import camping.model.database.ClientDB;

public class Client {
	private int IDClient; //L'ID du client sera son identifiant dans la base, l'ID est unique
	private String genre;
	private String nom;
	private String prenom;
	private String adresse;
	private String ville;
	private String codePostal;
	private String telephone;
	private String dateNaissance;
	private int nbReservation;

	private static int highestID = ClientDB.getHighestID();

	//BUILDERS
	public Client(String genre,String nom,String pren,String adr,String vil, String codpos,String tel,String dat, int nbr){
		this.setGenre(genre);
		this.setNom(nom);
		this.setPrenom(pren);
		this.setAdresse(adr);
		this.setVille(vil);
		this.setCodePostal(codpos);
		this.setTelephone(tel);
		this.setDateNaissance(dat);
		this.setNbReservation(nbr);
		this.IDClient = highestID+1;
		highestID++;
	}
	public Client(){
		genre="";
		nom="";
		prenom="";
		adresse="";
		ville="";
		codePostal="";
		telephone="";
		dateNaissance="";
		nbReservation=-1;
		IDClient = -1;
	}

	//METHODES
	public String toString(){return genre+" "+nom+" "+prenom+", "+adresse+" "+ville+" "+codePostal+", "+telephone+", n� le "+dateNaissance+", "+nbReservation+" r�servations";}

	//GETTERS et SETTERS
	public String getGenre(){return genre;}
	public void setGenre(String genre){this.genre = genre;}
	public String getVille(){return ville;}
	public void setVille(String ville){this.ville = ville;}
	public String getCodePostal(){return codePostal;	}
	public void setCodePostal(String codePostal){this.codePostal = codePostal;}
	public String getNom(){return nom;}
	public void setNom(String nom){this.nom = nom;}
	public String getPrenom(){return prenom;}
	public void setPrenom(String prenom){this.prenom = prenom;}
	public String getAdresse(){return adresse;}
	public void setAdresse(String adresse){this.adresse = adresse;}
	public int getNbReservation(){return nbReservation;}
	public void setNbReservation(int nbReservation){this.nbReservation = nbReservation;}
	public String getTelephone(){return telephone;}
	public void setTelephone(String tel){this.telephone = tel;}
	public String getDateNaissance(){return dateNaissance;}
	public void setDateNaissance(String dateNaissance){this.dateNaissance = dateNaissance;}
	public int getIDClient(){return IDClient;}
	public void setIDClient(int i){IDClient=i;}

}
