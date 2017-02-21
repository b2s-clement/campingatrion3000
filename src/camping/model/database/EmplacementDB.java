package camping.model.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import camping.model.Emplacement;

public class EmplacementDB {

	//Liste des attributs
	public static String NUM = "numero";
	public static String TYPE = "type";
	public static String OCC = "occupe";
	public static String NB = "nbPlaces";
	public static String PRIX = "prix";

	//MEthodes d'accès à la base de données 'Emplacement'

	public static int nbEmpl(){
		Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        int res=0;
        ResultSet resultSet = connexion.query("SELECT count() FROM emplacement");
        try{
        	res= resultSet.getInt("count()");
        }catch(SQLException e){
            e.printStackTrace();
        }
        connexion.close();
        return res;
	}
	public static int nbEmpl(boolean b){
		Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        int valueBase=0;
		if(b){
			valueBase=1;
		}
        int res=0;
        ResultSet resultSet = connexion.query("SELECT count() FROM emplacement WHERE occupe="+valueBase);
        try{
        	res= resultSet.getInt("count()");
        }catch(SQLException e){
            e.printStackTrace();
        }
        connexion.close();
        return res;
	}

	public static void ajouterNouveauEmplacement(Emplacement emp){
		Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        connexion.addEmplacement(emp);
        connexion.close();
	}

	public static void supprimerEmplacement(int numEmp){
		Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        connexion.queryUpdate("DELETE FROM emplacement WHERE numero='"+numEmp+"'");
        connexion.close();
	}

	public static void supprimerAllEmplacement(){
		Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        connexion.queryUpdate("DELETE FROM emplacement");
        connexion.close();
	}

	public static boolean isNumEmpTaken(int a){
		boolean res=false;
		Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        ResultSet resultSet = connexion.query("SELECT numero FROM emplacement");
        try{
			while(resultSet.next()&&!res){
				res=resultSet.getInt("numero")==a;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
        connexion.close();
		return res;
	}

	public static Emplacement getEmp(int numero){
		Emplacement res=null;
    	Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        String requet="SELECT * FROM emplacement WHERE numero='"+numero+"'";
        ResultSet resultSet = connexion.query(requet);
        try {
        	while (resultSet.next()) {
                res = new Emplacement(numero, resultSet.getString("type"), resultSet.getBoolean("occupe"), resultSet.getInt("nbPlaces"), resultSet.getDouble("prix"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connexion.close();
		return res;
	}

	public static void modifierEmplacementNumero(int remplacement,int numero){
    	Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        connexion.queryUpdate("UPDATE emplacement SET numero ="+remplacement+" WHERE numero="+numero);
    	connexion.close();
    }

	public static void modifierEmplacementType(String remplacement,int numero){
    	Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        connexion.queryUpdate("UPDATE emplacement SET type ='"+remplacement+"' WHERE numero="+numero);
    	connexion.close();
    }

	public static void modifierEmplacementNbPlaces(int remplacement,int numero){
    	Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        connexion.queryUpdate("UPDATE emplacement SET nbPlaces ="+remplacement+" WHERE numero="+numero);
    	connexion.close();
    }

	public static void modifierEmplacementPrix(double remplacement,int numero){
    	Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        connexion.queryUpdate("UPDATE emplacement SET prix ='"+remplacement+"' WHERE numero="+numero);
    	connexion.close();
    }

	public static void setEmplacementOccuped(Boolean value,int numero){
		Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        connexion.queryUpdate("UPDATE emplacement SET occupe ='"+value+"' WHERE numero="+numero);
    	connexion.close();
	}

	public static Object[][] getData(){
		Connexion connexion = new Connexion("Database.db");
		connexion.connect();
		ResultSet resultSet = connexion.query("SELECT * FROM emplacement");
		int x=nbEmpl();
		Object[][] data = new Object[x][5];
		try{
			int num,nbpl;
			double prix;
			boolean occ;
			String tp;
			while(resultSet.next()){
				num=(int)resultSet.getInt("numero");
				tp=(String)resultSet.getString("type");
				occ=(boolean)resultSet.getBoolean("occupe");
				nbpl=(int)resultSet.getInt("nbPlaces");
				prix=(double)resultSet.getDouble("prix");
				//Remplissage de l'object
				data[x-1][0]=num;
				data[x-1][1]=tp;
				if(occ){
					data[x-1][2]="occupé";
				}else{
					data[x-1][2]="libre";
				}
				data[x-1][3]=nbpl;
				data[x-1][4]=prix;
				x=x-1;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Erreur lors de l'affichage de la base");
		}
		connexion.close();
	    return data;
	}

	public static Object[][] getData(boolean b){
		Connexion connexion = new Connexion("Database.db");
		connexion.connect();
		int valueBase=0;
		if(b){
			valueBase=1;
		}
		ResultSet resultSet = connexion.query("SELECT * FROM emplacement WHERE occupe="+valueBase);
		int x=nbEmpl(b);
		Object[][] data = new Object[x][4];
		try{
			int num,nbpl;
			double prix;
			String tp;
			while(resultSet.next()){
				num=(int)resultSet.getInt("numero");
				tp=(String)resultSet.getString("type");
				nbpl=(int)resultSet.getInt("nbPlaces");
				prix=(double)resultSet.getDouble("prix");
				//Remplissage de l'object
				data[x-1][0]=num;
				data[x-1][1]=tp;
				data[x-1][2]=nbpl;
				data[x-1][3]=prix;
				x=x-1;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Erreur lors de l'affichage de la base");
		}
		connexion.close();
	    return data;
	}

	public static boolean isOccupe(int n){
		boolean res= false;
		Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        ResultSet resultSet = connexion.query("SELECT occupe FROM emplacement WHERE numero='"+n+"'");
        try{
        	while(resultSet.next()){
        		res= resultSet.getBoolean("occupe");
        	}
        }catch(SQLException e){
            e.printStackTrace();
        }
        connexion.close();
		return res;
	}

	public static void afficherBaseEmplacement(){
    	Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        ResultSet resultSet = connexion.query("SELECT * FROM emplacement");
        try {
        	while (resultSet.next()) {
                System.out.println("Emplacement "+resultSet.getInt("numero")+", "+resultSet.getString("type")+", "+resultSet.getBoolean("occupe")+", "+resultSet.getInt("nbPlaces")+", "+resultSet.getDouble("prix")+"€ ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connexion.close();
    }
}
