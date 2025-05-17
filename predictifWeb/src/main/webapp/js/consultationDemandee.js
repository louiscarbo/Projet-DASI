// js/consultationDemandee.js
document.addEventListener('DOMContentLoaded', () => {
  const btnValider     = document.getElementById('btnValider');
  const btnCommencer   = document.getElementById('btnCommencer');
  const errorDiv       = document.getElementById('error');
  let consultationId;   // pour réutiliser plus tard

  // 1) Charger les données de la consultation demandée
  fetch('ActionServlet?todo=consultationDemandee', {
    headers: { 'Accept': 'application/json' }
  })
    .then(r => r.ok ? r.json() : Promise.reject(r.status))
    .then(data => {
      if (!data.success) {
        errorDiv.textContent = data.message || 'Aucune consultation.';
        btnValider.disabled   = true;
        return;
      }

      // Médium
      document.getElementById('medNom').value          = data.medium.nom;
      document.getElementById('medGenre').value        = data.medium.genre;
      document.getElementById('medPresentation').value = data.medium.presentation;

      // Client
      document.getElementById('clientNom').value       = data.client.nom;
      document.getElementById('clientPrenom').value    = data.client.prenom;
      document.getElementById('clientDate').value      = data.client.dateNaissance;
      document.getElementById('clientTel').value       = data.client.tel;

      // Profil astral
      document.getElementById('astroZodiaque').value   = data.astro.zodiaque;
      document.getElementById('astroChinois').value    = data.astro.sgnChinois;
      document.getElementById('astroCouleur').value    = data.astro.couleur;
      document.getElementById('astroAnimal').value     = data.astro.animal;

      // Historique
      const tbody = document.querySelector('#historiqueTable tbody');
      data.historique.forEach(item => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
          <td>${item.date}</td>
          <td>${item.medium}</td>
          <td>${item.employe}</td>
        `;
        tr.addEventListener('click', () => {
          window.location.href = `detailConsultation.html?id=${item.id}`;
        });
        tbody.appendChild(tr);
      });

      // mémoriser l'id pour valider/démarrer
      consultationId = data.id;
    })
    .catch(err => {
      console.error(err);
      errorDiv.textContent = 'Erreur de chargement.';
      btnValider.disabled = true;
    });

  // 2) Valider la consultation (accepter)
  btnValider.addEventListener('click', () => {
    if (!consultationId) return;
    fetch(`ActionServlet?todo=accepterConsultation&id=${consultationId}`)
      .then(r => r.ok ? r.json() : Promise.reject(r.status))
      .then(data => {
        if (data.success) {
          // passer au bouton "commencer"
          btnValider.style.display   = 'none';
          btnCommencer.style.display = 'inline-block';
        } else {
          errorDiv.textContent = data.message || 'Impossible d’accepter.';
        }
      })
      .catch(err => {
        console.error(err);
        errorDiv.textContent = 'Erreur serveur.';
      });
  });

  // 3) Commencer la consultation
  btnCommencer.addEventListener('click', () => {
    if (!consultationId) return;
    fetch(`ActionServlet?todo=demarrerConsultation&id=${consultationId}`)
      .then(() => {
        window.location.href = 'appelEncours.html';
      })
      .catch(err => {
        console.error(err);
        errorDiv.textContent = 'Impossible de démarrer.';
      });
  });
});