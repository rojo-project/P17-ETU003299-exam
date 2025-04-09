<%@ page import="main.Credit" %>
<%@ page import="main.Depense" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<html>
<head>
<link rel="stylesheet" href="style.css">
    <title>Liste des</title>
</head>
<body>
<h1 style="">Tableau de Bord</h1>

    <%
        List<Credit> credit = (List<Credit>) request.getAttribute("credit");
    %>

    <table border="1">
        <thead>
            <tr>
                <th>Ligne de Credit</th>
                <th>Montant Credit</th>
                <th>Total montant Depense</th>
                <th>Reste</th>
            </tr>
        </thead>
        <tbody>
            <%
                for (Credit cred : credit) {
            %>
                <tr>
                    <td><%= cred.getLibelle() %></td>
                    <td><%= cred.getMontant() %></td>
                    <td><%= cred.getTotalDepense() %></td>
                    <td><%= cred.getReste() %></td>
                </tr>
            <%
                }
            %>
        </tbody>
    </table>
    <a href="formCredit">AJOUTER DEPENSE</a>
    <a href="pageCredit.jsp">RETOUR</a>
</body>
</html>
