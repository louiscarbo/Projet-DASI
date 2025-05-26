document.addEventListener('DOMContentLoaded', () => {
  const filterCarto   = document.getElementById('filterCarto');
  const filterSpirite = document.getElementById('filterSpirite');
  const filterAstro   = document.getElementById('filterAstro');
  const filterMedium  = document.getElementById('filterMedium');
  const filterSince   = document.getElementById('filterSince');
  const btnFilter     = document.getElementById('btnFilter');
  const tbody         = document.getElementById('tableContent');
  const errorDiv      = document.getElementById('error');

  // 1) Charger la liste de tous les mediums pour le <select>
  fetch('ActionServlet?todo=listMediums', { headers:{ 'Accept':'application/json' }})
    .then(r=>r.ok? r.json():Promise.reject(r.status))
    .then(json => {
      json.mediums.forEach(m => {
        const opt = document.createElement('option');
        opt.value = m.nom;
        opt.textContent = m.nom;
        filterMedium.appendChild(opt);
      });
    })
    .catch(console.error);

  // 2) Fonction de rendu
  function render(filters) {
    const params = new URLSearchParams({
      todo:         'afficherHistorique',
      spirite:      filters.spirite,
      astrologue:   filters.astro,
      cartomancien: filters.carto,
      nomMedium:    filters.nomMedium,
      dateString:   filters.since
    });

    fetch(`ActionServlet?${params.toString()}`, {
      headers:{ 'Accept':'application/json' }
    })
    .then(r=>r.ok? r.json():Promise.reject(r.status))
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
            <button class="btnDetail" data-id="${c.id}">+</button>
          </td>`;
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

  // 3) Premier chargement sans filtre
  render({
    spirite:    false,
    astro:      false,
    carto:      false,
    nomMedium:  '',
    since:      '01/01/0000'
  });

  // 4) Au clic sur Filtrer
  btnFilter.addEventListener('click', () => {
    errorDiv.textContent = '';
    render({
      spirite:    filterSpirite.checked,
      astro:      filterAstro.checked,
      carto:      filterCarto.checked,
      nomMedium:  filterMedium.value,
      since:      filterSince.value || '01/01/0000'
    });
  });
});