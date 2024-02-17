package com.kani.excelandpdf.download;

import com.kani.excelandpdf.entity.Employee;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class EmployeePDF {
    private final List<Employee> employeeList;

    public EmployeePDF(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    private void employeePdfTable(PdfPTable table){
        PdfPCell pdfPCell = new PdfPCell();
        pdfPCell.setBackgroundColor(Color.BLUE);
        pdfPCell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        pdfPCell.setPhrase(new Phrase("ID", font));
        table.addCell(pdfPCell);

        pdfPCell.setPhrase(new Phrase("First_Name", font));
        table.addCell(pdfPCell);

        pdfPCell.setPhrase(new Phrase("Last_Name", font));
        table.addCell(pdfPCell);

        pdfPCell.setPhrase(new Phrase("Email", font));
        table.addCell(pdfPCell);

        pdfPCell.setPhrase(new Phrase("Phone", font));
        table.addCell(pdfPCell);

        pdfPCell.setPhrase(new Phrase("Gender", font));
        table.addCell(pdfPCell);

        pdfPCell.setPhrase(new Phrase("Salary", font));
        table.addCell(pdfPCell);

        pdfPCell.setPhrase(new Phrase("Date", font));
        table.addCell(pdfPCell);
    }

    private void employeePdfData(PdfPTable table){
        for(Employee employee : employeeList){
            table.addCell(String.valueOf(employee.getEmployeeId()));
            table.addCell(employee.getFirstName());
            table.addCell(employee.getLastName());
            table.addCell(employee.getEmail());
            table.addCell(String.valueOf(employee.getPhone()));
            table.addCell(employee.getGender());
            table.addCell(String.valueOf(employee.getSalary()));
            table.addCell(employee.getDate().toString());
        }
    }

    public void exportPdf(HttpServletResponse httpServletResponse) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, httpServletResponse.getOutputStream());
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setColor(Color.BLUE);
        font.setSize(18);

        Paragraph paragraph = new Paragraph("List Of Employee", font);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);

        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);
        table.setSpacingBefore(15);
        table.setWidths(new float[] {1f, 2.3f, 2.3f, 6f, 2.9f, 3.5f, 2f, 2.2f});

        employeePdfTable(table);
        employeePdfData(table);

        document.add(table);
        document.close();
    }
}
