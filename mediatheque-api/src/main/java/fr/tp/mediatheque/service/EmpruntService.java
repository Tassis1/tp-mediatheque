package fr.tp.mediatheque.service;

import fr.tp.mediatheque.entity.Emprunt;
import fr.tp.mediatheque.entity.Livre;
import fr.tp.mediatheque.entity.Utilisateur;
import fr.tp.mediatheque.repository.EmpruntRepository;
import fr.tp.mediatheque.repository.LivreRepository;
import fr.tp.mediatheque.repository.UtilisateurRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class EmpruntService {

    private static final int DUREE_EMPRUNT_JOURS = 21;

    @Inject
    private EmpruntRepository empruntRepository;

    @Inject
    private LivreRepository livreRepository;

    @Inject
    private UtilisateurRepository utilisateurRepository;

    @Transactional
    public Emprunt creerEmprunt(Long utilisateurId, Long livreId) {
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId);
        if (utilisateur == null) {
            throw new IllegalArgumentException("Utilisateur introuvable");
        }

        Livre livre = livreRepository.findById(livreId);
        if (livre == null) {
            throw new IllegalArgumentException("Livre introuvable");
        }
        if (!livre.isDisponible()) {
            throw new IllegalStateException("Ce livre n'est pas disponible actuellement");
        }

        Emprunt emprunt = new Emprunt();
        emprunt.setUtilisateur(utilisateur);
        emprunt.setLivre(livre);
        emprunt.setDateEmprunt(LocalDate.now());
        emprunt.setDateRetourPrevue(LocalDate.now().plusDays(DUREE_EMPRUNT_JOURS));

        livre.setDisponible(false);
        livreRepository.save(livre);

        return empruntRepository.save(emprunt);
    }

    @Transactional
    public Emprunt retournerLivre(Long empruntId) {
        Emprunt emprunt = empruntRepository.findById(empruntId);
        if (emprunt == null) {
            throw new IllegalArgumentException("Emprunt introuvable");
        }
        if (emprunt.getDateRetourEffective() != null) {
            throw new IllegalStateException("Ce livre a déjà été retourné");
        }

        emprunt.setDateRetourEffective(LocalDate.now());

        Livre livre = emprunt.getLivre();
        livre.setDisponible(true);
        livreRepository.save(livre);

        emprunt.getUtilisateur().getNom(); // force l'initialisation du proxy Utilisateur pendant la transaction

        return empruntRepository.save(emprunt);
    }

    @Transactional
    public List<Emprunt> empruntsEnCoursDe(Long utilisateurId) {
        List<Emprunt> emprunts = empruntRepository.findEnCoursByUtilisateur(utilisateurId);
        for (Emprunt e : emprunts) {
            e.getUtilisateur().getNom();  // force l'initialisation des proxies pendant la transaction
            e.getLivre().getTitre();
        }
        return emprunts;
    }
}