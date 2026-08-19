package fr.tp.mediatheque.repository;

import fr.tp.mediatheque.entity.Livre;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class LivreRepository {

    @PersistenceContext(unitName = "mediathequePU")
    private EntityManager em;

    public List<Livre> findAll() {
        return em.createQuery("SELECT l FROM Livre l", Livre.class)
                  .getResultList();
    }

    public Livre findById(Long id) {
        return em.find(Livre.class, id);
    }
    @Transactional 
    public Livre save(Livre livre) {
        if (livre.getId() == null) {
            em.persist(livre);
            return livre;
        } else {
            return em.merge(livre);
        }
    }

    public void deleteById(Long id) {
        Livre livre = em.find(Livre.class, id);
        if (livre != null) {
            em.remove(livre);
        }
    }

    public boolean existsByIsbn(String isbn) {
        Long count = em.createQuery(
                "SELECT COUNT(l) FROM Livre l WHERE l.isbn = :isbn", Long.class)
                .setParameter("isbn", isbn)
                .getSingleResult();
        return count > 0;
    }
}