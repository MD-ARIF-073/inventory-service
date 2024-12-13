package com.example.inventory_service.infrastructure;

import com.example.inventory_service.core.model.InventoryDomainModel;
import com.example.inventory_service.infrastructure.entity.InventoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "order.created", groupId = "inventory-group")
    public void handleOrderCreated(InventoryDomainModel event) {
        InventoryEntity inventory = inventoryRepository.findByProductId(event.getProductId())
                .orElse(new InventoryEntity(null, event.getProductId(), 0));

        if (inventory.getQuantity() >= event.getQuantity()) {
            inventory.setQuantity(inventory.getQuantity() - event.getQuantity());
            inventoryRepository.save(inventory);

            kafkaTemplate.send("order.processed", new InventoryDomainModel(event.getOrderId(), "PROCESSED"));
        } else {
            kafkaTemplate.send("order.processed", new InventoryDomainModel(event.getOrderId(), "FAILED"));
        }
    }

}
