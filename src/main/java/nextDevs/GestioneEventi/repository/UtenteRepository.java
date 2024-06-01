package nextDevs.GestioneEventi.repository;



import nextDevs.GestioneEventi.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente,Integer> {

    public Optional<Utente> findByEmail(String email);
}
