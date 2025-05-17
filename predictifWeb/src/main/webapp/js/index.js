document.addEventListener('DOMContentLoaded', () => {
  const btnMenu    = document.getElementById('btnMenu');
  const authLinks  = document.getElementById('authLinks');
  const sideMenu   = document.getElementById('sideMenu');
  const logoutLink = document.getElementById('logoutLink');
  const errorDiv   = document.getElementById('error');

  // 1) Vérifier si l’utilisateur est connecté
  fetch('ActionServlet?todo=checkSession', {
    method: 'GET',
    headers: { 'Accept': 'application/json' }
  })
  .then(res => {
    if (!res.ok) throw new Error(`HTTP ${res.status}`);
    return res.json();
  })
  .then(data => {
    if (data.connected) {
      // Afficher le bouton Menu
      btnMenu.style.display = 'inline-block';
      authLinks.style.display = 'none';
    } else {
      // Laisser afficher Se connecter / S'inscrire
      authLinks.style.display = 'inline-block';
    }
  })
  .catch(err => {
    console.error(err);
    errorDiv.textContent = 'Erreur session. Réessayez plus tard.';
  });

  // 2) Ouvrir / fermer le menu latéral
  btnMenu.addEventListener('click', () => {
    sideMenu.style.display =
      sideMenu.style.display === 'none' ? 'block' : 'none';
  });

  // 3) Déconnexion : appel au servlet puis redirection
  logoutLink.addEventListener('click', (e) => {
    e.preventDefault();
    fetch('ActionServlet?todo=deconnecter', { method: 'GET' })
      .then(() => {
        window.location.href = '/predictifWeb';
      })
      .catch(err => {
        console.error(err);
        errorDiv.textContent = 'Échec de la déconnexion.';
      });
  });
});