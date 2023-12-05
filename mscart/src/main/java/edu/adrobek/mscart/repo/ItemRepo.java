package edu.adrobek.mscart.repo;

import java.util.List;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepo extends JpaRepository<Item, Integer> {
  List<Item> findByCartId(int cart_id);
  
  @Transactional
  void deleteByCartId(int cart_id);
}