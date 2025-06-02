document.addEventListener('DOMContentLoaded', () => {
    const authLinks = document.getElementById('authLinks');
    const errorDiv = document.getElementById('error');

    // Par défaut on affiche Se connecter / S'inscrire
    authLinks.style.display = 'inline-block';

    fetch('ActionServlet?todo=checkSession', {
        headers: {'Accept': 'application/json'}
    })
    .then(res => {
        if (!res.ok)
            throw new Error(res.status);
        return res.json();
    })
    .then(data => {
        if (data.connected) {
            authLinks.style.display = 'none';
        }
    })
    .catch(err => {
        console.error(err);
        errorDiv.textContent = 'Erreur session. Réessayez plus tard.';
    });
});