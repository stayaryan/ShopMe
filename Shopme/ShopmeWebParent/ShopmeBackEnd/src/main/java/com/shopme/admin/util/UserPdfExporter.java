package com.shopme.admin.util;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;
import com.shopme.common.entity.User;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;
import java.util.List;



public class UserPdfExporter extends AbstractExporter {

    public void writeTableHeader(PdfPTable table){
        PdfPCell pdfPCell = new PdfPCell();
        pdfPCell.setBackgroundColor(Color.gray);
        pdfPCell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setSize(14);
        font.setColor(Color.WHITE);

        pdfPCell.setPhrase(new Phrase("Id",font));
        table.addCell(pdfPCell);

        pdfPCell.setPhrase(new Phrase("E-Mail",font));
        table.addCell(pdfPCell);

        pdfPCell.setPhrase(new Phrase("FirstName",font));
        table.addCell(pdfPCell);

        pdfPCell.setPhrase(new Phrase("LastName",font));
        table.addCell(pdfPCell);

        pdfPCell.setPhrase(new Phrase("Roles",font));
        table.addCell(pdfPCell);

        pdfPCell.setPhrase(new Phrase("Enable",font));
        table.addCell(pdfPCell);


    }

    public void writeTableData(PdfPTable table,List<User> userList){

        for(User user : userList){
            table.addCell(String.valueOf(user.getId()));
            table.addCell(String.valueOf(user.getEmail()));
            table.addCell(String.valueOf(user.getFirstName()));
            table.addCell(String.valueOf(user.getLastName()));
            table.addCell(String.valueOf(user.getRoles()));
            table.addCell(String.valueOf(user.getEnabled()));
        }
    }
    public void export(List<User> listUsers, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "application/pdf", ".pdf");

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document,response.getOutputStream());

        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(24);
        font.setColor(Color.BLACK);

        Paragraph paragraph = new Paragraph("List of User",font);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(paragraph);

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100f);
        table.setSpacingBefore(10);
        table.setWidths(new float[] {1.0f,4.0f,2.0f,2.0f,3.0f,1.5f});


        writeTableHeader(table);
        writeTableData(table,listUsers);

        document.add(table);

        document.close();


    }
}
