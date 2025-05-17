document.addEventListener('DOMContentLoaded', () => {
  const mailInput = document.getElementById('mailEmp');
  const mdpInput  = document.getElementById('mdpEmp');
  const btn       = document.getElementById('btnValiderEmp');
  const errorDiv  = document.getElementById('error');

  btn.addEventListener('click', async () => {
    errorDiv.textContent = '';

    const mail = mailInput.value.trim();
    const mdp  = mdpInput.value;
    if (!mail || !mdp) {
      errorDiv.textContent = 'Veuillez remplir tous les champs.';
      return;
    }

    // Construction de l'URL
    const params = new URLSearchParams({
      todo: 'connecterEmploye',
      mail: mail,
      mdp:  mdp
    });

    try {
      const response = await fetch(`ActionServlet?${params.toString()}`, {
        method: 'GET',
        headers: { 'Accept': 'application/json' }
      });
      if (!response.ok) throw new Error(`HTTP ${response.status}`);

      const data = await response.json();
      if (data.success) {
        window.location.href = 'espaceEmploye.html';
      } else {
        errorDiv.textContent = data.message || 'Identifiants incorrects.';
        // vider les champs
        mailInput.value = '';
        mdpInput.value  = '';
        mailInput.focus();
      }
    } catch (err) {
      console.error(err);
      errorDiv.textContent = 'Erreur serveur. Réessayez plus tard.';
    }
  });
});