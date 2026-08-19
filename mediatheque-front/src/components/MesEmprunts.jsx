import { useState, useEffect } from 'react';
import axios from 'axios';

function MesEmprunts({ utilisateur, onRetour }) {
    const [emprunts, setEmprunts] = useState([]);
    const [chargement, setChargement] = useState(true);

    const chargerEmprunts = () => {
        axios.get(`/api/emprunts/utilisateur/${utilisateur.id}/en-cours`)
            .then(reponse => {
                setEmprunts(reponse.data);
                setChargement(false);
            });
    };

    useEffect(() => {
        chargerEmprunts();
    }, [utilisateur.id]);

    const retourner = (empruntId) => {
        axios.put(`/api/emprunts/${empruntId}/retour`)
            .then(reponse => {
                setEmprunts(empruntsActuels =>
                    empruntsActuels.filter(e => e.id !== empruntId)
                );
                onRetour(reponse.data);
            })
            .catch(() => alert("Erreur lors du retour"));
    };

    if (chargement) return <p>Chargement de vos emprunts...</p>;

    return (
        <div className="carte">
            <h3>Mes emprunts en cours</h3>
            {emprunts.length === 0 ? (
                <p>Aucun emprunt en cours.</p>
            ) : (
                <ul className="liste-emprunts">
                    {emprunts.map(emprunt => (
                        <li key={emprunt.id}>
                            <span>{emprunt.livreTitre} — à rendre avant le {emprunt.dateRetourPrevue}</span>
                            <button onClick={() => retourner(emprunt.id)}>Retourner</button>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}

export default MesEmprunts;