document.addEventListener('DOMContentLoaded', () => {
  const btnClose   = document.getElementById('btnClose');
  const predA      = document.getElementById('predAmour');
  const predS      = document.getElementById('predSante');
  const predT      = document.getElementById('predTravail');
  const errorDiv   = document.getElementById('error');

  // 1) Extraire l'ID de la consultation depuis l'URL
  const params = new URLSearchParams(window.location.search);
  const id = params.get('id');
  if (!id) {
    errorDiv.textContent = 'ID de consultation manquant';
    return;
  }

  // 2) Fermer → revenir à la liste
  btnClose.addEventListener('click', () => {
    window.location.href = 'historique.html';
  });

  // 3) Appel AJAX pour charger les prédictions
  fetch(`ActionServlet?todo=consulterDetailsConsultation&id=${id}`, {
    headers: {'Accept':'application/json'}
  })
    .then(res => res.ok ? res.json() : Promise.reject(res.status))
    .then(json => {
      if (!json.success) {
        throw new Error(json.message || 'Aucune prédiction trouvée.');
      }
      predA.textContent = json.amour || '';
      predS.textContent = json.sante || '';
      predT.textContent = json.travail || '';
    })
    .catch(err => {
      console.error(err);
      errorDiv.textContent = err.message;
    });
});