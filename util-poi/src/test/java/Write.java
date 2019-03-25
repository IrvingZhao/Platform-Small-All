import cn.irving.zhao.util.poi.ExcelExporter;
import cn.irving.zhao.util.poi.config.WorkBookConfigFactory;
import entity.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Write {
    public static void main(String[] args) {
        WorkbookContainer workbookContainer = new WorkbookContainer();
        Entity1 e1_1 = new Entity1();
        Entity2 e2_1 = new Entity2();
        Entity2 e2_2 = new Entity2();

        workbookContainer.setS1("workbook-s1");
        workbookContainer.setS2(Arrays.asList("workbook-s2-1", "workbook-s2-2", "workbook-s2-3"));
        workbookContainer.setS3(Arrays.asList("workbook-s3-1", "workbook-s3-2", "workbook-s3-3"));

        e1_1.setS1("entity-1-1-s1");
        e1_1.setS2(Arrays.asList("entity-1-1-s2-1", "entity-1-1-s2-2", "entity-1-1-s2-3"));
        e1_1.setS3(Arrays.asList("entity-1-1-s3-1", "entity-1-1-s3-2", "entity-1-1-s3-3"));

        e2_2.setS1("entity-2-2-s1");
        e2_2.setS2(Arrays.asList("entity-2-2-s2-1", "entity-2-2-s2-2", "entity-2-2-s2-3"));
        e2_2.setS3(Arrays.asList("entity-2-2-s3-1", "entity-2-2-s3-2", "entity-2-2-s3-3"));
        e1_1.setEntity2(Arrays.asList(e2_2, e2_2));

        Entity2 e1_1_e2_1 = new Entity2();
        e1_1_e2_1.setS1("e1_1_e2_1-s1");
        e1_1_e2_1.setS2(Arrays.asList("e1_1_e2_1-s2-1", "e1_1_e2_1-s2-2", "e1_1_e2_1-s2-3"));
        e1_1_e2_1.setS3(Arrays.asList("e1_1_e2_1-s3-1", "e1_1_e2_1-s3-2", "e1_1_e2_1-s3-3"));

        Entity2 e1_1_e2_2 = new Entity2();
        e1_1_e2_2.setS1("e1_1_e2_2-s1");
        e1_1_e2_2.setS2(Arrays.asList("e1_1_e2_2-s2-1", "e1_1_e2_2-s2-2", "e1_1_e2_2-s2-3"));
        e1_1_e2_2.setS3(Arrays.asList("e1_1_e2_2-s3-1", "e1_1_e2_2-s3-2", "e1_1_e2_2-s3-3"));

        e1_1.setEntity2List(Arrays.asList(e1_1_e2_1, e1_1_e2_2));

        Entity3 e1_1_e3_1 = new Entity3(1, 2, "测试1");
        Entity3 e1_1_e3_2 = new Entity3(2, 3, "测试2");
        Entity3 e1_1_e3_3 = new Entity3(3, 4, "测试3");

        e1_1.setEntity3s(Arrays.asList(e1_1_e3_1, e1_1_e3_2, e1_1_e3_3));
        workbookContainer.setEntity1(e1_1);

        e2_1.setS1("entity-2-1-s1");
        e2_1.setS2(Arrays.asList("entity-2-1-s2-1", "entity-2-1-s2-2", "entity-2-1-s2-3"));
        e2_1.setS3(Arrays.asList("entity-2-1-s3-1", "entity-2-1-s3-2", "entity-2-1-s3-3"));
        workbookContainer.setEntity2(e2_1);

        workbookContainer.setEntity2s(Arrays.asList(e1_1_e2_1, e1_1_e2_2));

        workbookContainer.setEntity3s(List.of(e1_1_e3_1, e1_1_e3_2, e1_1_e3_3));

        Entity4 entity4 = new Entity4();

        List<Entity5> e5s = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            e5s.add(new Entity5("s1-e5-" + (i + 1), "s2-e5-" + (i + 1), "s3-e5-" + (i + 1), "s4-e5-" + (i + 1)));
        }
        entity4.setEntity5(e5s);
        entity4.setMerge("测试合并");

        workbookContainer.setEntity4(entity4);

        workbookContainer.setEntity4List(List.of(entity4, entity4, entity4));

//        WorkBookConfigFactory.getWorkBookConfig(workbookContainer);

        ExcelExporter.export(workbookContainer, "E:\\workspace\\base\\b.xlsx", "");
    }
}
