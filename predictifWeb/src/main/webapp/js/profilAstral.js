document.addEventListener('DOMContentLoaded', () => {
    const errorDiv = document.getElementById('error');

    // 1. Charger les données du profil
    fetch('ActionServlet?todo=profilAstral', {
        method: 'GET',
        headers: {'Accept': 'application/json'}
    })
    .then(res => {
        if (!res.ok)
            throw new Error(`HTTP ${res.status}`);
        return res.json();
    })
    .then(data => {
        if (data.success) {
            document.getElementById('zodiaqueValue').textContent = data.zodiaque;
            document.getElementById('sgnChinoisValue').textContent = data.sgnChinois;
            document.getElementById('animalValue').textContent = data.animal;
            document.getElementById('couleurValue').textContent = data.couleur;
        } else {
            errorDiv.textContent = data.message || 'Profil introuvable.';
        }
    })
    .catch(err => {
        console.error(err);
        errorDiv.textContent = 'Erreur lors du chargement du profil.';
    });
});