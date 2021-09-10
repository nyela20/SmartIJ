package smartij.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smartij.model.SmartIG;

import java.io.File;


class TwiskTest {

    SmartIG smartIG;
    File file;

    @BeforeEach
    void setup(){
        smartIG = new SmartIG();
        file = new File("src/smartij/tests/try.xlsx");
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
            FileOutputStream out = new FileOutputStream(new File("src/smartij/tests/try.xlsx"));
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    */
    }
}
