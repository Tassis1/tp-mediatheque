package fr.tp.mediatheque.security;

import org.mindrot.jbcrypt.BCrypt;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PasswordHasher {

    public String hash(String motDePasseClair) {
        return BCrypt.hashpw(motDePasseClair, BCrypt.gensalt());
    }

    public boolean verifier(String motDePasseClair, String hash) {
        return BCrypt.checkpw(motDePasseClair, hash);
    }
}