document.addEventListener('DOMContentLoaded', () => {
    const nomMediumInput = document.getElementById('nomMedium');
    const btnOuvrirFiltres = document.getElementById('btnFiltrer');
    const panel = document.getElementById('filterPanel');
    const btnFermerFiltres = document.getElementById('btnCloseFilter');
    const btnApplyFilter = document.getElementById('btnApplyFilters');
    const errDiv = document.getElementById('error');
    const tbody = document.getElementById('tableContent');

    // 1) Show/hide the filter panel
    btnOuvrirFiltres.addEventListener('click', () => panel.style.display = "block");
    btnFermerFiltres.addEventListener('click', () => panel.style.display = "none");

    // 2) Core filter logic extracted
    async function applyFilters() {
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

        try {
            const response = await fetch(`ActionServlet?${params}`, {
                method: 'GET',
                headers: {'Accept': 'application/json'}
            });
            if (!response.ok)
                throw new Error(`HTTP ${response.status}`);
            const data = await response.json();

            if (!data.success) {
                errDiv.textContent = data.message || 'Erreur lors de la recherche de médium.';
                return;
            }

            // populate table
            tbody.innerHTML = '';
            data.mediums.forEach(med => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
          <td>${med.type}</td>
          <td>${med.nom}</td>
          <td>${med.genre}</td>
          <td><button class="btn-reserver btn-secondary">Réserver</button></td>
        `;
                tr.querySelector('.btn-reserver').addEventListener('click', () => {
                    const p2 = new URLSearchParams({
                        todo: 'reserverConsultation',
                        mediumId: med.id
                    });
                    fetch(`ActionServlet?${p2}`, {
                        method: 'GET',
                        headers: {'Accept': 'application/json'}
                    })
                            .then(r => r.ok ? r.json() : Promise.reject(r.status))
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

        } catch (err) {
            console.error(err);
            errDiv.textContent = 'Impossible de contacter le serveur, réessayez.';
        }
    }

    nomMediumInput.addEventListener('input', applyFilters);

    btnApplyFilter.addEventListener('click', applyFilters);
    applyFilters();
});