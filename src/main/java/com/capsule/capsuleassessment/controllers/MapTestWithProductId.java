package com.capsule.capsuleassessment.controllers;

import com.capsule.capsuleassessment.services.MapTestWithProductIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;


@RestController
public class MapTestWithProductId {

    @Autowired
    MapTestWithProductIdService mapTestWithProductIdService;

    @GetMapping("/testResult")
    public ResponseEntity<Resource> result(@RequestParam("test-file") MultipartFile file) {
        String filename = "test-result.xlsx";
        InputStreamResource resultFile = new InputStreamResource(mapTestWithProductIdService.getResult(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body( resultFile);
    }
}
