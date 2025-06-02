document.addEventListener('DOMContentLoaded', () => {
    const welcomeH2 = document.getElementById('welcome');
    const cardNext = document.getElementById('cardNext');
    const btnNext = document.getElementById('btnNext');
    const btnStats = document.getElementById('btnStats');
    const errorDiv = document.getElementById('error');

    // Charger la prochaine consultation
    fetch('ActionServlet?todo=prochaineConsultation', {
        headers: {'Accept': 'application/json'}
    })
    .then(r => r.json())
    .then(data => {
        if (data.success) {
            cardNext.style.opacity = '1';
            btnNext.disabled = false;
        } else {
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

    // Gestion des clics
    btnNext.addEventListener('click', () => {
        if (!btnNext.disabled) {
            window.location.href = 'consultationDemandee.html';
        }
    });
    btnStats.addEventListener('click', () => {
        window.location.href = 'statistiques.html';
    });

    // Déconnexion employés
    btnLogoutEmp.addEventListener('click', () => {
        fetch('ActionServlet?todo=deconnecterEmploye', {
            headers: {'Accept': 'application/json'}
        })
        .then(res => {
            if (!res.ok)
                throw new Error(res.status);
            return res.json();
        })
        .then(data => {
            if (data.success) {
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