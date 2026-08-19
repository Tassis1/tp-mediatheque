package fr.tp.mediatheque.rest.dto;

import fr.tp.mediatheque.entity.Emprunt;
import java.time.LocalDate;

public class EmpruntDTO {

    public Long id;
    public Long utilisateurId;
    public String utilisateurNom;
    public Long livreId;
    public String livreTitre;
    public LocalDate dateEmprunt;
    public LocalDate dateRetourPrevue;
    public LocalDate dateRetourEffective;

    public EmpruntDTO() {}

    // Méthode de conversion : entité JPA -> DTO
    public static EmpruntDTO depuis(Emprunt emprunt) {
        EmpruntDTO dto = new EmpruntDTO();
        dto.id = emprunt.getId();
        dto.utilisateurId = emprunt.getUtilisateur().getId();
        dto.utilisateurNom = emprunt.getUtilisateur().getNom();
        dto.livreId = emprunt.getLivre().getId();
        dto.livreTitre = emprunt.getLivre().getTitre();
        dto.dateEmprunt = emprunt.getDateEmprunt();
        dto.dateRetourPrevue = emprunt.getDateRetourPrevue();
        dto.dateRetourEffective = emprunt.getDateRetourEffective();
        return dto;
    }
}