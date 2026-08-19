import { useState, useEffect } from 'react';
import axios from 'axios';
import ListeLivres from './components/ListeLivres';
import FormulaireLivre from './components/FormulaireLivre';
import LoginForm from './components/LoginForm';
import MesEmprunts from './components/MesEmprunts';
import './App.css';

function App() {
    const [livres, setLivres] = useState([]);
    const [chargement, setChargement] = useState(true);
    const [erreur, setErreur] = useState(null);
    const [utilisateur, setUtilisateur] = useState(null);

    const chargerLivres = () => {
        axios.get('/api/livres')
            .then(reponse => {
                setLivres(reponse.data);
                setChargement(false);
            })
            .catch(() => {
                setErreur('Impossible de charger les livres');
                setChargement(false);
            });
    };

    useEffect(() => {
        chargerLivres();
    }, []);

    const handleLivreCree = (nouveauLivre) => {
        setLivres(livresActuels => [...livresActuels, nouveauLivre]);
    };

    const handleConnexion = (donneesUtilisateur) => {
        setUtilisateur(donneesUtilisateur);
    };

    const handleDeconnexion = () => {
        setUtilisateur(null);
    };

    const handleEmprunt = (emprunt) => {
        setLivres(livresActuels =>
            livresActuels.map(livre =>
                livre.id === emprunt.livreId ? { ...livre, disponible: false } : livre
            )
        );
    };

    const handleRetour = (emprunt) => {
        setLivres(livresActuels =>
            livresActuels.map(livre =>
                livre.id === emprunt.livreId ? { ...livre, disponible: true } : livre
            )
        );
    };

    return (
        <div className="App">
            <h1>Médiathèque</h1>

            {utilisateur ? (
                <div className="barre-utilisateur">
                    <p>Connecté en tant que <strong>{utilisateur.nom}</strong> ({utilisateur.role})</p>
                    <button onClick={handleDeconnexion}>Se déconnecter</button>
                </div>
            ) : (
                <LoginForm onConnexion={handleConnexion} />
            )}

            {utilisateur && <MesEmprunts utilisateur={utilisateur} onRetour={handleRetour} />}

            {utilisateur && utilisateur.role === 'ADMIN' && (
                <FormulaireLivre onLivreCree={handleLivreCree} />
            )}

            <ListeLivres
                livres={livres}
                chargement={chargement}
                erreur={erreur}
                utilisateur={utilisateur}
                onEmprunt={handleEmprunt}
            />
        </div>
    );
}

export default App;