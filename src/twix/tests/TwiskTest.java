package twix.tests;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twix.model.TwixIG;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


class TwiskTest {

    TwixIG twixIG;
    File file;

    @BeforeEach
    void setup(){
        twixIG = new TwixIG();
        file = new File("src/twix/tests/try.xlsx");
    }

    @Test
    void write2(){

    }


    @Test
    void write(){
/*
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Student Data");
        XSSFRow row;
        Map<String, Object[]> studentdata = new TreeMap<String, Object[]>();
        studentdata.put("1", new Object[] { "Roll No", "NAME", "Year" });

        studentdata.put("2", new Object[] { "128", "Nyela", "2nd year" });

        studentdata.put("3", new Object[] { "129", "Narayana", "2nd year" });

        Set<String> keyid =  studentdata.keySet();
        int rowid = 1;
        for(String key : keyid){
            System.out.println("key : " + key);
            row = sheet.createRow(rowid++);
            System.out.println("rowid : " + rowid);
            Object[] objects = studentdata.get(key);
            System.out.println("objects : " + objects);
            int cellid = 1;
            for(Object obj : objects){
                Cell cell = row.createCell(cellid++);
                System.out.println("cellid : " + cellid);
                cell.setCellValue((String) obj);
                System.out.println("obj : " + (String) obj);
            }
        }
        try {
            FileOutputStream out = new FileOutputStream(new File("src/twix/tests/try.xlsx"));
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    */
    }
}
