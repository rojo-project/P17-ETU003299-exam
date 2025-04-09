package main;

import utils.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Utilisateur extends BaseObject {
    private String name;
    private String email;
    private String mot_de_passe;

    public Utilisateur (){

    }

    public Utilisateur(String name,String email,String mot_de_passe){
        this.name = name;
        this.email = email;
        this.mot_de_passe = mot_de_passe;
    }


    public Utilisateur(int id,String name,String email,String mot_de_passe){
        super.setId(id);
        this.name = name;
        this.email = email;
        this.mot_de_passe = mot_de_passe;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getMdp(){
        return mot_de_passe;
    }

    public void setName(String nom) {
        this.name = nom;
    }

    public void setEmail(String e){
        this.email = e;
    }

    public void setMdp(String e){
        this.email = e;
    }

    @Override
    public String toString() {
        return "Utilisateur{id=" + id + ", Nom= " + name + ", Email='" + email + ",'Mot de passe='" + mot_de_passe + "'}";
    }

    @Override
    public void save() throws Exception {
        String request = "INSERT INTO Utilisateur(nom,email,mot_de_passe) VALUES (?,?,?)";
        try (Connection connection = DB.connect()) {
            try (PreparedStatement ps = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, mot_de_passe);
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
    public List<Utilisateur> findAll() throws Exception {
        List<Utilisateur> list = new ArrayList<Utilisateur>();
        String request = "SELECT * FROM Utilisateur";
        try (Connection connection = DB.connect()) {
            try (Statement s = connection.createStatement()) {
                try (ResultSet rs = s.executeQuery(request)) {
                    while (rs.next()) {
                        list.add(new Utilisateur(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("email"),
                            rs.getString("mot_de_passe"))
                        );
                    }
                }
            }
        }
        return list;
    }

    @Override
    public Utilisateur findById(int id) throws Exception {
        Utilisateur user = null;
        String request = "SELECT * FROM Utilisateur WHERE id=?";
        try (Connection connection = DB.connect()) {
            try (PreparedStatement ps = connection.prepareStatement(request)) {
                ps.setInt(1, id);  
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        user = new Utilisateur(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("email"),
                            rs.getString("mot_de_passe")
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
        String request = "UPDATE Utilisateur SET nom=?, email=? mot_de_passe=? WHERE id=?";
        try (Connection connection = DB.connect()) {
            try (PreparedStatement ps = connection.prepareStatement(request)) {
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, mot_de_passe);
                ps.setInt(3, id);
                ps.executeUpdate();
            }
        }
    }
    
    @Override
    public void delete() throws Exception {
        String request = "DELETE FROM Utilisateur WHERE id=?";
        try (Connection connection = DB.connect()) {
            try (PreparedStatement ps = connection.prepareStatement(request)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        }
    }
    
}