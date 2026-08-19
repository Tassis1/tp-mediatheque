import { useState } from 'react';
import axios from 'axios';

function FormulaireLivre({ onLivreCree }) {
    const [titre, setTitre] = useState('');
    const [auteur, setAuteur] = useState('');
    const [isbn, setIsbn] = useState('');
    const [erreur, setErreur] = useState(null);

    const handleSubmit = (e) => {
        e.preventDefault();
        setErreur(null);

        axios.post('/api/livres', { titre, auteur, isbn, disponible: true })
            .then(reponse => {
                setTitre('');
                setAuteur('');
                setIsbn('');
                onLivreCree(reponse.data);
            })
            .catch(err => {
                if (err.response && err.response.data && err.response.data.erreur) {
                    setErreur(err.response.data.erreur);
                } else {
                    setErreur('Erreur lors de la création du livre');
                }
            });
    };

    return (
        <form onSubmit={handleSubmit} className="carte">
            <h3>Ajouter un livre</h3>
            {erreur && <p className="erreur">{erreur}</p>}
            <div>
                <label>Titre : </label>
                <input value={titre} onChange={e => setTitre(e.target.value)} required />
            </div>
            <div>
                <label>Auteur : </label>
                <input value={auteur} onChange={e => setAuteur(e.target.value)} required />
            </div>
            <div>
                <label>ISBN : </label>
                <input value={isbn} onChange={e => setIsbn(e.target.value)} required />
            </div>
            <button type="submit">Créer</button>
        </form>
    );
}

export default FormulaireLivre;