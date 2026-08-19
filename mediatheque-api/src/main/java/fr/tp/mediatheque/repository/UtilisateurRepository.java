package fr.tp.mediatheque.repository;

import fr.tp.mediatheque.entity.Utilisateur;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class UtilisateurRepository {

    @PersistenceContext(unitName = "mediathequePU")
    private EntityManager em;

    public List<Utilisateur> findAll() {
        return em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class)
                  .getResultList();
    }

    public Utilisateur findById(Long id) {
        return em.find(Utilisateur.class, id);
    }

    public Utilisateur findByEmail(String email) {
        try {
            return em.createQuery(
                    "SELECT u FROM Utilisateur u WHERE u.email = :email", Utilisateur.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    @Transactional
    public Utilisateur save(Utilisateur utilisateur) {
        if (utilisateur.getId() == null) {
            em.persist(utilisateur);
            return utilisateur;
        } else {
            return em.merge(utilisateur);
        }
    }
}