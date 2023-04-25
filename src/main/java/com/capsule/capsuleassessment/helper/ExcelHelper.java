package com.capsule.capsuleassessment.helper;

import com.capsule.capsuleassessment.models.MedicineMaster;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String SHEET = "MedicineMaster";
    static String[] RESULT_HEADER = {"S No.","Item Name","Composition","Manufacturer","Product ID_Master"};

    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static List<MedicineMaster> excelToMedicine(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet("sheet1");
            Iterator<Row> rows = sheet.iterator();

            List<MedicineMaster> medicines = new ArrayList<MedicineMaster>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();
                MedicineMaster medicine = new MedicineMaster();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 1:
                            medicine.setProductId(currentCell.getStringCellValue().trim());
                            break;
                        case 2:
                            medicine.setMedicineName(currentCell.getStringCellValue().trim().replaceAll("'","\"").toLowerCase());
                            break;
                        case 3:
                            medicine.setComposition(currentCell.getStringCellValue().trim());
                            break;
                        case 4:
                            medicine.setManufacturer(currentCell.getStringCellValue().trim());
                        case 5:
                            medicine.setPacking(currentCell.getStringCellValue().trim());
                        default:
                            break;
                    }

                    cellIdx++;
                }

                medicines.add(medicine);
            }

            workbook.close();
            return medicines;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    public static ByteArrayInputStream medicinesToExcel(List<MedicineMaster> medicines) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);

            // Header
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < RESULT_HEADER.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(RESULT_HEADER[col]);
            }

            int rowIdx = 0;
            for ( MedicineMaster medicine : medicines) {
                Row row = sheet.createRow(++rowIdx);

                row.createCell(0).setCellValue(rowIdx);
                row.createCell(1).setCellValue(medicine.getMedicineName());
                row.createCell(2).setCellValue(medicine.getComposition());
                row.createCell(3).setCellValue(medicine.getManufacturer());
                row.createCell(4).setCellValue(medicine.getProductId());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
}
