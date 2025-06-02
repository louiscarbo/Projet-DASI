document.addEventListener('DOMContentLoaded', function () {
    const nomInput = document.getElementById('nom');
    const prenomInput = document.getElementById('prenom');
    const dateInput = document.getElementById('dateNaissance');
    const adresseInput = document.getElementById('adresse');
    const codePostalInput = document.getElementById('codePostal');
    const telInput = document.getElementById('telephone');
    const mailInput = document.getElementById('mail');
    const mdpInput = document.getElementById('mdp');
    const genreSelect = document.getElementById('genre');
    const btnInscrire = document.getElementById('btnInscrire');
    const errDiv = document.getElementById('error');

    btnInscrire.addEventListener('click', function () {
        errDiv.textContent = '';

        const nom = nomInput.value.trim();
        const prenom = prenomInput.value.trim();
        const dateNaiss = dateInput.value.trim();
        const adresse = adresseInput.value.trim();
        const codePostal = codePostalInput.value.trim();
        const telephone = telInput.value.trim();
        const mail = mailInput.value.trim();
        const mdp = mdpInput.value;
        const genre = genreSelect.value;

        if (!nom || !prenom || !dateNaiss || !adresse ||
                !codePostal || !telephone || !mail || !mdp || !genre) {
            errDiv.textContent = 'Veuillez remplir tous les champs.';
            return;
        }

        const params = new URLSearchParams({
            todo: 'inscrireClient',
            nom: nom,
            prenom: prenom,
            dateNaissance: dateNaiss,
            adresse: adresse,
            codePostal: codePostal,
            telephone: telephone,
            mail: mail,
            mdp: mdp,
            genre: genre
        });

        fetch('ActionServlet?' + params.toString(), {
            method: 'GET',
            headers: {
                'Accept': 'application/json'
            }
        })
        .then(function (response) {
            if (!response.ok) {
                throw new Error('Statut HTTP ' + response.status);
            }
            return response.json();
        })
        .then(function (data) {
            if (data.success) {
                window.location.href = '/';
            } else {
                errDiv.textContent = data.message || 'Erreur lors de l’inscription.';
            }
        })
        .catch(function (error) {
            console.error(error);
            errDiv.textContent = 'Impossible de contacter le serveur, réessayez.';
        });
    });
});