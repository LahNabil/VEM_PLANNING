package net.lahlalia.stock.repositories;

import net.lahlalia.stock.entities.Bac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BacRepository extends JpaRepository<Bac, String> {
}
