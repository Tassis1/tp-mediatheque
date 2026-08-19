import { useState } from 'react';
import axios from 'axios';

function LoginForm({ onConnexion }) {
    const [email, setEmail] = useState('');
    const [motDePasse, setMotDePasse] = useState('');
    const [erreur, setErreur] = useState(null);

    const handleSubmit = (e) => {
        e.preventDefault();
        setErreur(null);

        axios.post('/api/utilisateurs/login', { email, motDePasse })
            .then(reponse => {
                onConnexion(reponse.data);
            })
            .catch(err => {
                if (err.response && err.response.data && err.response.data.erreur) {
                    setErreur(err.response.data.erreur);
                } else {
                    setErreur('Erreur de connexion');
                }
            });
    };

    return (
        <form onSubmit={handleSubmit} className="carte">
            <h3>Connexion</h3>
            {erreur && <p className="erreur">{erreur}</p>}
            <div>
                <label>Email : </label>
                <input type="email" value={email} onChange={e => setEmail(e.target.value)} required />
            </div>
            <div>
                <label>Mot de passe : </label>
                <input type="password" value={motDePasse} onChange={e => setMotDePasse(e.target.value)} required />
            </div>
            <button type="submit">Se connecter</button>
        </form>
    );
}

export default LoginForm;