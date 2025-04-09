package milay;
import main.Credit;
import main.Depense;

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

public class CreditServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        
        String libelle = request.getParameter("libelle");
        String m = request.getParameter("montant");

        int montant = Integer.parseInt(m);
        try {
            if("ajout_credit".equals(action)) {
                Credit nouveau = new Credit(libelle,montant);
                nouveau.save(); 

                request.getRequestDispatcher("pageCredit.jsp").forward(request, response); // redirection vers la page de login
            } 
            
            else {
                response.getWriter().println("Action inconnue !");
            }

        } catch (Exception e) {
            handleError(request, response, e);
        }

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Credit credit = new Credit();
        try {
            List<Credit> listCred = credit.findAll();
            List<Depense> listDep = new Depense().findAll();

            request.setAttribute("depense", listDep);
            request.setAttribute("credit", listCred);

            request.getRequestDispatcher("LigneDepense.jsp").forward(request, response);
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
