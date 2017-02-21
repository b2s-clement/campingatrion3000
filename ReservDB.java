package camping.model.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import camping.model.Client;


public class ReservDB{
	//Liste des attributs (compression des méthodes d'accès)
		//Clé primaire d'emplacement et client
		public static String IDCLIENT = "idClient";
		public static String IDEMPLACEMENT = "idEmplacement"; // La clé primaire de l'emplacement a changer au besoin
		
		//Attributs que de reservation
		public static String ID="id";
		public static String DATE_DEB="date_deb";	//Date de début 
		public static String DATE_FIN="date_fin";	//Date de fin
		
		
		
	public static void ajouterNouveauClient (Reservation res){
		Connexion connexion = new Connexion("Database.db");
		connexion.connect();
		connexion.addReservation(res);
		connexion.close();
	}
	
	public static int nbReservation(){
		Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        int res=0;
        ResultSet resultSet = connexion.query("SELECT count() FROM reservation");	
        try
        {
        	res= resultSet.getInt("count()");
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        connexion.close();
        return res;
	}
	
	
	public static void supprimerReservation (Reservation res){
		Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        connexion.queryUpdate("DELETE FROM reservation WHERE id='"+res.getIDReservation()+"'"); 
        connexion.close();
	}


    public static void rechercheBaseReservationFiltre(String attribut, String recherche){
  		Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        String requet="SELECT * FROM reservation"
				  	+ "JOIN client ON client.id=reservation.idClient"
				  	+ "JOIN emplacement ON emplacement.id=reservation.idEmplacement WHERE INSTR("+attribut+",'"+recherche+"')";	
        ResultSet resultSet = connexion.query(requet);
        try{
        	while (resultSet.next()) 
        	{
        		System.out.println("Tu as recherché la recherché la reservation n° : " + resultSet.getString("id")
        						   +" réservé par : " + resultSet.getString("nom") +" "+ resultSet.getString("prenom")
        						   +" à l'emplacement n° : " + resultSet.getString("idEmplacement"));
        	}	
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        connexion.close();
    }
	
	public static void afficherBaseReservation(){
    	Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        ResultSet resultSet = connexion.query("SELECT * FROM reservation "											//	    Je ne suis pas 
        									+ "JOIN client ON client.id=reservation.idClient "						//	   sûr des jointure
        									+ "JOIN emplacement ON emplacement.id=reservation.idEmplacement");		//	  surtout l'emploie des 
        try {																										//	 attributs qui en découle
        	while (resultSet.next()) {
                System.out.println("Reservation n° : ID : "+resultSet.getInt("id")+" de "
                					+resultSet.getString("nom")+" "
                					+resultSet.getString("prenom")+", à l'emplacement "
                					+resultSet.getString("idEmplacement")+" du "
                					+resultSet.getString("date_deb")+" au "
                					+resultSet.getString("date_fin")+", pour un prix de "
                					+resultSet.getDouble("prix")+" €");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connexion.close();
    }

    public static void modifierClient(String attribut, String remplacement,int ID){
    	Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        connexion.queryUpdate("UPDATE reservation SET "+attribut+" = '"+remplacement+"' WHERE id='"+ID+"';");
    	connexion.close();
    }
    
    public static Object[][] getData(){
    	Connexion connexion = new Connexion("Database.db");
    	  connexion.connect();
          ResultSet resultSet = connexion.query("SELECT * FROM reservation "							
											   +"JOIN client ON client.id=reservation.idClient "			
											   +"JOIN emplacement ON emplacement.id=reservation.idEmplacement");

    	  //On crï¿½e ici l'objet que l'on dï¿½coupera dans notre tableau
    	  int x=nbReservation();
    	  Object[][] data =new Object[nbReservation()][7];

    	    try {
    	      	while (resultSet.next())
    	      	{
	      			int id = resultSet.getInt("id");
					String nom = resultSet.getString("nom");
        			String prenom = resultSet.getString("prenom");
        			int idEmplacement =resultSet.getInt("idEmplacement");
        			String date_deb = resultSet.getString("date_deb");
        			String date_fin = resultSet.getString("date_fin");
        			Double prix =resultSet.getDouble("prix");

    	      		//On remplit ici notre objet qui sera interprï¿½tï¿½ dans le tableau
    	      		data[x-1][0]=id;
    	      		data[x-1][1]=nom;
    	      		data[x-1][2]=prenom;
    	      		data[x-1][3]=idEmplacement;		// Probablement a remplacer juste le num de l'emplacement c'est moyen :/
    	      		data[x-1][4]=date_deb;
    	      		data[x-1][5]=date_fin;
    	      		data[x-1][6]=prix;
    	      		x=x-1;
    	        }
    	    } catch (SQLException e) {
    	          e.printStackTrace();
    	    } catch (ArrayIndexOutOfBoundsException e){
    	    	System.out.println("Erreur lors de l'affichage de la base");
    	    }
    	    connexion.close();
    	    return data;
    }
    
    public static Object[][] getDataFiltre(String attribut, String recherche){
    	Connexion connexion = new Connexion("Database.db");
  	  connexion.connect();
      ResultSet resultSet = connexion.query("SELECT * FROM reservation "							
										   +"JOIN client ON client.id=reservation.idClient "			
										   +"JOIN emplacement ON emplacement.id=reservation.idEmplacement"
      									   +"WHERE INSTR("+attribut+",'"+recherche+"')");

    	  //On crï¿½e ici l'objet que l'on dï¿½coupera dans notre tableau
    	  Object[][] data =new Object[nbReservation()][7];
    	  int x=0;
    	    try {
    	      	while (resultSet.next())
    	      	{
	      			int id = resultSet.getInt("id");
					String nom = resultSet.getString("nom");
        			String prenom = resultSet.getString("prenom");
        			int idEmplacement =resultSet.getInt("idEmplacement");
        			String date_deb = resultSet.getString("date_deb");
        			String date_fin = resultSet.getString("date_fin");
        			Double prix =resultSet.getDouble("prix");

    	      		//On remplit ici notre objet qui sera interprï¿½tï¿½ dans le tableau
    	      		data[x-1][0]=id;
    	      		data[x-1][1]=nom;
    	      		data[x-1][2]=prenom;
    	      		data[x-1][3]=idEmplacement;		// Probablement a remplacer juste le num de l'emplacement c'est moyen :/
    	      		data[x-1][4]=date_deb;
    	      		data[x-1][5]=date_fin;
    	      		data[x-1][6]=prix;
    	      		x=x-1;
    	        }
    	    } catch (SQLException e) {
    	          e.printStackTrace();
    	    } catch (ArrayIndexOutOfBoundsException e){
    	    	System.out.println("Erreur lors de l'affichage de la base");
    	    }
    	    connexion.close();
    	    return data;
    }
    
    // RECUPERATION DE LA RESERVATION DEPUIS l'ID

    public static Reservation getReservation(int ID){
    	Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        ResultSet resultSet = connexion.query("SELECT * FROM reservation WHERE id="+ID+";");
        Reservation res=new Reservation();
        try {
        	while (resultSet.next())
        	{
      			int id = resultSet.getInt("id");
				String nom = resultSet.getString("nom");
    			String prenom = resultSet.getString("prenom");
    			int idEmplacement =resultSet.getInt("idEmplacement");
    			String date_deb = resultSet.getString("date_deb");
    			String date_fin = resultSet.getString("date_fin");
    			Double prix =resultSet.getDouble("prix");
        	}
        	}catch(SQLException e)
        	{
                System.out.println("Reservation non trouvé");
        	}
	    connexion.close();
	    return res;
      }
    
    // RECUPERATION DE LA RESERVATION DEPUIS la date
    	
    			/**LA DATE DE DEBUT **/
    public static Reservation getReservationDateDeb(String date){
    	Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        ResultSet resultSet = connexion.query("SELECT * FROM reservation WHERE date_deb="+date+";");
        Reservation res=new Reservation();
        try {
        	while (resultSet.next())
        	{
      			int id = resultSet.getInt("id");
				String nom = resultSet.getString("nom");
    			String prenom = resultSet.getString("prenom");
    			int idEmplacement =resultSet.getInt("idEmplacement");
    			String date_deb = resultSet.getString("date_deb");
    			String date_fin = resultSet.getString("date_fin");
    			Double prix =resultSet.getDouble("prix");
        	}
        	}catch(SQLException e)
        	{
                System.out.println("Reservation non trouvé");
        	}
	    connexion.close();
	    return res;
      }

    			/**LA DATE DE FIN **/
	public static Reservation getReservationDateDeb(String date){
		Connexion connexion = new Connexion("Database.db");
		connexion.connect();
		ResultSet resultSet = connexion.query("SELECT * FROM reservation WHERE date_fin="+date+";");
		Reservation res=new Reservation();
		try 
		{
			while (resultSet.next())
			{
				int id = resultSet.getInt("id");
				String nom = resultSet.getString("nom");
				String prenom = resultSet.getString("prenom");
				int idEmplacement =resultSet.getInt("idEmplacement");
				String date_deb = resultSet.getString("date_deb");
				String date_fin = resultSet.getString("date_fin");
				Double prix =resultSet.getDouble("prix");
			}
		}catch(SQLException e)
		{
		    System.out.println("Reservation non trouvé");
		}
		connexion.close();
		return res;
	}
		    
}
