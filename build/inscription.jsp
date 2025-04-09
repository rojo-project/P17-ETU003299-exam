<body>
<link rel="stylesheet" href="style.css">
<form name="form" method="post" action="formulaireUser">

<p>INSCRIPTION</p>
    <input type="hidden" name="action" value="register"> 
    <p>
        Nom : <input type="text" name="name">
        Email : <input type="text" name="email" id="email">
        Mot de passe : <input type="password" name="mdp" id="mdp">
    </p>
    <p>
        <input type="submit" name="Submit" value="S'INSCRIRE">
        <a href="index.html">RETOUR</a>
    </p>
</form>
</body>