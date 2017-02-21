package camping.model.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import camping.model.Client;
import camping.model.Emplacement;
import camping.model.Reservation;
import camping.view.tools.CustomDate;


public class ReservDB{
	//Liste des attributs (compression des méthodes d'accès)
		public static String IDCLIENT = "numCli";
		public static String IDEMPLACEMENT = "numEmp";
		public static String ID="ID";
		public static String DATE_DEB="dateArr";
		public static String DATE_FIN="dateDep";
		public static String REGLEMENT="reglement";
		public static String ENCOURS="encours";

	public static void ajouterNouveauClient(Reservation res){
		Connexion connexion = new Connexion("Database.db");
		connexion.connect();
		connexion.addReservation(res);
		connexion.close();
	}

	public static boolean isNumResTaken(int a){
		boolean res=false;
		Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        ResultSet resultSet = connexion.query("SELECT ID FROM reservation");
        try{
			while(resultSet.next()&&!res){
				res=resultSet.getInt("ID")==a;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
        connexion.close();
		return res;
	}

	public static int nbReservation(){
		Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        int res=0;
        ResultSet resultSet = connexion.query("SELECT count() FROM reservation");
        try{
        	res= resultSet.getInt("count()");
        }catch(SQLException e){
            e.printStackTrace();
        }
        connexion.close();
        return res;
	}
	public static int nbReservation(int v,String s){
		Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        int res=0;
        String request;
        switch(v){
        case 1:
        	request="SELECT count() FROM reservation WHERE dateArr='"+s+"'";
        	break;
        case 2:
        	request="SELECT count() FROM reservation WHERE dateDep='"+s+"'";
        	break;
        default:
        	request="SELECT count() FROM reservation";
        	break;
        }
        ResultSet resultSet = connexion.query(request);
        try{
        	res= resultSet.getInt("count()");
        }catch(SQLException e){
            e.printStackTrace();
        }
        connexion.close();
        return res;
	}


	public static void supprimerReservation(Reservation res){
		Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        connexion.queryUpdate("DELETE FROM reservation WHERE id='"+res.getID()+"'");
        connexion.close();
	}

	public static Object[][] getData(){
		Connexion connexion = new Connexion("Database.db");
		connexion.connect();
		ResultSet resultSet = connexion.query("SELECT * FROM reservation JOIN client ON reservation.numCli=client.id");
		int x = nbReservation();
		Object[][] data = new Object[x][6];
		try{
			int id,emp;
			String cli,dateArr,dateDep,etat;
			while(resultSet.next()){
				id=(int)resultSet.getInt("ID");
				cli=(String)resultSet.getString("nom")+" "+(String)resultSet.getString("prenom");
				emp=(int)resultSet.getInt("numEmp");
				dateArr=(String)resultSet.getString("dateArr");
				dateDep=(String)resultSet.getString("dateDep");
				if((boolean)resultSet.getBoolean("reglement")){
					etat="Réglé";
				}else{
					if(!(boolean)resultSet.getBoolean("encours")){
						etat="En attente";
					}else{
						etat="Non réglé";
					}
				}
				data[x-1][0]=id;
				data[x-1][1]=cli;
				data[x-1][2]=emp;
				data[x-1][3]=dateArr;
				data[x-1][4]=dateDep;
				data[x-1][5]=etat;
				x--;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Erreur lors de l'affichage de la base");
		}
		connexion.close();
		return data;
	}
	public static Object[][] getData(int v){
		String today = (new CustomDate()).toString();
		Connexion connexion = new Connexion("Database.db");
		connexion.connect();
		String request;
		switch(v){
		case 1:
			request="SELECT * FROM reservation JOIN client ON reservation.numCli=client.id WHERE dateArr='"+today+"'";
			break;
		case 2:
			request="SELECT * FROM reservation JOIN client ON reservation.numCli=client.id WHERE dateDep='"+today+"'";
			break;
		default:
			request="SELECT * FROM reservation JOIN client ON reservation.numCli=client.id";
			break;
		}
		ResultSet resultSet = connexion.query(request);
		int x = nbReservation(v,today);
		Object[][] data = new Object[x][6];
		try{
			int id,emp;
			String cli,dateArr,dateDep,etat;
			while(resultSet.next()){
				id=(int)resultSet.getInt("ID");
				cli=(String)resultSet.getString("nom")+" "+(String)resultSet.getString("prenom");
				emp=(int)resultSet.getInt("numEmp");
				dateArr=(String)resultSet.getString("dateArr");
				dateDep=(String)resultSet.getString("dateDep");
				if((boolean)resultSet.getBoolean("reglement")){
					etat="Réglé";
				}else{
					if(!(boolean)resultSet.getBoolean("encours")){
						etat="En attente";
					}else{
						etat="Non réglé";
					}
				}
				data[x-1][0]=id;
				data[x-1][1]=cli;
				data[x-1][2]=emp;
				data[x-1][3]=dateArr;
				data[x-1][4]=dateDep;
				data[x-1][5]=etat;
				x--;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Erreur lors de l'affichage de la base");
		}
		connexion.close();
		return data;
	}
	public static Reservation getResa(int ID){
		Reservation res=null;
		Client c;
		Emplacement e;
		CustomDate d1,d2;
		boolean r,cu;
		Connexion connexion = new Connexion("Database.db");
		connexion.connect();
		ResultSet resultSet = connexion.query("SELECT * FROM reservation WHERE ID="+ID);
		try{
        	e = EmplacementDB.getEmp((int)resultSet.getInt("numEmp"));
        	c = ClientDB.getClient((int)resultSet.getInt("numCli"));
        	d1 = new CustomDate((String)resultSet.getString("dateArr"));
        	d2 = new CustomDate((String)resultSet.getString("dateDep"));
        	r= (boolean)resultSet.getBoolean("reglement");
        	cu= (boolean)resultSet.getBoolean("encours");
        	res = new Reservation(ID,c,e,d1,d2,r,cu);
        }catch(SQLException error){
            error.printStackTrace();
        }
        connexion.close();
		return res;
	}

}