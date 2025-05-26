// js/menu.js
document.addEventListener('DOMContentLoaded', () => {
  const errorDiv = document.getElementById('error');

  // 1) Injecter le menu commun
  fetch('menu.html')
    .then(res => res.text())
    .then(html => {
      document.body.insertAdjacentHTML('afterbegin', html);

      // 2) Paramétrer le burger + logout
      const btnMenu    = document.getElementById('btnMenu');
      const sideMenu   = document.getElementById('sideMenu');
      const logoutLink = document.getElementById('logoutLink');
      btnMenu.style.display = 'inline-block';
      
      /*btnMenu.addEventListener('click', () => {
        sideMenu.style.display =
          sideMenu.style.display === 'block' ? 'none' : 'block';
      });
      */
     
     const overlay = document.getElementById('menuOverlay');
     
     function openMenu (){
         sideMenu.style.display = 'block';
         overlay.style.display = 'block';
     }
     
    function closeMenu (){
         sideMenu.style.display = 'none';
         overlay.style.display = 'none';
     }
     
    btnMenu.addEventListener('click', openMenu);
    overlay.addEventListener('click', closeMenu);

      logoutLink.addEventListener('click', e => {
        e.preventDefault();
        fetch('ActionServlet?todo=deconnecter')
          .then(res => res.json())
          .then(() => window.location.href = '/')
          .catch(err => {
            console.error(err);
            errorDiv.textContent = 'Échec de la déconnexion.';
          });
      });

      // 3) Vérifier la session pour masquer totalement le menu si non connecté
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
        document.getElementById('sideMenu').style.display = 'none';
      }
    })
    .catch(err => {
      console.error(err);
      errorDiv.textContent = 'Erreur session. Réessayez plus tard.';
    });
});