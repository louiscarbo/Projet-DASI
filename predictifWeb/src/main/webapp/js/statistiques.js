$(function () {
    let map, infoWindow;

    // 1) Initialise Google Maps
    function initMap() {
        const franceCenter = {lat: 46.2276, lng: 2.2137};
        map = new google.maps.Map(document.getElementById('map'), {
            center: franceCenter,
            zoom: 5
        });
        infoWindow = new google.maps.InfoWindow();
        loadStatistiques();
    }

    // 2) Requête unique vers ActionServlet?todo=statistiquesPage
    function loadStatistiques() {
        $.ajax({
            url: 'ActionServlet?todo=statistiquesPage',
            method: 'GET',
            dataType: 'json'
        })
                .done(function (data) {
                    if (!data.success) {
                        alert('Erreur : impossible de récupérer les statistiques.');
                        return;
                    }
                    placeClientMarkers(data.clients);
                    remplirTableEmployes(data.employes);
                    remplirTableMediums(data.mediums);
                })
                .fail(function () {
                    alert('Erreur réseau : impossible de charger les statistiques.');
                });
    }

    // 3) Fonction pour placer les marqueurs clients sur la carte
    function placeClientMarkers(clients) {
        const sdf = new Intl.DateTimeFormat('fr-FR');
        clients.forEach(function (c) {
            const lat = parseFloat(c.coordLat);
            const lng = parseFloat(c.coordLng);
            if (lat && lng) {
                const marker = new google.maps.Marker({
                    position: {lat: lat, lng: lng},
                    map: map,
                    title: c.prenom + ' ' + c.nom
                });
                marker.addListener('mouseover', () => {
                    const contenu = `
            <div style="font-family:${getComputedStyle(document.body).getPropertyValue('--font-serif')};">
              <strong>${c.prenom} ${c.nom}</strong><br>
              Né(e) le : ${c.dateNaissance}<br>
              📞 ${c.tel}<br>
              ${c.adresse}
            </div>`;
                    infoWindow.setContent(contenu);
                    infoWindow.open(map, marker);
                });
            }
        });
    }

    // 4) Remplir le tableau Employés
    function remplirTableEmployes(employes) {
        const tbody = $('#tblEmployes tbody');
        tbody.empty();
        employes.forEach(function (e) {
            const tr = $(`
        <tr data-empid="${e.id}">
          <td>${e.prenom}</td>
          <td>${e.nom}</td>
          <td>${e.nbClients}</td>
        </tr>
      `);
            tbody.append(tr);
        });
    }

    // 5) Remplir le tableau Médiums
    function remplirTableMediums(mediums) {
        const tbody = $('#tblMediums tbody');
        tbody.empty();
        mediums.forEach(function (m) {
            const tr = $(`
        <tr>
          <td>${m.nom}</td>
          <td>${m.specialite}</td>
          <td>${m.nbClients}</td>
        </tr>
      `);
            tbody.append(tr);
        });
    }

    // 7) Fermer le modal lorsque l’on clique sur la croix
    $('#closeModal').on('click', function () {
        $('#modalHistorique').fadeOut(200);
    });

    // 8) Bouton « Retour »
    $('#btnRetour').on('click', function () {
        window.location.href = '/espaceEmploye.html';
    });

    // 9) Initialisation finale
    initMap();
});