### 使用POI导出Excel工具包

#### 使用方式

1. 创建Workbook对象，Workbook对象需实现 `cn.irving.zhao.util.poi.inter.IWorkbook` 接口，可选择性覆盖 `getWorkbookType` 方法，默认为 `XLSX` 模式
2. 创建 `cn.irving.zhao.util.poi.ExcelExporter` 实例，并调用 `export` 方法，传入 Workbook对象，输出流，模板文件等内容

```java
entity.WorkbookContainer workbookContainer = new entity.WorkbookContainer();
ExcelExporter excelExporter = new ExcelExporter();
excelExporter.export(workbookContainer, "D:\\basepath\\a.xlsx", "");
```

#### 配置详解

> workbook对象中，包含对整个对象与Excel单元格之间的映射关系，可配置属性与单元格位置、数据格式化对象、表格嵌套、表格合并等功能

##### 单个属性映射

> 在属性上添加 `cn.irving.zhao.util.poi.annonation.Cell` 注解，并配置 `rowIndex` 行坐标，`cellIndex` 列坐标，`dataType` 数据类型，坐标值从0开始

##### 单属性映射多单元格

> 在单个属性的映射基础上，添加 `cn.irving.zhao.util.poi.annonation.Repeatable` 注解，并配置 `direction` 循环方向，`identity` 每次循环递增数，`max` 最大循环数。其中，`direction` 可选：`HERIZONTAL` 横向，即cellIndex增加，`VERTICALLY` 纵向，即rowIndex增加，`BOTH` 双向增加。`identity` 可使用负值表示反向循环。`max` 为-1时，则不进行最大循环次数限制

##### 合并单元格

> 在单个属性的基础上，添加`cn.irving.zhao.util.poi.annonation.MergedPosition` 注解，并配置`endRowIndex` 结束行坐标，`endColIndex` 结束列坐标，合并单元格的行列坐标为：开始行 - Cell.rowIndex，开始列 - Cell.cellIndex，结束行endRowIndex，结束列endColIndex。注：当`MergedRegion` 与 `Repeatable` 同时使用时，每次合并的坐标在基础坐标的基础上，增加`Repeatable.identity`。

##### 工作簿引入

> 工作簿引入分为：外部引入，内部引入。外部引入：新创建一个工作簿并将对象中的内容写入至新的工作簿中，可进行多次循环创建引入。内部引入：将一个对象中的内容写入至当前工作簿中的指定位置。
>
> 在属性上添加`cn.irving.zhao.util.poi.annonation.Sheet` 注解，并配置`type` 引入方式，`name` 工作簿名称，`nameFormatter` 工作簿名称格式化类，`baseRow` 工作簿初始行，`baseCol` 工作簿初始列。
>
> 其中：`type` 可选 `INNER` / `OUTER` 分别对应内部引入和外部引入。
>
> `name` 与 `nameFormatter` 仅在 `type` 为 `OUTER` 时可用。
>
> `baseRow` 与 `baseCol` 仅在`type` 为 `INNER` 时可用。`baseRow` 与 `baseCol` 为指定被引入的工作簿的0,0坐标所对应的当前工作簿的坐标，默认为0,0
>
> 注：`cn.irving.zhao.util.poi.annonation.Sheet` 与 `cn.irving.zhao.util.poi.annonation.Cell` 、`cn.irving.zhao.util.poi.annonation.MergedPosition` 不可同时使用

##### 单属性映射多工作簿

> 在工作簿的基础上，添加`cn.irving.zhao.util.poi.annonation.Repeatable` 即可，使用方式与单属性映射多单元格方式一致。

#### 配置样例

##### entity.WorkbookContainer

```java
public class entity.WorkbookContainer implements IWorkbook {
    @Sheet(type = SheetType.OUTER, name = "workbook-outer-sheet1")
    private entity.Entity1 entity1;

    @Cell(rowIndex = 7, colIndex = 1)
    private String s1;

    @Cell(rowIndex = 8, colIndex = 2)
    @Repeatable(direction = Direction.HERIZONTAL, identity = 1)
    private List<String> s2;

    @Cell(rowIndex = 9, colIndex = 3)
    @Repeatable(direction = Direction.VERTICALLY, identity = 2, max = 2)
    @MergedRegion(endRowIndex = 10, endColIndex = 4)
    private List<String> s3;

    @Sheet(type = SheetType.INNER)
    private entity.Entity2 entity2;
}
```

##### entity.Entity1

```java
public class entity.Entity1 {
    @Cell(rowIndex = 1, colIndex = 1)
    private String s1;

    @Cell(rowIndex = 2, colIndex = 2)
    @Repeatable(direction = Direction.HERIZONTAL, identity = 1)
    private List<String> s2;

    @Cell(rowIndex = 3, colIndex = 3)
    @Repeatable(direction = Direction.VERTICALLY, identity = 2, max = 2)
    @MergedRegion(endRowIndex = 4, endColIndex = 4)
    private List<String> s3;

    @Sheet(type = SheetType.INNER, baseCol = 6, baseRow = 0)
    @Repeatable(direction = Direction.VERTICALLY, identity = 8)
    private List<entity.Entity2> entity2List;

    @Sheet(type = SheetType.OUTER, name = "引入外部", nameFormatter = DemoNameFormatter.class)
    @Repeatable
    private List<entity.Entity2> entity2;

    @Sheet(type = SheetType.INNER, baseRow = 20, baseCol = 0)
    @Repeatable(direction = Direction.VERTICALLY, identity = 2)
    private List<entity.Entity3> entity3s;
}
```

##### entity.Entity2

```java
public class entity.Entity2 {
    @Cell(rowIndex = 1, colIndex = 1)
    private String s1;

    @Cell(rowIndex = 2, colIndex = 2)
    @Repeatable(direction = Direction.HERIZONTAL, identity = 1)
    private List<String> s2;

    @Cell(rowIndex = 3, colIndex = 3)
    @Repeatable(direction = Direction.VERTICALLY, identity = 2, max = 2)
    @MergedRegion(endRowIndex = 4, endColIndex = 4)
    private List<String> s3;
}
```

##### entity.Entity3

```java
public class entity.Entity3 {
    @Cell(rowIndex = 1, colIndex = 1, dataType = CellDataType.NUMERIC)
    private int intA;

    @Cell(rowIndex = 1, colIndex = 2, dataType = CellDataType.NUMERIC)
    private int intB;

    @Cell(rowIndex = 1, colIndex = 3, dataType = CellDataType.FORMULA)
    @Formatter(cellDataFormatter = FormulaRepeatDataFormater.class)
    private String sum;
}
```

##### Entity1中使用的表格名称格式化类

```java
public class DemoNameFormatter implements SheetNameFormatter {
    public String getSheetName(String sheetName, int loopIndex) {
        return sheetName + "__" + (loopIndex + 1);
    }
}
```

##### Entity3中使用的单元格属性格式化类

```java
public class FormulaRepeatDataFormater implements CellDataFormatter {
    @Override
    public Object format(Object source, int rowIndex, int colIndex) {
        return "SUM(" + getColCharIndex((colIndex - 2)) + (rowIndex + 1) + ":" + getColCharIndex((colIndex - 1)) + (rowIndex + 1) + ")";
    }
}
```

##### 初始化数据及导出

```java
entity.WorkbookContainer workbookContainer = new entity.WorkbookContainer();
entity.Entity1 e1_1 = new entity.Entity1();
entity.Entity2 e2_1 = new entity.Entity2();
entity.Entity2 e2_2 = new entity.Entity2();

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

entity.Entity2 e1_1_e2_1 = new entity.Entity2();
e1_1_e2_1.setS1("e1_1_e2_1-s1");
e1_1_e2_1.setS2(Arrays.asList("e1_1_e2_1-s2-1", "e1_1_e2_1-s2-2", "e1_1_e2_1-s2-3"));
e1_1_e2_1.setS3(Arrays.asList("e1_1_e2_1-s3-1", "e1_1_e2_1-s3-2", "e1_1_e2_1-s3-3"));

entity.Entity2 e1_1_e2_2 = new entity.Entity2();
e1_1_e2_2.setS1("e1_1_e2_2-s1");
e1_1_e2_2.setS2(Arrays.asList("e1_1_e2_2-s2-1", "e1_1_e2_2-s2-2", "e1_1_e2_2-s2-3"));
e1_1_e2_2.setS3(Arrays.asList("e1_1_e2_2-s3-1", "e1_1_e2_2-s3-2", "e1_1_e2_2-s3-3"));

e1_1.setEntity2List(Arrays.asList(e1_1_e2_1, e1_1_e2_2));

entity.Entity3 e1_1_e3_1 = new entity.Entity3(1, 2, "测试1");
entity.Entity3 e1_1_e3_2 = new entity.Entity3(2, 3, "测试2");
entity.Entity3 e1_1_e3_3 = new entity.Entity3(3, 4, "测试3");

e1_1.setEntity3s(Arrays.asList(e1_1_e3_1, e1_1_e3_2, e1_1_e3_3));
workbookContainer.setEntity1(e1_1);

e2_1.setS1("entity-2-1-s1");
e2_1.setS2(Arrays.asList("entity-2-1-s2-1", "entity-2-1-s2-2", "entity-2-1-s2-3"));
e2_1.setS3(Arrays.asList("entity-2-1-s3-1", "entity-2-1-s3-2", "entity-2-1-s3-3"));
workbookContainer.setEntity2(e2_1);


ExcelExporter excelExporter = new ExcelExporter();

excelExporter.export(workbookContainer, "输出目录", "");
```

