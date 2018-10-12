package com.fingerprint.matcher.project.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fingerprint.matcher.project.FingerPrintMatcher;

public class ExcelFunctions {

	static int rowNum = 0;
    static int colNum = 0;

	public static void createExcel(String sheetName, String fileName) {
		
		XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(sheetName);
        String[] columnNames = {"Threshold", "Match", "False Match", "Average Match Time", "Zero False Match", "Zero Non Match"};

        int colNum = 0;
        Row row = sheet.createRow(rowNum);
        for (String column : columnNames) {
            Cell cell = row.createCell(colNum++);
            cell.setCellValue(column);
        }

		try {
			FileOutputStream outputStream = new FileOutputStream(FingerPrintMatcher.DIRECTORY + fileName);
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeToExcel(String[] data, String fileName) {
		
		FileInputStream excelFile;
		Workbook workbook = null; 
		Sheet sheet = null;

		try {
			excelFile = new FileInputStream(new File(FingerPrintMatcher.DIRECTORY + fileName));
			workbook = new XSSFWorkbook(excelFile);
			sheet = workbook.getSheetAt(0);
		} catch (final Exception e) {
			e.printStackTrace();
		}
        
		colNum = 0;
        Row row = sheet.createRow(rowNum++);
        for (String value : data) {
            Cell cell = row.createCell(colNum++);
            cell.setCellValue(value);
        }

		try {
			FileOutputStream outputStream = new FileOutputStream(FingerPrintMatcher.DIRECTORY + fileName);
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
