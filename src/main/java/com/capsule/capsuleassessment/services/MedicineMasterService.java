package com.capsule.capsuleassessment.services;

import com.capsule.capsuleassessment.helper.ExcelHelper;
import com.capsule.capsuleassessment.models.MedicineMaster;
import com.capsule.capsuleassessment.repository.MedicineMasterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class MedicineMasterService {
    @Autowired
    MedicineMasterRepository medicineMasterRepository;
    public void saveMedicine(MultipartFile file) {
        try{
            List<MedicineMaster> medicines = ExcelHelper.excelToMedicine(file.getInputStream());
            for(MedicineMaster medicine: medicines){
                if(getMedicineByName(medicine.getMedicineName()) != null) continue;
                medicineMasterRepository.save(medicine);
            }
        } catch (IOException e) {
            throw new RuntimeException("Fail to store excel data:" + e.getMessage());
        }
    }

    public List<MedicineMaster> getMedicineList() {
        List<MedicineMaster> list = medicineMasterRepository.findAll();
        return list;
    }

    public MedicineMaster getMedicineByName(String name) {
        return medicineMasterRepository.findByMedicineName(name);
    }
}
