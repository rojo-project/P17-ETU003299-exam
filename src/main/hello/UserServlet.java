package milay;
import main.Utilisateur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        
        String nom = request.getParameter("name");
        String email = request.getParameter("email");
        String mot_de_passe = request.getParameter("mdp");

        try {
            if ("login".equals(action)) {
                // Traitement de connexion
                Utilisateur user = new Utilisateur();
                List<Utilisateur> allUsers = user.findAll();

                boolean isUser = false;
                int userId = -1;

                for (Utilisateur u : allUsers) {
                    if (nom.equals(u.getName()) && email.equals(u.getEmail()) && mot_de_passe.equals(u.getMdp())) {
                        isUser = true;
                        userId = u.getId();
                        break;
                    }
                }

                if (isUser) {
                    HttpSession session = request.getSession(true);
                    session.setAttribute("userId", userId);
                    request.getRequestDispatcher("pageCredit.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("index.html").forward(request, response);
                }

            } else if ("register".equals(action)) {
                Utilisateur nouveau = new Utilisateur(nom,email,mot_de_passe);
                nouveau.save(); 

                request.getRequestDispatcher("index.html").forward(request, response); // redirection vers la page de login
            } else {
                response.getWriter().println("Action inconnue !");
            }

        } catch (Exception e) {
            handleError(request, response, e);
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utilisateur user = new Utilisateur();
        try {
            List<Utilisateur> list = user.findAll();
            
            System.out.println("Nombre de départements trouvés : " + list.size()); // Log
            request.setAttribute("departement", list);
            request.getRequestDispatcher("form_emp.jsp").forward(request, response);
        } catch (Exception e) {
            handleError(request, response, e);
        }
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, Exception e) throws ServletException, IOException {
        e.printStackTrace(); // Affiche l'erreur dans les logs du serveur
        request.setAttribute("errorMessage", e.getMessage());
        request.getRequestDispatcher("error.jsp").forward(request, response);
    }
}
