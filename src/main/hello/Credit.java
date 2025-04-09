package main;

import utils.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Credit extends BaseObject {
    private String libelle;
    private int montant ;

    public Credit (){

    }

    public Credit(String libelle,int montant){
        this.libelle = libelle;
        this.montant = montant;
    }

    public Credit(int id,String libelle,int montant){
        super.setId(id);
        this.libelle = libelle;
        this.montant = montant;
    }

    public String getLibelle(){
        return libelle;
    }

    public int getMontant(){
        return montant;
    }

    public void setLibelle(String l) {
        this.libelle = l;
    }

    public void setMontant(int m){
        this.montant = m;
    }


    public int getTotalDepense() throws Exception {
        int total_depense = 0;
        String request = "SELECT * FROM Depense WHERE id_credit=?";

        try (Connection connection = DB.connect()) {
            try (PreparedStatement ps = connection.prepareStatement(request)) {
                ps.setInt(1, id);  
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        total_depense += rs.getInt("montant");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Une erreur SQL est survenue : " + e.getMessage());
            throw e;  
        }
        
        return total_depense;
    }

    public int getReste() throws Exception{
        int total_depense = this.getTotalDepense();

        int reste = this.montant - total_depense;
        return reste;
    }



    @Override
    public String toString() {
        return "Credit{id=" + id + ", Libelle= " + libelle + ", Montant='" + montant + "}";
    }


    @Override
    public void save() throws Exception {
        String request = "INSERT INTO Credit(libelle,montant) VALUES (?,?)";
        try (Connection connection = DB.connect()) {
            try (PreparedStatement ps = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, libelle);
                ps.setInt(2, montant);
                ps.executeUpdate();
                
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        this.id = rs.getInt(1);
                    }
                }
            }
        }
    }
    
    @Override
    public List<Credit> findAll() throws Exception {
        List<Credit> list = new ArrayList<Credit>();
        String request = "SELECT * FROM Credit";
        try (Connection connection = DB.connect()) {
            try (Statement s = connection.createStatement()) {
                try (ResultSet rs = s.executeQuery(request)) {
                    while (rs.next()) {
                        list.add(new Credit(
                            rs.getInt("id"),
                            rs.getString("libelle"),
                            rs.getInt("montant"))
                        );
                    }
                }
            }
        }
        return list;
    }

    @Override
    public Credit findById(int id) throws Exception {
        Credit user = null;
        String request = "SELECT * FROM Credit WHERE id=?";
        try (Connection connection = DB.connect()) {
            try (PreparedStatement ps = connection.prepareStatement(request)) {
                ps.setInt(1, id);  
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        user = new Credit(rs.getInt("id"),rs.getString("libelle"),rs.getInt("montant"));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Une erreur SQL est survenue : " + e.getMessage());
            throw e;  
        }
        return user;
    }
    
    @Override
    public void update() throws Exception {
        String request = "UPDATE Credit SET libelle=?, montant=? WHERE id=?";
        try (Connection connection = DB.connect()) {
            try (PreparedStatement ps = connection.prepareStatement(request)) {
                ps.setString(1, libelle);
                ps.setInt(2, montant);
                ps.setInt(3, id);
                ps.executeUpdate();
            }
        }
    }
    
    @Override
    public void delete() throws Exception {
        String request = "DELETE FROM Credit WHERE id=?";
        try (Connection connection = DB.connect()) {
            try (PreparedStatement ps = connection.prepareStatement(request)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        }
    }
    
}