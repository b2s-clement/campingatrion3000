package camping.model.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import camping.model.Client;

public class ClientDB {

	//Liste des attributs (compression des méthodes d'accès)
	public static String ID = "id";
	public static String GENRE = "genre";
	public static String NOM = "nom";
	public static String PRENOM = "prenom";
	public static String ADRESSE = "adresse";
	public static String VILLE = "ville";
	public static String CODE = "codePostal";
	public static String TEL = "telephone";
	public static String DATE = "dateNaissance";
	public static String NLOC = "nbLocations";


	//Regroupement des méthodes d'accès à la base données 'Client'

	public static void ajouterNouveauClient(Client cli){
    	Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        connexion.addClient(cli);
        connexion.close();
    }

	public static int nbClient(){
    	Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        int res=0;
        ResultSet resultSet = connexion.query("SELECT count() FROM client");
        try{
        	res= resultSet.getInt("count()");
        }catch(SQLException e){
            e.printStackTrace();
        }
        connexion.close();
        return res;
    }

	//cette mï¿½thode sert ï¿½ trouver le bon id pour rajouter un client
	public static int getHighestID(){
		int res=-1;
		Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        ResultSet resultSet = connexion.query("SELECT id FROM client");
		//sï¿½mantiquement le plus haut ID se trouve en fin de table
        try{
			while(resultSet.next()){
				res=resultSet.getInt("id");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		connexion.close();
		return res;
	}

	public static void supprimerClient(Client c){
		Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        connexion.queryUpdate("DELETE FROM client WHERE id='"+c.getIDClient()+"'");
        connexion.close();
	}

	public static void afficherBaseClient(){
    	Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        ResultSet resultSet = connexion.query("SELECT * FROM client");
        try {
        	while (resultSet.next()) {
                System.out.println("Client : (ID : "+resultSet.getInt("id")+") "
                					+resultSet.getString("genre")+" "
                					+resultSet.getString("nom")+" "
                					+resultSet.getString("prenom")+", "
                					+resultSet.getString("adresse")+" "
                					+resultSet.getString("ville")+" "+
                					resultSet.getString("codePostal")+", "
                					+resultSet.getString("telephone")+", nï¿½ le "
                					+resultSet.getString("dateNaissance")+", "
                					+resultSet.getInt("nbLocations")+" rï¿½servations.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connexion.close();
    }

    public static void afficherBaseClientTri(String argument,boolean desc){
    	Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        String requet;
        //Le booleen DESC nous permet de savoir si on veut trier dans l'ordre dï¿½croissant ou pas
        if(desc){
            requet="SELECT * FROM client ORDER BY "+argument+" DESC";
        }else{
            requet="SELECT * FROM client ORDER BY "+argument;
        }
        ResultSet resultSet = connexion.query(requet);
        try {
        	while (resultSet.next()) {
                System.out.println("Client : "+resultSet.getString("genre")+" "+resultSet.getString("nom")+" "+resultSet.getString("prenom")+", "+resultSet.getString("adresse")+" "+resultSet.getString("ville")+" "+resultSet.getString("codePostal")+", "+resultSet.getString("telephone")+", nï¿½ le "+resultSet.getString("dateNaissance")+", "+resultSet.getInt("nbLocations")+" rï¿½servations.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connexion.close();
    }

    public static void rechercheBaseClient2(String attribut, String recherche){
  		Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        String requet="SELECT * FROM client WHERE INSTR("+attribut+",'"+recherche+"')";
        ResultSet resultSet = connexion.query(requet);
        try{
        	while (resultSet.next()) {
        		System.out.println("Client : "+resultSet.getString("genre")+" "+resultSet.getString("nom")+" "+resultSet.getString("prenom")+", "+resultSet.getString("adresse")+" "+resultSet.getString("ville")+" "+resultSet.getString("codePostal")+", "+resultSet.getString("telephone")+", nï¿½ le "+resultSet.getString("dateNaissance")+", "+resultSet.getInt("nbLocations")+" rï¿½servations.");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        connexion.close();
    }

    public static void rechercheBaseClient(String attribut,String recherche){
    	Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        String requet="SELECT * FROM client WHERE "+attribut+"='"+recherche+"'";
        ResultSet resultSet = connexion.query(requet);
        try {
        	while (resultSet.next()) {
                System.out.println("Client : "+resultSet.getString("genre")+" "+resultSet.getString("nom")+" "+resultSet.getString("prenom")+", "+resultSet.getString("adresse")+" "+resultSet.getString("ville")+" "+resultSet.getString("codePostal")+", "+resultSet.getString("telephone")+", nï¿½ le "+resultSet.getString("dateNaissance")+", "+resultSet.getInt("nbLocations")+" rï¿½servations.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connexion.close();
    }

    public static void modifierClient(String attribut, String remplacement,int ID){
    	Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        connexion.queryUpdate("UPDATE client SET "+attribut+" = '"+remplacement+"' WHERE id='"+ID+"';");
    	connexion.close();
    }

    public static Object[][] getData(){
    	Connexion connexion = new Connexion("Database.db");
    	  connexion.connect();
    	  ResultSet resultSet = connexion.query("SELECT * FROM client ORDER BY nom DESC");

    	  //On crï¿½e ici l'objet que l'on dï¿½coupera dans notre tableau
    	  Object[][] data =new Object[nbClient()][10];
    	  int x=nbClient();
    	    try {
    	      	while (resultSet.next()) {
    	      		int id=(int)resultSet.getInt("id");
    	      		String genre=(String)resultSet.getString("genre");
    	      		String nom=(String)resultSet.getString("nom");
    	      		String prenom=(String)resultSet.getString("prenom");
    	      		String adresse=(String)resultSet.getString("adresse");
    	      		String ville=(String)resultSet.getString("ville");
    	      		String telephone=(String)resultSet.getString("telephone");

    	      		//On remplit ici notre objet qui sera interprï¿½tï¿½ dans le tableau
    	      		data[x-1][0]=genre;
    	      		data[x-1][1]=nom;
    	      		data[x-1][2]=prenom;
    	      		data[x-1][3]=adresse;
    	      		data[x-1][4]=ville;
    	      		data[x-1][5]=telephone;
    	      		data[x-1][6]=id;
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
    	  ResultSet resultSet = connexion.query("SELECT * FROM client WHERE "+attribut+"='"+recherche+"' ORDER BY "+attribut+" DESC ");

    	  //On crï¿½e ici l'objet que l'on dï¿½coupera dans notre tableau
    	  Object[][] data =new Object[nbClient()][10];
    	  int x=nbClient();
    	    try {
    	      	while (resultSet.next()) {
    	      		int id=(int)resultSet.getInt("id");
    	      		String genre=(String)resultSet.getString("genre");
    	      		String nom=(String)resultSet.getString("nom");
    	      		String prenom=(String)resultSet.getString("prenom");
    	      		String adresse=(String)resultSet.getString("adresse");
    	      		String ville=(String)resultSet.getString("ville");
    	      		String telephone=(String)resultSet.getString("telephone");

    	      		//On remplit ici notre objet qui sera interprï¿½tï¿½ dans le tableau
    	      		data[x-1][0]=genre;
    	      		data[x-1][1]=nom;
    	      		data[x-1][2]=prenom;
    	      		data[x-1][3]=adresse;
    	      		data[x-1][4]=ville;
    	      		data[x-1][5]=telephone;
    	      		data[x-1][6]=id;
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

    // RECUPERATION DU CLIENT DEPUIS l'ID

    public static Client getClient(int ID){
    	Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        ResultSet resultSet = connexion.query("SELECT * FROM client WHERE id="+ID+";");
        Client res=new Client();
        try {
        	while (resultSet.next()){
        		res.setIDClient(ID);
        		res.setGenre((String)resultSet.getString("genre"));
        		res.setNom((String)resultSet.getString("nom"));
        		res.setPrenom((String)resultSet.getString("prenom"));
        		res.setAdresse((String)resultSet.getString("adresse"));
        		res.setVille((String)resultSet.getString("ville"));
        		res.setCodePostal((String)resultSet.getString("codePostal"));
        		res.setTelephone((String)resultSet.getString("telephone"));
        		res.setDateNaissance(resultSet.getString("dateNaissance"));
        		res.setNbReservation(resultSet.getInt("nbLocations"));
        	}
        }catch(SQLException e){
                System.out.println("Client non trouvï¿½");
        }
        connexion.close();
        return res;
      }




    //--------------------Recherche dans la bdd optimisï¿½e-------------------//

  	public static void rechercheBaseClientAdresse(String recherche){
  		Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        String requet="SELECT * FROM client WHERE INSTR(adresse,'"+recherche+"')";
        ResultSet resultSet = connexion.query(requet);
        try{
        	while (resultSet.next()) {
        		System.out.println("Client : "+resultSet.getString("genre")+" "+resultSet.getString("nom")+" "+resultSet.getString("prenom")+", "+resultSet.getString("adresse")+" "+resultSet.getString("ville")+" "+resultSet.getString("codePostal")+", "+resultSet.getString("telephone")+", nï¿½ le "+resultSet.getString("dateNaissance")+", "+resultSet.getInt("nbLocations")+" rï¿½servations.");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        connexion.close();
    }

  	public static void rechercheBaseClientVille(String recherche){
      	//On ouvre la connection ï¿½ la base
      	Connexion connexion = new Connexion("Database.db");
          connexion.connect();
          String requet="SELECT * FROM client WHERE INSTR(ville,'"+recherche+"')";
          ResultSet resultSet = connexion.query(requet);
          try {
          	while (resultSet.next()) {
          		System.out.println("Client : "+resultSet.getString("genre")+" "+resultSet.getString("nom")+" "+resultSet.getString("prenom")+", "+resultSet.getString("adresse")+" "+resultSet.getString("ville")+" "+resultSet.getString("codePostal")+", "+resultSet.getString("telephone")+", nï¿½ le "+resultSet.getString("dateNaissance")+", "+resultSet.getInt("nbLocations")+" rï¿½servations.");
              }
          } catch (SQLException e) {
              e.printStackTrace();
          }

        //On ferme la connection ï¿½ la base
          connexion.close();
      }

  	//Main de test
  	/*public static void main(String[] args){
  		Client c1 = new Client("M.","LUCAS","Clï¿½ment","33 Rue du petit val","Octeville","50130","0629913932","1995-04-23",0);
  		Client c2 = new Client("M.","LUCAS","Audric","22 Rue faiblesse","Rennes","35000","0654879865","1997-02-12",0);
  		ajouterNouveauClient(c1);
  		//afficherBaseClient();
  		ajouterNouveauClient(c2);
  		afficherBaseClient();
  		supprimerClient(c1);
  		afficherBaseClient();
  		Client c = getClient(3);
  	}*/
}
