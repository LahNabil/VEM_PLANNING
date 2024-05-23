package net.lahlalia.prevision.repositories;

import net.lahlalia.prevision.entities.Prevision;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrevisionRepository extends JpaRepository<Prevision,Long> {
}
