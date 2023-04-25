package com.capsule.capsuleassessment.repository;

import com.capsule.capsuleassessment.models.MedicineMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineMasterRepository extends JpaRepository<MedicineMaster, Integer> {

    public MedicineMaster findByMedicineName(String name);
}
