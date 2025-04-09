<body>
    <link rel="stylesheet" href="style.css">
    <form name="form" method="post" action="formCredit">
    <h1>Enregistrement de credit</h1>
<p>
<input type="hidden" name="action" value="ajout_credit">
Libelle : <input type="text" name="libelle"> 
Montant : <input type="number" name="montant" id="montant">
</p>
<p>
<input type="submit" name="Submit" rows="5" value="AJOUTER">
<br>
<a href="formCredit">AJOUTER DEPENSE</a>
<a href="dash">VOIR LE DASHBOARD</a>
</p>
</form>
</body>