// js/espaceEmploye.js

document.addEventListener('DOMContentLoaded', () => {
  const welcomeH2 = document.getElementById('welcome');
  const cardNext  = document.getElementById('cardNext');
  const btnNext   = document.getElementById('btnNext');
  const btnStats  = document.getElementById('btnStats');
  const errorDiv  = document.getElementById('error');

  // 1) Récupérer le nom de l'employé en session pour le welcome
  fetch('ActionServlet?todo=checkSession', { headers:{ 'Accept':'application/json' }})
    .then(r => r.ok ? r.json() : Promise.reject(r.status))
    .then(data => {
      if (data.connected && data.employeName) {
        welcomeH2.textContent = `Bienvenue, ${data.employeName} !`;
      } else {
        welcomeH2.textContent = `Bienvenue !`;
      }
    })
    .catch(err => console.error('Session:', err));

  // 2) Charger la prochaine consultation
  fetch('ActionServlet?todo=prochaineConsultation', {
    headers:{ 'Accept':'application/json' }
  })
    .then(r => r.ok ? r.json() : Promise.reject(r.status))
    .then(data => {
      if (data.success) {
        // consultation ok → bouton actif
        cardNext.style.opacity = '1';
        btnNext.disabled = false;
      } else {
        // pas de consultation → griser la carte et désactiver le bouton
        cardNext.style.opacity = '0.5';
        btnNext.disabled = true;
      }
    })
    .catch(err => {
      console.error('ProchaineConsultation:', err);
      errorDiv.textContent = 'Erreur lors du chargement des consultations.';
      cardNext.style.opacity = '0.5';
      btnNext.disabled = true;
    });

  // 3) Gestion des clics
  btnNext.addEventListener('click', () => {
    if (!btnNext.disabled) {
      window.location.href = 'consultationDemandee.html';
    }
  });
  btnStats.addEventListener('click', () => {
    window.location.href = 'statistiques.html';
  });
  
  // 4) Déconnexion employé
  btnLogoutEmp.addEventListener('click', () => {
    fetch('ActionServlet?todo=deconnecterEmploye', {
      headers: { 'Accept': 'application/json' }
    })
      .then(res => {
        if (!res.ok) throw new Error(res.status);
        return res.json();
      })
      .then(data => {
        if (data.success) {
          // redirection vers page d'accueil publique
          window.location.href = '/connexionEmploye.html';
        } else {
          errorDiv.textContent = data.message || 'Échec de la déconnexion.';
        }
      })
      .catch(err => {
        console.error(err);
        errorDiv.textContent = 'Erreur serveur lors de la déconnexion.';
      });
  });
});