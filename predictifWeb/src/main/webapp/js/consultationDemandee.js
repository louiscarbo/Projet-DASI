document.addEventListener('DOMContentLoaded', () => {
    const btnValider = document.getElementById('btnValider');
    const btnCommencer = document.getElementById('btnCommencer');
    const errorDiv = document.getElementById('error');
    let consultationId;

    // Charger les données de la consultation demandée
    fetch('ActionServlet?todo=consultationDemandee', {
        headers: {'Accept': 'application/json'}
    })
    .then(r => r.json())
    .then(data => {
        if (!data.success) {
            errorDiv.textContent = data.message || 'Aucune consultation.';
            btnValider.disabled = true;
            return;
        }
        // Médium
        document.getElementById('medNom').value = data.medium.nom;
        document.getElementById('medGenre').value = data.medium.genre;
        document.getElementById('medPresentation').value = data.medium.presentation;
        document.getElementById('medType').value = data.medium.type;

        // Client
        document.getElementById('clientNom').value = data.client.nom;
        document.getElementById('clientPrenom').value = data.client.prenom;
        document.getElementById('clientDate').value = data.client.dateNaissance;
        document.getElementById('clientTel').value = data.client.tel;

        // Profil astral
        document.getElementById('astroZodiaque').value = data.astro.zodiaque;
        document.getElementById('astroChinois').value = data.astro.sgnChinois;
        document.getElementById('astroCouleur').value = data.astro.couleur;
        document.getElementById('astroAnimal').value = data.astro.animal;

        // Historique
        const tbody = document.querySelector('#historiqueTable tbody');
        data.historique.forEach(item => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${item.date}</td>
                <td>${item.medium}</td>
                <td>${item.employe}</td>
              `
            ;
            tbody.appendChild(tr);
        });
        consultationId = data.id;
    })
    .catch(err => {
        console.error(err);
        errorDiv.textContent = 'Erreur de chargement.';
        btnValider.disabled = true;
    });

    // Bouton Accepter la consultation
    btnValider.addEventListener('click', () => {
        if (!consultationId) return;
        fetch(`ActionServlet?todo=accepterConsultation&id=${consultationId}`)
        .then(r => r.json())
        .then(data => {
            if (data.success) {
                btnValider.style.display = 'none';
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

    // Bouton Commencer la consultation
    btnCommencer.addEventListener('click', () => {
        if (!consultationId)
            return;
        fetch(`ActionServlet?todo=demarrerConsultation&id=${consultationId}`)
        .then(() => {
            window.location.href = `appelEnCours.html?id=${consultationId}`;
        })
        .catch(err => {
            console.error(err);
            errorDiv.textContent = 'Impossible de démarrer.';
        });
    });
});