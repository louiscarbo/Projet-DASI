document.addEventListener('DOMContentLoaded', () => {
    const nomMediumInput = document.getElementById('nomMedium');
    const btnOuvrirFiltres = document.getElementById('btnFiltrer');
    const panel = document.getElementById('filterPanel');
    const btnFermerFiltres = document.getElementById('btnCloseFilter');
    const btnApplyFilter = document.getElementById('btnApplyFilters');
    const errDiv = document.getElementById('error');
        
    btnOuvrirFiltres.addEventListener('click', () => panel.style.display = "block");
    btnFermerFiltres.addEventListener('click', () => panel.style.display = "none");
        
    btnApplyFilter.addEventListener('click', async () => {
        errDiv.textContent = '';
        const astro = document.getElementById('astro').checked;
        const carto = document.getElementById('carto').checked;
        const spirite = document.getElementById('spirite').checked;
        const genre = document.querySelector('input[name="genre"]:checked').value; //

        const nomMedium = nomMediumInput.value.trim();
        
        // 2. Construire les paramètres
        const params = new URLSearchParams({
        todo: 'chercherMediums',
                spirite: spirite,
                astrologue: astro,
                cartomancien: carto,
                nomMedium: nomMedium,
                genre: genre
        });
                
        try {
            // 3. Envoi de la requête ; avec post, passer param dans corps de la requete
            const response = await fetch(`ActionServlet?${params.toString()}`, {
            method: 'GET',
                    headers: {
                    'Accept': 'application/json'
                    }
            });
            
            if (!response.ok) {
                throw new Error(`Statut HTTP ${response.status}`);
            }

            const data = await response.json();
            if (data.success) {
                // Peupler le tableau :
                const tbody = document.getElementById('tableContent');
                tbody.innerHTML = '';
                data.mediums.forEach(med => {
                    const tr = document.createElement('tr');
                    tr.innerHTML =  `
                        <td>${med.type}</td>
                        <td>${med.nom}</td>
                        <td>${med.genre}</td>
                        <td> 
                            <button id="btnReserver">Réserver</button>
                        </td>
                     `;
                    
                    // "Brancher" le bouton pour faire appel au service
                    const btnReserver = tr.querySelector('#btnReserver');
                    btnReserver.addEventListener('click', () => {
                        const params2 = new URLSearchParams({
                            todo: 'reserverConsultation',
                            mediumId: med.id
                        });

                        fetch(`ActionServlet?${params2.toString()}`, {
                        method: 'GET',
                                headers: {
                                'Accept': 'application/json'
                                }
                        })
                        .then(res => res.ok ? res.json() : Promise.reject(res.status))
                        .then(json => {
                            if (json.success) {
                                window.location.href = '/historique.html';
                            } else {
                                errDiv.textContent = 'La consultation n\'a pas pu être réservée. Choisissez un autre médium.';
                            }
                        })
                        .catch(err => {
                            console.error(err);
                            errDiv.textContent = 'Impossible de réserver la consultation, rééessayez.';
                        })
                    });
                    
                    tbody.appendChild(tr);
                });
            } else {
                errDiv.textContent = data.message || 'Erreur lors de la recherche de médium.';
            }
            
        } catch (err) {
            console.error(err);
            errDiv.textContent = 'Impossible de contacter le serveur, réessayez.';
        }
    });
});