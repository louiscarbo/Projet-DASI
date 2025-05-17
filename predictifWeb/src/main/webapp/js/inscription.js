// js/inscription.js

document.addEventListener('DOMContentLoaded', () => {
  const nomInput           = document.getElementById('nom');
  const prenomInput        = document.getElementById('prenom');
  const dateInput          = document.getElementById('dateNaissance');
  const adresseInput       = document.getElementById('adresse');
  const codePostalInput    = document.getElementById('codePostal');
  const telInput           = document.getElementById('telephone');
  const mailInput          = document.getElementById('mail');
  const mdpInput           = document.getElementById('mdp');
  const genreSelect        = document.getElementById('genre');
  const btnInscrire        = document.getElementById('btnInscrire');
  const errDiv             = document.getElementById('error');

  btnInscrire.addEventListener('click', async () => {
    errDiv.textContent = '';

    // 1. Lire et valider basiquement
    const nom        = nomInput.value.trim();
    const prenom     = prenomInput.value.trim();
    const dateNaiss  = dateInput.value.trim();
    const adresse    = adresseInput.value.trim();
    const codePostal = codePostalInput.value.trim();
    const telephone  = telInput.value.trim();
    const mail       = mailInput.value.trim();
    const mdp        = mdpInput.value;
    const genre      = genreSelect.value;

    if (!nom || !prenom || !dateNaiss || !adresse ||
        !codePostal || !telephone || !mail || !mdp || !genre) {
      errDiv.textContent = 'Veuillez remplir tous les champs.';
      return;
    }

    // 2. Construire les paramètres
    const params = new URLSearchParams({
      todo:           'inscrireClient',
      nom:            nom,
      prenom:         prenom,
      dateNaissance:  dateNaiss,
      adresse:        adresse,
      codePostal:     codePostal,
      telephone:      telephone,
      mail:           mail,
      mdp:            mdp,
      genre:          genre
    });

    try {
      // 3. Envoi de la requête
      const response = await fetch(`ActionServlet?${params.toString()}`, {
        method: 'GET',
        headers: {
          'Accept': 'application/json'
        }
      });
      if (!response.ok) {
        throw new Error(`Statut HTTP ${response.status}`);
      }

      // 4. Lire le JSON
      const data = await response.json();

      // 5. Traitement du retour
      if (data.success) {
        // Inscription OK → renvoyer vers la connexion
        window.location.href = 'espaceClient.html';
      } else {
        errDiv.textContent = data.message || 'Erreur lors de l’inscription.';
      }
    } catch (err) {
      console.error(err);
      errDiv.textContent = 'Impossible de contacter le serveur, réessayez.';
    }
  });
});