// js/appelEnCours.js
document.addEventListener('DOMContentLoaded', ()=> {
  const params = new URLSearchParams(window.location.search);
  const consultationId = params.get('id');
  if (!consultationId) {
    document.getElementById('error').textContent = 'ID de consultation manquant';
    return;
  }
    
  const callUI     = document.getElementById('callUI');
  const valUI      = document.getElementById('validateUI');
  const errorDiv   = document.getElementById('error');

  const predA      = document.getElementById('predAmour');
  const predS      = document.getElementById('predSante');
  const predT      = document.getElementById('predTravail');
  const inA        = document.getElementById('inputAmour');
  const inS        = document.getElementById('inputSante');
  const inT        = document.getElementById('inputTravail');
  const btnGen     = document.getElementById('btnGen');
  const btnHangup  = document.getElementById('btnHangup');

  const pred2A     = document.getElementById('pred2Amour');
  const pred2S     = document.getElementById('pred2Sante');
  const pred2T     = document.getElementById('pred2Travail');
  const comm       = document.getElementById('commentaire');
  const btnValCons = document.getElementById('btnValiderConsul');

  // Générer prédiction
  btnGen.addEventListener('click', ()=>{
    errorDiv.textContent = '';
    const params = new URLSearchParams({
      todo: 'genererPrediction',
      id: consultationId,
      amour:  inA.value,
      sante:  inS.value,
      travail:inT.value
    });
    fetch(`ActionServlet?${params}`,{headers:{'Accept':'application/json'}})
    .then(r=>r.ok? r.json():Promise.reject(r.status))
    .then(json=>{
      if (json.success) {
        predA.value = json.amourTexte;
        predS.value = json.santeTexte;
        predT.value = json.travailTexte;
      } else {
        throw new Error(json.message);
      }
    })
    .catch(err=>{
      console.error(err);
      errorDiv.textContent = err.message||'Erreur génér. prédiction';
    });
  });

  // Raccrocher → passer en mode validation
  btnHangup.addEventListener('click', ()=>{
    // copier les prédictions
    pred2A.value = predA.value;
    pred2S.value = predS.value;
    pred2T.value = predT.value;
    callUI.style.display = 'none';
    valUI .style.display = 'block';
  });

  // Valider la consultation
  btnValCons.addEventListener('click', ()=>{
    const params = new URLSearchParams({
      todo: 'terminerConsultation',
      id: consultationId,
      amourTexte:   pred2A.value,
      santeTexte:   pred2S.value,
      travailTexte: pred2T.value,
      commentaire:  comm.value
    });
    fetch(`ActionServlet?${params}`,{headers:{'Accept':'application/json'}})
    .then(r=>r.ok? r.json():Promise.reject(r.status))
    .then(json=>{
      if (json.success) {
        window.location.href = 'espaceEmploye.html';
      } else {
        throw new Error(json.message);
      }
    })
    .catch(err=>{
      console.error(err);
      errorDiv.textContent = err.message||'Erreur validation';
    });
  });
});