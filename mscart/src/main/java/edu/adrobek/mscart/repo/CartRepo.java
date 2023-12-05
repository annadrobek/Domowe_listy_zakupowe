package edu.adrobek.mscart.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart, Integer> {
    List<Cart> findByNameContaining(@Param("name") String name);
    Cart findByName(@Param("name") String name);
}