import axios from 'axios';

function ListeLivres({ livres, chargement, erreur, utilisateur, onEmprunt }) {
    if (chargement) return <p>Chargement...</p>;
    if (erreur) return <p className="erreur">{erreur}</p>;

    const emprunter = (livreId) => {
        axios.post('/api/emprunts', { utilisateurId: utilisateur.id, livreId })
            .then(reponse => {
                onEmprunt(reponse.data);
            })
            .catch(err => {
                const message = err.response?.data?.erreur || "Erreur lors de l'emprunt";
                alert(message);
            });
    };

    return (
        <div>
            <h2>Catalogue des livres</h2>
            <ul className="grille-livres">
                {livres.map(livre => (
                    <li key={livre.id} className="carte-livre">
                        <span className="titre">{livre.titre}</span>
                        <span className="auteur">{livre.auteur}</span>
                        <span className={`badge ${livre.disponible ? 'disponible' : 'emprunte'}`}>
                            {livre.disponible ? 'Disponible' : 'Emprunté'}
                        </span>
                        {utilisateur && livre.disponible && (
                            <button onClick={() => emprunter(livre.id)}>
                                Emprunter
                            </button>
                        )}
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default ListeLivres;