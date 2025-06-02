document.addEventListener('DOMContentLoaded', function () {
    const mailInput = document.getElementById('mail');
    const mdpInput = document.getElementById('mdp');
    const btn = document.getElementById('btnValider');
    const errorDiv = document.getElementById('error');

    btn.addEventListener('click', function () {
        errorDiv.textContent = '';
        const mail = mailInput.value.trim();
        const mdp = mdpInput.value;

        if (!mail || !mdp) {
            errorDiv.textContent = 'Veuillez remplir tous les champs.';
            return;
        }

        const params = new URLSearchParams({
            todo: 'connecterEmploye',
            mail: mail,
            mdp: mdp
        });

        fetch('ActionServlet?' + params.toString(), {
            method: 'GET',
            headers: {
                'Accept': 'application/json'
            }
        })
        .then(r => r.json())
        .then(data => {
            if (data.success) {
                window.location.href = 'espaceEmploye.html';
            } else {
                errorDiv.textContent = data.message || 'Identifiants incorrects.';
                mailInput.value = '';
                mdpInput.value = '';
                mailInput.focus();
            }
        })
        .catch(error => {
            console.error(error);
            errorDiv.textContent = 'Erreur serveur. Réessayez plus tard.';
        });
    });
});