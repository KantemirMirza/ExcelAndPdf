package com.kani.excelandpdf.download;

import com.kani.excelandpdf.entity.Employee;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

public class EmployeeExcel {

    private XSSFWorkbook book;
    private XSSFSheet paper;

    private List<Employee> employeeList;

    public EmployeeExcel(List<Employee> employeeList) {
        this.employeeList = employeeList;
        book = new XSSFWorkbook();
        paper = book.createSheet("Employees");
    }

    private void employeePdfTable(){

        Row row = paper.createRow(0);

        CellStyle cellStyle = book.createCellStyle();
        XSSFFont font = book.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        cellStyle.setFont(font);

        Cell cell = row.createCell(0);
        cell.setCellValue("ID");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(1);
        cell.setCellValue("First_Name");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(2);
        cell.setCellValue("Last_Name");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3);
        cell.setCellValue("Email");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(4);
        cell.setCellValue("Phone");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(5);
        cell.setCellValue("Gender");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(6);
        cell.setCellValue("Salary");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(7);
        cell.setCellValue("Date");
        cell.setCellStyle(cellStyle);
    }

    private void employeePdfData(){
        int numberFile = 1;

        CellStyle cellStyle = book.createCellStyle();
        XSSFFont font = book.createFont();
        font.setFontHeight(16);
        cellStyle.setFont(font);

        for(Employee employee : employeeList){
            Row list = paper.createRow(numberFile ++);

            Cell cell = list.createCell(0);
            cell.setCellValue(employee.getEmployeeId());
            paper.autoSizeColumn(0);
            cell.setCellStyle(cellStyle);

            cell = list.createCell(1);
            cell.setCellValue(employee.getFirstName());
            paper.autoSizeColumn(1);
            cell.setCellStyle(cellStyle);

            cell = list.createCell(2);
            cell.setCellValue(employee.getLastName());
            paper.autoSizeColumn(2);
            cell.setCellStyle(cellStyle);

            cell = list.createCell(3);
            cell.setCellValue(employee.getEmail());
            paper.autoSizeColumn(3);
            cell.setCellStyle(cellStyle);

            cell = list.createCell(4);
            cell.setCellValue(employee.getPhone());
            paper.autoSizeColumn(4);
            cell.setCellStyle(cellStyle);

            cell = list.createCell(5);
            cell.setCellValue(employee.getGender());
            paper.autoSizeColumn(5);
            cell.setCellStyle(cellStyle);

            cell = list.createCell(6);
            cell.setCellValue(employee.getSalary());
            paper.autoSizeColumn(6);
            cell.setCellStyle(cellStyle);

            cell = list.createCell(7);
            cell.setCellValue(employee.getDate().toString());
            paper.autoSizeColumn(7);
            cell.setCellStyle(cellStyle);
        }
    }

    public void exportExcel(HttpServletResponse response) throws IOException {
        employeePdfTable();
        employeePdfData();

        ServletOutputStream servletOutputStream = response.getOutputStream();
        book.write(servletOutputStream);

        book.close();
        servletOutputStream.close();
    }
}
