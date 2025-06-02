document.addEventListener('DOMContentLoaded', () => {
    fetch('ActionServlet?todo=clientInfo', {
        headers: {'Accept': 'application/json'}
    })
    .then(resp => resp.json())
    .then(data => {
        document.getElementById('nom').value = data.nom;
        document.getElementById('prenom').value = data.prenom;
        document.getElementById('dateNaissance').value = data.dateNaissance;
        document.getElementById('tel').value = data.tel;
        document.getElementById('adresse').value = data.adresse;
    })
    .catch(err => console.error('Erreur chargement profil client', err));
});