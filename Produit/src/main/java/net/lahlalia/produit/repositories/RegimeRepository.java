package net.lahlalia.produit.repositories;

import net.lahlalia.produit.entities.Regime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegimeRepository extends JpaRepository<Regime,Long> {
}
