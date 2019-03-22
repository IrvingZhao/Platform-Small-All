import cn.irving.zhao.util.poi.ExcelImporter;
import entity.WorkbookContainer;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;

public class Read {
    public static void main(String[] args) throws IOException {
        Workbook workbook = WorkbookFactory.create(new File("E:\\workspace\\base\\b.xlsx"));
        WorkbookContainer container = ExcelImporter.readExcel(WorkbookContainer.class,workbook);
        System.out.println(container);
    }
}
