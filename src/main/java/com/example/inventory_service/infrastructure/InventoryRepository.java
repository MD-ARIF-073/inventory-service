package com.example.inventory_service.infrastructure;

import com.example.inventory_service.infrastructure.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {

    Optional<InventoryEntity> findByProductId(String productId);
}
