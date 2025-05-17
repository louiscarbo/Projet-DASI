document.addEventListener('DOMContentLoaded', () => {
  const btnMenu   = document.getElementById('btnMenu');
  const authLinks = document.getElementById('authLinks');
  const errorDiv  = document.getElementById('error');

  // Par défaut on affiche Se connecter / S'inscrire, cache Menu
  btnMenu.style.display = 'none';
  authLinks.style.display = 'inline-block';

  // Check session
  fetch('ActionServlet?todo=checkSession', {
    headers: { 'Accept': 'application/json' }
  })
  .then(res => {
    if (!res.ok) throw new Error(res.status);
    return res.json();
  })
  .then(data => {
    if (data.connected) {
      // utilisateur connecté → cacher liens auth, montrer burger
      btnMenu.style.display   = 'inline-block';
      authLinks.style.display = 'none';
    }
  })
  .catch(err => {
    console.error(err);
    errorDiv.textContent = 'Erreur session. Réessayez plus tard.';
  });
});