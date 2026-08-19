package fr.tp.mediatheque.repository;

import fr.tp.mediatheque.entity.Emprunt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class EmpruntRepository {

    @PersistenceContext(unitName = "mediathequePU")
    private EntityManager em;

    public List<Emprunt> findAll() {
        return em.createQuery("SELECT e FROM Emprunt e", Emprunt.class)
                  .getResultList();
    }

    public Emprunt findById(Long id) {
        return em.find(Emprunt.class, id);
    }

    public List<Emprunt> findEnCoursByUtilisateur(Long utilisateurId) {
        return em.createQuery(
                "SELECT e FROM Emprunt e WHERE e.utilisateur.id = :uid AND e.dateRetourEffective IS NULL",
                Emprunt.class)
                .setParameter("uid", utilisateurId)
                .getResultList();
    }
    @Transactional
    public Emprunt save(Emprunt emprunt) {
        if (emprunt.getId() == null) {
            em.persist(emprunt);
            return emprunt;
        } else {
            return em.merge(emprunt);
        }
    }
}