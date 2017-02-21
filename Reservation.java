package camping.model.database;

import camping.view.tools.CustomDate;



	/*********************************************************
	 * 
	 * 		idEmplacement
	 * 
	 * 		Arret precedent :
	 * 				pendant le test de la methode InTime()
	 * 
	 * 
	 * 
	 *********************************************************/



public class Reservation 
{
	//Attributs
	private int id; // Clé primaire
	private static int nbReservation=0;;
	private String date_deb, date_fin;
	
		//liaison étrangère
	private int idEmplacement,idClient;
	private boolean enCour, valider;
	
		
	public Reservation(int id_Client, String dateDeb, String dateFin)
	{
		idClient=id_Client;		//Rajouter emplacement (une fois crée)
		
		nbReservation++;		id = nbReservation;	
		date_deb=dateDeb;		date_fin=dateFin;
		valider=false;			enCour=true;
	}
	
	
	
	
	//Getter && Setter
	public boolean EnCour()		{return enCour;}
	public int getID()			{return id;}
	
		//Relatif au client
	public int getClient()		{return idClient;}
	public void setClient(int id_Client)		{idClient = id_Client;}
		
		//Relatifs a l'emplacement
	public int getEmplacement()	{return idEmplacement;}
	
	
	//Methods	
	
	public void Depart()
	{
		valider = true;
	}
	
	public boolean InTime()
	{		
		CustomDate current = new CustomDate();
		int jour = current.getDay();
		int mois = current.getMonth();
		int anne = current.getYear();
		
		
		String str[]=date_fin.split("--");
		
		
		
		if ( (Integer.parseInt(str[0]) > anne)  && (Integer.parseInt(str[1]) > mois) && (Integer.parseInt(str[2]) > jour))
		{
			enCour = false;
		}
		return enCour;
}
	
	
	
	
	//  aaaa--mm--jj
	public int validite()		// Test par rapport au temp de la location
	{							// 	1 => Reservation fini (temps + départ)
								//	0 => Reservation en cour
								// -1 => Reservation terminer mais sans départ
		return 1;
	}
	
	public static void main(String[]args)
	{
		Reservation res1 = new Reservation(1, "2016--02--10", "2016--06--9");
		Reservation res2 = new Reservation(1, "2016--02--10", "2016--06--11");		
		
		if (res1.InTime()) System.out.println("res 1 est OK");
		if (res2.InTime()) System.out.println("res 2 est OK");
			
	}
}
