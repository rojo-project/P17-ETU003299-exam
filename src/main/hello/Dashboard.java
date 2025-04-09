package milay;
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

public class Dashboard extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Credit credit = new Credit();
        try {
            List<Credit> list = credit.findAll();
            
            request.setAttribute("credit", list);
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
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
