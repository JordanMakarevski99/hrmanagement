package com.jmakarevski.hrmanagement.repository;

import com.jmakarevski.hrmanagement.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
}
