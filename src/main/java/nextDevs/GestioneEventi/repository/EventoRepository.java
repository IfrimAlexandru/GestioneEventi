package nextDevs.GestioneEventi.repository;

import nextDevs.GestioneEventi.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento,Integer> {
}
