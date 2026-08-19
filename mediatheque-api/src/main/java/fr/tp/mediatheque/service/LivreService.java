package fr.tp.mediatheque.service;

import fr.tp.mediatheque.entity.Livre;
import fr.tp.mediatheque.repository.LivreRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class LivreService {

    @Inject
    private LivreRepository livreRepository;

    public List<Livre> listerTous() {
        return livreRepository.findAll();
    }

    public Livre obtenirParId(Long id) {
        Livre livre = livreRepository.findById(id);
        if (livre == null) {
            throw new IllegalArgumentException("Livre introuvable, id=" + id);
        }
        return livre;
    }

    public Livre creer(Livre livre) {
        if (livreRepository.existsByIsbn(livre.getIsbn())) {
            throw new IllegalStateException("Un livre avec cet ISBN existe déjà");
        }
        return livreRepository.save(livre);
    }

    public Livre mettreAJour(Long id, Livre donnees) {
        Livre existant = obtenirParId(id);
        existant.setTitre(donnees.getTitre());
        existant.setAuteur(donnees.getAuteur());
        existant.setIsbn(donnees.getIsbn());
        return livreRepository.save(existant);
    }

    public void supprimer(Long id) {
        livreRepository.deleteById(id);
    }
}