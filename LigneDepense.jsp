<%@ page import="main.Credit" %>
<%@ page import="main.Depense" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<link rel="stylesheet" href="style.css">
<body>



<form name="form" method="post" action="formDepense">
<% if (request.getAttribute("successMessage") != null) { %>
    <div style="color: green;"><%= request.getAttribute("successMessage") %></div>
<% } %>
<% if (request.getAttribute("errorMessage") != null) { %>
    <div style="color: red;"><%= request.getAttribute("errorMessage") %></div>
<% } %>

<h1>Formulaire Depense</h1>
<p>
<input type="hidden" name="action" value="ajout_depense">
Nom : <input type="text" name="nom">
Montant : <input type="number" name="montant">
</p>
<p>
<select name="credit" id="credit" class="form-select" required>
                <%
                List<Credit> credit = (List<Credit>) request.getAttribute("credit");
                if (credit == null) {
                    out.println("L'attribut 'credit' est null !");
                } else {
                    out.println("Nombre de departements recuperes : " + credit.size());
                }
            
                if (credit != null && !credit.isEmpty()) {
                    for (Credit cred : credit) { %>
                    <option value="<%= cred.getId() %>" >
                        <%= cred.getLibelle() != null ? cred.getLibelle() : "Nom non disponible" %>
                    </option>
                        
                    <% }
                } else {
                    out.println("Aucun credit disponible.");
                }
            
            %>

            </select>
</p>
<h3>Liste des Depenses</h3>

<ul>
    <%
    List<Depense> dep = (List<Depense>) request.getAttribute("depense");

    if (dep != null && !dep.isEmpty()) {
        for (Depense depense : dep) {
    %>
        <li>
            <%= depense.getNomDepense() != null ? depense.getNomDepense() : "Nom non disponible" %>
        </li>
    <%
        }
    } else {
        out.println("<p>Aucun depense disponible.</p>");
    }
    %>
</ul>

<p>
<input type="submit" name="Submit" rows="5" value="Soumettre">
</p>
<a href="dash">VOIR LE DASHBOARD</a>
<a href="pageCredit.jsp">RETOUR</a>
</form>
</body>
