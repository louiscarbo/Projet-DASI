document.addEventListener('DOMContentLoaded', () => {
  const errorDiv = document.getElementById('error');

  // 1) Inject the shared menu
  fetch('menu.html')
    .then(res => res.text())
    .then(html => {
      document.body.insertAdjacentHTML('afterbegin', html);

      const btnMenu    = document.getElementById('btnMenu');
      const sideMenu   = document.getElementById('sideMenu');
      const overlay    = document.getElementById('menuOverlay');
      const logoutLink = document.getElementById('logoutLink');

      // 2) Toggle .open on click — no display:block hacks
      btnMenu.addEventListener('click', () => {
        sideMenu.classList.add('open');
        overlay.classList.add('open');
      });

      overlay.addEventListener('click', () => {
        sideMenu.classList.remove('open');
        overlay.classList.remove('open');
      });

      // 3) Logout handler
      logoutLink.addEventListener('click', e => {
        e.preventDefault();
        fetch('ActionServlet?todo=deconnecter')
          .then(r => r.json())
          .then(() => window.location.href = '/')
          .catch(err => {
            console.error(err);
            errorDiv.textContent = 'Échec de la déconnexion.';
          });
      });

      // 4) Check session: hide menu button completely if not logged
      return fetch('ActionServlet?todo=checkSession', {
        headers: { 'Accept': 'application/json' }
      });
    })
    .then(res => {
      if (!res.ok) throw new Error(res.status);
      return res.json();
    })
    .then(data => {
      if (!data.connected) {
        document.getElementById('btnMenu').style.display = 'none';
        // no need to fiddle sideMenu/overlay here
      }
    })
    .catch(err => {
      console.error(err);
      errorDiv.textContent = 'Erreur session. Réessayez plus tard.';
    });
});