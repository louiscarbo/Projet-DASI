document.addEventListener('DOMContentLoaded', () => {
    const nomMediumInput = document.getElementById('nomMedium');
    const btnOuvrirFiltres = document.getElementById('btnFiltrer');
    const panel = document.getElementById('filterPanel');
    const btnFermerFiltres = document.getElementById('btnCloseFilter');
    const btnApplyFilter = document.getElementById('btnApplyFilters');
    const errDiv = document.getElementById('error');
    const tbody = document.getElementById('tableContent');

    // Afficher/Cacher le panneau filtres
    btnOuvrirFiltres.addEventListener('click', () => panel.style.display = 'block');
    btnFermerFiltres.addEventListener('click', () => panel.style.display = 'none');

    // Function pour appliquer les filtres
    function applyFilters() {
        errDiv.textContent = '';

        const astro = document.getElementById('astro').checked;
        const carto = document.getElementById('carto').checked;
        const spirite = document.getElementById('spirite').checked;
        const genre = document.querySelector('input[name="genre"]:checked').value;
        const nomMedium = nomMediumInput.value.trim();

        const params = new URLSearchParams({
            todo: 'chercherMediums',
            spirite: spirite,
            astrologue: astro,
            cartomancien: carto,
            nomMedium: nomMedium,
            genre: genre
        });

        fetch('ActionServlet?' + params.toString(), {
            method: 'GET',
            headers: {'Accept': 'application/json'}
        })
        .then(r => r.json())
        .then(data => {
            if (!data.success) {
                errDiv.textContent = data.message || 'Erreur lors de la recherche de médium.';
                return;
            }

            // Mise à jour du tableau quand on trouve les médiums
            tbody.innerHTML = '';
            data.mediums.forEach(med => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${med.type}</td>
                    <td>${med.nom}</td>
                    <td>${med.genre}</td>
                    <td><button class="btn-reserver btn-secondary">Réserver</button></td>
                  `
                ;
                // Logique du bouton de réservation
                tr.querySelector('.btn-reserver').addEventListener('click', () => {
                    const p2 = new URLSearchParams({
                        todo: 'reserverConsultation',
                        mediumId: med.id
                    });
                    fetch('ActionServlet?' + p2.toString(), {
                        method: 'GET',
                        headers: {'Accept': 'application/json'}
                    })
                    .then(r => r.json())
                    .then(json => {
                        if (json.success) {
                            window.location.href = '/historique.html';
                        } else {
                            errDiv.textContent = 'La consultation n’a pas pu être réservée.';
                        }
                    })
                    .catch(() => {
                        errDiv.textContent = 'Impossible de réserver, réessayez.';
                    });
                });
                tbody.appendChild(tr);
            });
        })
        .catch(err => {
            console.error(err);
            errDiv.textContent = 'Impossible de contacter le serveur, réessayez.';
        });
    }
    ;

    // Les boutons appellent applyFilters
    nomMediumInput.addEventListener('input', applyFilters);
    btnApplyFilter.addEventListener('click', applyFilters);
    
    // Appel initial pour peupler la page à l'affichage
    applyFilters();
});