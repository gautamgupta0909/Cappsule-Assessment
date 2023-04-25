package com.capsule.capsuleassessment.controllers;

import com.capsule.capsuleassessment.message.ResponseMessage;
import com.capsule.capsuleassessment.helper.ExcelHelper;
import com.capsule.capsuleassessment.models.MedicineMaster;
import com.capsule.capsuleassessment.services.MedicineMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class MedicineMasterController {
    @Autowired
    MedicineMasterService medicineMasterService;
    @PostMapping("api/excel/medicine")
    public ResponseEntity<ResponseMessage> addMedicine(@RequestParam("file") MultipartFile file) {
        String message = "";
        if(ExcelHelper.hasExcelFormat(file)) {
            try{
                medicineMasterService.saveMedicine(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {

                message = "Could not upload the file: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }
        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @GetMapping("api/medicines")
    public List<MedicineMaster> medicineList(){
        return medicineMasterService.getMedicineList();
    }


}
