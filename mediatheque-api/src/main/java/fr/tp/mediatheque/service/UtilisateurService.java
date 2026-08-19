package fr.tp.mediatheque.service;

import fr.tp.mediatheque.entity.Utilisateur;
import fr.tp.mediatheque.repository.UtilisateurRepository;
import fr.tp.mediatheque.security.PasswordHasher;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UtilisateurService {

    @Inject
    private UtilisateurRepository utilisateurRepository;

    @Inject
    private PasswordHasher passwordHasher;

    public Utilisateur creer(String nom, String email, String motDePasseClair) {
        if (utilisateurRepository.findByEmail(email) != null) {
            throw new IllegalStateException("Un compte existe déjà avec cet email");
        }
        Utilisateur u = new Utilisateur();
        u.setNom(nom);
        u.setEmail(email);
        u.setMotDePasseHash(passwordHasher.hash(motDePasseClair));
        return utilisateurRepository.save(u);
    }

    public Utilisateur authentifier(String email, String motDePasseClair) {
        Utilisateur u = utilisateurRepository.findByEmail(email);
        if (u == null || !passwordHasher.verifier(motDePasseClair, u.getMotDePasseHash())) {
            throw new SecurityException("Email ou mot de passe incorrect");
        }
        return u;
    }
}