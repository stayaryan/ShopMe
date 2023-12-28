package com.shopme.admin.util;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.formula.EvaluationWorkbook;
import org.apache.poi.ss.formula.udf.UDFFinder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class UserExcelExporter extends AbstractExporter {

    private XSSFWorkbook workbook;

    private XSSFSheet sheet ;

    public  UserExcelExporter(){
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine(){
        sheet = workbook.createSheet("Users");
        XSSFRow row = sheet.createRow(0);

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        cellStyle.setFont(font);

        createCell(row,0,"User Id",cellStyle);
        createCell(row,1,"E-mail",cellStyle);
        createCell(row,2,"First Name",cellStyle);
        createCell(row,3,"Last Name",cellStyle);
        createCell(row,4,"Roles",cellStyle);
        createCell(row,5,"Enabled",cellStyle);



    }

    private void createCell(XSSFRow row,int columnIndex,Object value,CellStyle style){
        XSSFCell cell = row.createCell(columnIndex);
        sheet.autoSizeColumn(columnIndex);

        if(value instanceof Integer ){
            cell.setCellValue((Integer) value);
        }else if(value instanceof  Boolean){
            cell.setCellValue((Boolean) value);
        }else if(value instanceof String) {
            cell.setCellValue((String) value);
        }

    }


   private String convertRolesToString(Set<Role> roles){
        String ans = "";
        for(Role role :roles){
            ans = ans + role.toString()+",";
        }
        ans = ans.substring(0, ans.length() - 1);
        return ans;
   }

    private void writeDataLines(List<User> listUsers){
        int rowIndex = 1;

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        cellStyle.setFont(font);

        for(User user : listUsers){
            XSSFRow row = sheet.createRow(rowIndex++);
            int columnIndex = 0;
            createCell(row,columnIndex++,user.getId(),cellStyle);
            createCell(row,columnIndex++,user.getEmail(),cellStyle);
            createCell(row,columnIndex++,user.getFirstName(),cellStyle);
            createCell(row,columnIndex++,user.getLastName(),cellStyle);
            createCell(row,columnIndex++,user.getRoles().toString(),cellStyle);
            createCell(row,columnIndex,user.getEnabled(),cellStyle);

        }
    }

    public void export(List<User> listUsers, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response,"application/octet-stream",".xlsx");

        writeHeaderLine();
        writeDataLines(listUsers);



        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
