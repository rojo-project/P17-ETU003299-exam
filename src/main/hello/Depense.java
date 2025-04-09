package main;

import utils.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Depense extends BaseObject {
    private int id_credit;
    private String nom_depense;
    private int montant ;

    public Depense (){

    }

    public Depense(int id_credit,String nom_depense,int montant){
        this.id_credit = id_credit;
        this.nom_depense = nom_depense;
        this.montant = montant;
    }

    public Depense(int id,int id_credit,String nom_depense,int montant){
        super.setId(id);
        this.id_credit = id_credit;
        this.nom_depense = nom_depense;
        this.montant = montant;
    }

    public int getId_credit(){
        return id_credit;
    }

    public String getNomDepense(){
        return nom_depense;
    }

    public int getMontant(){
        return montant;
    }

    public void setIdCredit(int i){
        this.id_credit = i;
    }

    public void setNomDepense(String l) {
        this.nom_depense = l;
    }

    public void setMontant(int m){
        this.montant = m;
    }


    @Override
    public String toString() {
        return "Depense{id=" + id + ", Nom Depense= " + nom_depense + ", Montant='" + montant + "}";
    }

    @Override
    public void save() throws Exception {
        String request = "INSERT INTO Depense(id_credit,nom_depense,montant) VALUES (?,?,?)";
        try (Connection connection = DB.connect()) {
            try (PreparedStatement ps = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, id_credit);
                ps.setString(2, nom_depense);
                ps.setInt(3, montant);
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
    public List<Depense> findAll() throws Exception {
        List<Depense> list = new ArrayList<Depense>();
        String request = "SELECT * FROM Depense";
        try (Connection connection = DB.connect()) {
            try (Statement s = connection.createStatement()) {
                try (ResultSet rs = s.executeQuery(request)) {
                    while (rs.next()) {
                        list.add(new Depense(
                            rs.getInt("id"),
                            rs.getInt("id_credit"),
                            rs.getString("nom_depense"),
                            rs.getInt("montant"))
                        );
                    }
                }
            }
        }
        return list;
    }

    @Override
    public Depense findById(int id) throws Exception {
        Depense user = null;
        String request = "SELECT * FROM Depense WHERE id=?";
        try (Connection connection = DB.connect()) {
            try (PreparedStatement ps = connection.prepareStatement(request)) {
                ps.setInt(1, id);  
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        user = new Depense(
                            rs.getInt("id"),
                            rs.getInt("id_credit"),
                            rs.getString("nom_depense"),
                            rs.getInt("montant")
                        );
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
        String request = "UPDATE Depense SET id_credit=? ,nom_depense=? , montant=? WHERE id=?";
        try (Connection connection = DB.connect()) {
            try (PreparedStatement ps = connection.prepareStatement(request)) {
                ps.setInt(1, id_credit);
                ps.setString(2, nom_depense);
                ps.setInt(3, montant);
                ps.setInt(4, id);
                ps.executeUpdate();
            }
        }
    }
    
    @Override
    public void delete() throws Exception {
        String request = "DELETE FROM Depense WHERE id=?";
        try (Connection connection = DB.connect()) {
            try (PreparedStatement ps = connection.prepareStatement(request)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        }
    }
    
}