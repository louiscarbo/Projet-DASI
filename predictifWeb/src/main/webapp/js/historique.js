document.addEventListener('DOMContentLoaded', () => {
    const filterCarto = document.getElementById('filterCarto');
    const filterSpirite = document.getElementById('filterSpirite');
    const filterAstro = document.getElementById('filterAstro');
    const filterSince = document.getElementById('filterSince');
    const btnFilter = document.getElementById('btnFilter');
    const tbody = document.getElementById('tableContent');
    const errorDiv = document.getElementById('error');

    // Fonction pour appliquer les filtres
    function render(filters) {
        const params = new URLSearchParams({
            todo: 'afficherHistorique',
            spirite: filters.spirite,
            astrologue: filters.astro,
            cartomancien: filters.carto,
            nomMedium: filters.nomMedium,
            dateString: filters.since
        });

        fetch(`ActionServlet?${params.toString()}`, {
            headers: {'Accept': 'application/json'}
        })
        .then(r => r.json())
        .then(json => {
            tbody.innerHTML = '';
            if (!json.success) {
                errorDiv.textContent = json.message;
                return;
            }
            json.consultations.forEach(c => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${c.date}</td>
                    <td>${c.medium}</td>
                    <td>${c.type}</td>
                    <td>
                      <button class="btnDetail" data-id="${c.id}">Voir les Prédictions</button>
                    </td>`
                ;
                tr.querySelector('button.btnDetail')
                        .addEventListener('click', () => {
                            window.location.href = `detailConsultation.html?id=${c.id}`;
                        });
                tbody.appendChild(tr);
            });
        })
        .catch(err => {
            console.error(err);
            errorDiv.textContent = 'Erreur chargement historique.';
        });
    }

    // On fait un premier chargement des informations, sans les filtres
    render({
        spirite: false,
        astro: false,
        carto: false,
        nomMedium: '',
        since: '01/01/0000'
    });

    // On filtre quand on clique sur "Filtrer"
    btnFilter.addEventListener('click', () => {
        errorDiv.textContent = '';
        render({
            spirite: filterSpirite.checked,
            astro: filterAstro.checked,
            carto: filterCarto.checked,
            nomMedium: "",
            since: filterSince.value || '01/01/0000'
        });
    });
});