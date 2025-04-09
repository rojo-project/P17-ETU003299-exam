<h1>Bienvenue </h1>
<%
    if (session != null && session.getAttribute("userId") != null) {
        Integer idUtilisateur = (Integer) session.getAttribute("userId");
%>
        <p>ID de l'utilisateur connecté : <%= idUtilisateur %></p>
<%
    } else {
        response.sendRedirect("index.html");
    }
%>
