package org.luvx.excel;

import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.luvx.utils.ExcelUtil;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class ExcelTest {

    @Test
    public void createExcel() throws Exception {
        List<String> row1 = Arrays.asList("000001", "foo", "bar", "26");
        List<String> row2 = Arrays.asList("000002", "Luvx", "1234", "27");
        List<List<String>> list = Arrays.asList(row1, row2);

        File file = new File("D:\\code\\me_repo\\spring-boot-excel\\src\\test\\resources\\test.xlsx");
        InputStream is = new FileInputStream(file);

        Workbook workbook = ExcelUtil.getWorkbook(1, is);
        workbook = ExcelUtil.createExcel(workbook, "user info", list);


        File file1 = new File("D:\\code\\me_repo\\spring-boot-excel\\src\\test\\resources\\test1.xlsx");

        OutputStream os = new FileOutputStream(file1);
        workbook.write(os);
        os.flush();
        os.close();

        System.out.println("end...");
    }


    @Test
    public void createExce() {
        int[] array = {-2,-4,2,4};
        boolean result = canReorderDoubled(array);
        System.out.println(result);
    }

    public boolean canReorderDoubled(int[] A) {
        int length = A.length;
        int i = 0;
        while (i < length / 2) {
            if (A[2 * i + 1] == 2 * A[2 * i]) {
                i++;
                continue;
            } else {
                return false;
            }
        }
        return false;
    }
}
