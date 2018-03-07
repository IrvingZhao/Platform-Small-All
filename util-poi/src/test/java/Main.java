import cn.irving.zhao.util.poi.ExcelExporter;
import cn.irving.zhao.util.poi.ExcelImporter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.util.Arrays;

public class Main<T, A> {
    public static void main(String[] args) throws Exception {
//        Entity entity = new Entity();
//        entity.setS1("测试内容");
//        entity.setListStr(Arrays.asList("1", "2", "3", "4", "5"));
//        entity.setvListStr(Arrays.asList("v1", "v2", "v3", "v4", "v5"));
//        entity.setRegion("region");
//        entity.setMergeds(Arrays.asList("re1", "re2", "re3", "re4", "re5"));
////        new ExcelExporter().writeExcel(entity, "E:\\basepath\\a.xlsx", OutputType.XLSX);
//
//        System.out.println(entity.getClass().getAnnotation(Sheet.class).name());
//        System.out.println(Entity2.class.getAnnotation(Sheet.class).name());
//
//        Field field = entity.getClass().getDeclaredField("listStr");
//
//        Type type = field.getGenericType();
//        if (ParameterizedType.class.isAssignableFrom(type.getClass())) {
//            ParameterizedType pType = (ParameterizedType) type;
//            System.out.println(pType.getActualTypeArguments()[0]==String.class);
//        }
//        long time=System.currentTimeMillis();
//        PropertyUtils.getProperty(entity,"mergeds");
//        System.out.println(System.currentTimeMillis()-time);
//        for(int i=0;i<10000;i++) {
//            MethodUtils.getAccessibleMethod(Entity.class, "getMergeds", new Class[]{});
//        }
//        System.out.println(System.currentTimeMillis()-time);
//        Logger logger= LoggerFactory.getLogger(Main.class);
//        logger.error("测试格式化{}：{}","123","456","456");
//        logger.info("测试");
//        System.out.println(String.class.isInstance(123));
//        List<String> stringList=new ArrayList<>();
//        TypeVariable[] types = stringList.getClass().getTypeParameters();
//        for (TypeVariable type : types) {
//            System.out.println(type);
//            System.out.println(type.getClass());
//        }
//        Field field = Main.class.getDeclaredField("list");
//        Type mapMainType = field.getGenericType();
//        if (mapMainType instanceof ParameterizedType) {
//            // 执行强制类型转换
//            ParameterizedType parameterizedType = (ParameterizedType) mapMainType;
//            // 获取基本类型信息，即Map
//            Type basicType = parameterizedType.getRawType();
//            System.out.println("基本类型为：" + basicType);
//            // 获取泛型类型的泛型参数
//            Type[] types = parameterizedType.getActualTypeArguments();
//            for (int i = 0; i < types.length; i++) {
//                System.out.println("第" + (i + 1) + "个泛型类型是：" + types[i]);
//            }
//        }

//        DemoWorkbook demoWorkbook = new DemoWorkbook();
//        Sheet1 sheet1 = new Sheet1();
//        Sheet2 sheet2 = new Sheet2();
//
//        sheet1.setStr("sheet1-str");
//        sheet1.setListStr1(Arrays.asList("sheet-1-list1-1", "sheet-1-list1-2", "sheet-1-list1-3"));
//        sheet1.setListStr2(Arrays.asList("sheet-1-list2-1", "sheet-1-list2-2", "sheet-1-list2-3"));
//        sheet1.setSheet2(sheet2);
//
//        sheet2.setStr("sheet2-str");
//        sheet2.setListStr1(Arrays.asList("sheet-2-list1-1", "sheet-2-list1-2", "sheet-2-list1-3"));
//        sheet2.setListStr2(Arrays.asList("sheet-2-list2-1", "sheet-2-list2-2", "sheet-2-list2-3"));
//
//        demoWorkbook.setSheet1(sheet1);
//        demoWorkbook.setSheet2(sheet2);
//        long time = System.currentTimeMillis();
//        new ExcelExporter().writeExcel(demoWorkbook, "E:\\basepath\\a.xlsx", OutputType.XLSX);
//        System.out.println("time1：" + (System.currentTimeMillis() - time));
//
//        time = System.currentTimeMillis();
//
//        new ExcelExporter().writeExcel(demoWorkbook, "E:\\basepath\\b.xlsx", OutputType.XLSX);
//        System.out.println("time2：" + (System.currentTimeMillis() - time));

//        int rowIv = 1;
//        int colIv = 2;
//        System.out.println(rowIv + ":" + colIv);
//        rowIv = colIv = 0;
//        System.out.println(rowIv + ":" + colIv);

//        System.out.println(Entity1.class.getDeclaredField("s1").getGenericType().getClass());
//        System.out.println(((ParameterizedType)Entity1.class.getDeclaredField("s2").getGenericType()).getActualTypeArguments()[0].getClass());
//
//        if (args.length == 0) {
//            throw new RuntimeException();
//        }


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

        workbookContainer.setEntity2s(e1_1.getEntity2());
        workbookContainer.setEntity3s(e1_1.getEntity3s());

        long time = System.currentTimeMillis();
//        ExcelExporter.export(workbookContainer, "D:\\basepath\\a.xlsx", "");
//        System.out.println(System.currentTimeMillis() - time);
//        time = System.currentTimeMillis();
//        ExcelExporter.export(workbookContainer, "D:\\basepath\\b.xlsx", "");
//        System.out.println(System.currentTimeMillis() - time);

        time = System.currentTimeMillis();
        Workbook workbook = WorkbookFactory.create(new File("d:\\basepath\\a.xlsx"));
        WorkbookContainer result = ExcelImporter.imports(WorkbookContainer.class, workbook);
        System.out.println(System.currentTimeMillis() - time);
    }

//    public List<String> list;
}
