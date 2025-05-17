// js/profilAstral.js

document.addEventListener('DOMContentLoaded', () => {
  const errorDiv = document.getElementById('error');
  // 1) Charger les données du profil
  fetch('ActionServlet?todo=profilAstral', {
    method: 'GET',
    headers: { 'Accept': 'application/json' }
  })
    .then(res => {
      if (!res.ok) throw new Error(`HTTP ${res.status}`);
      return res.json();
    })
    .then(data => {
      if (data.success) {
        document.getElementById('zodiaqueValue').textContent  = data.zodiaque;
        document.getElementById('sgnChinoisValue').textContent = data.sgnChinois;
        document.getElementById('animalValue').textContent     = data.animal;
        document.getElementById('couleurValue').textContent    = data.couleur;
      } else {
        errorDiv.textContent = data.message || 'Profil introuvable.';
      }
    })
    .catch(err => {
      console.error(err);
      errorDiv.textContent = 'Erreur lors du chargement du profil.';
    });

  // 2) Le menu (ouverture / fermeture) et déconnexion
  const btnMenu    = document.getElementById('btnMenu');
  const sideMenu   = document.getElementById('sideMenu');
  const logoutLink = document.getElementById('logoutLink');

  // Après que menu.html ait été injecté...
  document.body.addEventListener('click', () => {
    // Si le menu existe, on peut l’afficher
    if (sideMenu) {
      // Affiche le bouton burger
      btnMenu.style.display = 'inline-block';
      btnMenu.addEventListener('click', () => {
        sideMenu.style.display =
          sideMenu.style.display === 'block' ? 'none' : 'block';
      });
      logoutLink.addEventListener('click', e => {
        e.preventDefault();
        fetch('ActionServlet?todo=deconnecter', { method: 'GET' })
          .then(() => window.location.href = '/predictifWeb')
          .catch(() => errorDiv.textContent = 'Échec de la déconnexion.');
      });
    }
  });
});