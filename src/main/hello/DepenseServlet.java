package milay;
import main.Depense;
import main.Credit;

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

public class DepenseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        
        String nom = request.getParameter("nom");
        String credit = request.getParameter("credit");
        String m = request.getParameter("montant");

        int montant = Integer.parseInt(m);
        int id_credit = (int)Integer.parseInt(credit);

        

        try {
            if("ajout_depense".equals(action)) {
                Credit c = new Credit().findById(id_credit);
                if (c.getReste() >= montant) {
                    Depense nouveau = new Depense(id_credit, nom, montant);
                    nouveau.save(); 
                    request.setAttribute("successMessage", "Dépense insérée avec succès !");
                } else {
                    request.setAttribute("errorMessage", "Montant insuffisant dans le Crédit sélectionné.");
                }

                List<Depense> list = new Depense().findAll();
                List<Credit> listCredit = new Credit().findAll();

                request.setAttribute("depense", list);
                request.setAttribute("credit", listCredit);

                request.getRequestDispatcher("LigneDepense.jsp").forward(request, response); // redirection vers la page de login

            } 
            
            else {
                response.getWriter().println("Action inconnue !");
            }

        } catch (Exception e) {
            handleError(request, response, e);
        }

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Depense credit = new Depense();
        try {
            List<Depense> list = credit.findAll();
            
            System.out.println("Nombre de Depense trouvés : " + list.size()); // Log
            request.setAttribute("credit", list);
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
