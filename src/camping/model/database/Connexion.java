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

/* La classe connexion sert à se connecter à la base de données.
 * Elle contient aussi toutes les méthodes nécessaires à l'ajout/modification des tables
 */
public class Connexion {
    private String DBPath = "Chemin aux base de donnée SQLite";
    private Connection connection = null;
    private Statement statement = null;

    public Connexion(String dBPath) {
        DBPath = dBPath;
    }

    //Cette méthode connecte la base de données
    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + DBPath);
            statement = connection.createStatement();
            //System.out.println("Connexion a " + DBPath + " avec succès");
        } catch (ClassNotFoundException notFoundException) {
            notFoundException.printStackTrace();
            System.out.println("Erreur de connection");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.out.println("Erreur de connection");
        }
    }

    //Cette méthode ferme la connection à la base de données.
    public void close() {
        try {
        	statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   /* Cette méthode permet d'ajouter un client en requête préparée
    * L'avantage des requêtes préparées par rapport aux simples requêtes
    * c'est qu'elles permettent d'ajouter une suite de mots
    * séparés par des espaces en un seul type(prénoms,nom,adresse,ville)
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

   /* Cette fonction permet d'éxécuter une requête SQL et d'en récupérer le résultat
    * pour pouvoir l'interpréter/afficher/décomposer en Java ensuite
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