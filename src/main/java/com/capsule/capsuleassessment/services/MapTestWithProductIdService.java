package com.capsule.capsuleassessment.services;

import com.capsule.capsuleassessment.helper.ExcelHelper;
import com.capsule.capsuleassessment.models.MedicineMaster;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class MapTestWithProductIdService {

    @Autowired
    MedicineMasterService medicineMasterService;
    public ByteArrayInputStream getResult(MultipartFile file) {
        ByteArrayInputStream in = null;
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheet("sheet1");
            Iterator<Row> rows = sheet.iterator();
            int rowNumber = 0;
            List<MedicineMaster> medicines = new ArrayList<>();
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                MedicineMaster medicine = medicineMasterService.getMedicineByName(currentRow.getCell(1).getStringCellValue().trim().replaceAll("'","\"").toLowerCase());
                if(medicine == null) continue;
                medicine.setMedicineName(currentRow.getCell(1).getStringCellValue());
                medicines.add(medicine);
            }
             in = ExcelHelper.medicinesToExcel(medicines);
            workbook.close();

        } catch (Exception e) {
            return in;
        }
        return in;

    }
}
