document.addEventListener('DOMContentLoaded', () => {
  const mailInput = document.getElementById('mail');
  const mdpInput  = document.getElementById('mdp');
  const btn       = document.getElementById('btnValider');
  const errDiv    = document.getElementById('error');

  btn.addEventListener('click', async () => {
    // 1. Lecture et validation basique
    const mail = mailInput.value.trim(); //enlève whitespaces
    const mdp  = mdpInput.value;
    errDiv.textContent = '';
    if (!mail || !mdp) {
      errDiv.textContent = 'Veuillez remplir tous les champs.';
      return;
    }

    // 2. Construction de l’URL (GET) ou préparation du POST
    const params = new URLSearchParams({
      todo: 'connecterClient',
      mail: mail,
      mdp:  mdp
    });

    try {
      // 3. Envoi de la requête
      const response = await fetch(`ActionServlet?${params.toString()}`, {
        method: 'GET'
      });
      
      if (!response.ok) {
        throw new Error(`Statut HTTP ${response.status}`);
      }

      // 4. Lecture du JSON renvoyé par la servlet
      const data = await response.json();

      // 5. Traitement selon le résultat
      if (data.success) {
        // identifiants corrects → accès à l’espace client
        window.location.href = 'espaceClient.html';
      } else {
        // échec de connexion → affichage du message d’erreur
        errDiv.textContent = data.message || 'Identifiants invalides.';
      }

    } catch (err) {
      // erreur réseau ou parsing JSON
      console.error(err);
      errDiv.textContent = 'Erreur de connexion. Veuillez réessayer plus tard.';
    }
  });
});