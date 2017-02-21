package camping.model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import camping.model.Client;
import camping.model.Emplacement;
import camping.model.Reservation;

import java.sql.ResultSet;
import java.sql.*;

/* La classe connexion sert � se connecter � la base de donn�es.
 * Elle contient aussi toutes les m�thodes n�cessaires � l'ajout/modification des tables
 */
public class Connexion {
    private String DBPath = "Chemin aux base de donn�e SQLite";
    private Connection connection = null;
    private Statement statement = null;

    public Connexion(String dBPath) {
        DBPath = dBPath;
    }

    //Cette m�thode connecte la base de donn�es
    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + DBPath);
            statement = connection.createStatement();
            //System.out.println("Connexion a " + DBPath + " avec succ�s");
        } catch (ClassNotFoundException notFoundException) {
            notFoundException.printStackTrace();
            System.out.println("Erreur de connection");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.out.println("Erreur de connection");
        }
    }

    //Cette m�thode ferme la connection � la base de donn�es.
    public void close() {
        try {
        	statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   /* Cette m�thode permet d'ajouter un client en requ�te pr�par�e
    * L'avantage des requ�tes pr�par�es par rapport aux simples requ�tes
    * c'est qu'elles permettent d'ajouter une suite de mots
    * s�par�s par des espaces en un seul type(pr�noms,nom,adresse,ville)
    */
    public void addClient(Client client) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO client VALUES(?,?,?,?,?,?,?,?,?,?)");
            preparedStatement.setInt(1, client.getIDClient());
            preparedStatement.setString(2, client.getGenre());
            preparedStatement.setString(3, client.getNom());
            preparedStatement.setString(4, client.getPrenom());
            preparedStatement.setString(5, client.getAdresse());
            preparedStatement.setString(6, client.getVille());
            preparedStatement.setString(7, client.getCodePostal());
            preparedStatement.setString(8, client.getTelephone());
            preparedStatement.setString(9, client.getDateNaissance());
            preparedStatement.setInt(10, client.getNbReservation());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addEmplacement(Emplacement emp) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO emplacement VALUES(?,?,?,?,?)");
            preparedStatement.setInt(1, emp.getNumeroEmplacement());
            preparedStatement.setString(2, emp.getType());
            preparedStatement.setBoolean(3, emp.isOccupe());
            preparedStatement.setInt(4, emp.getNbPlaces());
            preparedStatement.setDouble(5, emp.getPrixEmplacement());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addReservation(Reservation res){
    	 try {
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO reservation VALUES(?,?,?,?,?,?)");
             preparedStatement.setInt(1, res.getID());
             preparedStatement.setInt(2, res.getEmp().getNumeroEmplacement());
             preparedStatement.setInt(3, res.getCli().getIDClient());
             preparedStatement.setString(4, res.getDateArr().toString());
             preparedStatement.setString(5, res.getDateDep().toString());
             preparedStatement.setBoolean(5, res.getReglement());
             preparedStatement.executeUpdate();
         } catch (SQLException e) {
             e.printStackTrace();
         }
    }

   /* Cette fonction permet d'�x�cuter une requ�te SQL et d'en r�cup�rer le r�sultat
    * pour pouvoir l'interpr�ter/afficher/d�composer en Java ensuite
    */
    public ResultSet query(String requet) {
        ResultSet resultat = null;
        try {
            resultat = statement.executeQuery(requet);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur dans la requete : " + requet);
        }
        return resultat;

    }

    public void queryUpdate(String requet) {
        try {
            statement.executeQuery(requet);
        }catch ( SQLException ignore ) {
        }

    }
}