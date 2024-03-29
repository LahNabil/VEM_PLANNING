package net.lahlalia.produit.repositories;

import net.lahlalia.produit.entities.Regime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegimeRepository extends JpaRepository<Regime,Long> {
}
