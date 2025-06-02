document.addEventListener('DOMContentLoaded', () => {
    const mailInput = document.getElementById('mail');
    const mdpInput = document.getElementById('mdp');
    const btn = document.getElementById('btnValider');
    const errDiv = document.getElementById('error');

    btn.addEventListener('click', () => {
        errDiv.textContent = '';

        const mail = mailInput.value.trim();
        const mdp = mdpInput.value;

        if (!mail || !mdp) {
            errDiv.textContent = 'Veuillez remplir tous les champs.';
            return;
        }

        const params = new URLSearchParams({
            todo: 'connecterClient',
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
                window.location.href = '/';
            } else {
                errDiv.textContent = data.message || 'Identifiants invalides.';
            }
        })
        .catch(err => {
            console.error(err);
            errDiv.textContent = 'Erreur de connexion. Veuillez réessayer plus tard.';
        });
    });
});