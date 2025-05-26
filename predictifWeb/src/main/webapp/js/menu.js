document.addEventListener('DOMContentLoaded', () => {
    const errorDiv = document.getElementById('error');

    fetch('/menu.html')
        .then(res => res.text())
        .then(html => {
            document.body.insertAdjacentHTML('afterbegin', html);

            const btnMenu = document.getElementById('btnMenu');
            const sideMenu = document.getElementById('sideMenu');
            const overlay = document.getElementById('menuOverlay');
            const logoutLink = document.getElementById('logoutLink');

            btnMenu.addEventListener('click', () => {
                sideMenu.classList.add('open');
                overlay.classList.add('open');
            });

            overlay.addEventListener('click', () => {
                sideMenu.classList.remove('open');
                overlay.classList.remove('open');
            });

            logoutLink.addEventListener('click', e => {
                e.preventDefault();
                fetch('ActionServlet?todo=deconnecter')
                        .then(r => r.json())
                        .then(() => window.location.href = '/')
                        .catch(err => {
                            console.error(err);
                            errorDiv.textContent = 'Échec de la déconnexion.';
                        });
            });

            return fetch('ActionServlet?todo=checkSession', {
                headers: {'Accept': 'application/json'}
            });
        })
        .then(res => {
            if (!res.ok)
                throw new Error(res.status);
            return res.json();
        })
        .then(data => {
            const btnMenu = document.getElementById('btnMenu');
            if (data.connected) {
                btnMenu.style.display = 'inline-block';
            } else {
                btnMenu.style.display = 'none';
            }

        })
        .catch(err => {
            console.error(err);
            errorDiv.textContent = 'Erreur session. Réessayez plus tard.';
        });
});