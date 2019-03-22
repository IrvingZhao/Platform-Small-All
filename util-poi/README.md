### 使用POIExcel工具包

#### Excel导出

1. 创建Workbook对象，Workbook需实现``cn.irving.zhao.util.poi.inter.IWorkbook``接口或添加``cn.irving.zhao.util.poi.annonation.WorkBook``注解完成，并自由配置工作簿默认工作表名称、工作簿默认工作表名称格式化类，工作簿类型等信息
2. 调用``cn.irving.zhao.util.poi.ExcelExporter.export``方法，传入Workbook对象，输出位置，模板数据流参数

```java
entity.WorkbookContainer workbookContainer = new entity.WorkbookContainer();
ExcelExporter excelExporter = new ExcelExporter();
excelExporter.export(workbookContainer, "D:\\basepath\\a.xlsx", "");
```

#### Excel导入

> 调用``cn.irving.zhao.util.poi.ExcelImporter.readExcel``方法，并传入文件流及需要导出的数据类型

#### 配置详解

> workbook对象中，包含对整个对象与Excel单元格之间的映射关系，可配置属性与单元格位置、数据格式化对象、表格嵌套、表格合并等功能

##### 单元格配置

> 在属性上添加 `cn.irving.zhao.util.poi.annonation.Cell` 注解，并配置 `rowIndex` 行坐标，`cellIndex` 列坐标，`dataType` 数据类型，坐标值从0开始，并可选配 重复 配置、合并单元格配置、数据格式化配置

##### 循环配置

> 在属性上添加``cn.irving.zhao.util.poi.annotation.Repeatable``注解，并配置(rowIdentity,colIdentity)/formatter(循环行列单次追加量)、max(最大循环次数)、check(循环是否继续检查)、collectionType(集合实现类)、itemType(集合元素对象实现类)，后三项为读取时使用

##### 单元格合并配置

> 在属性上添加``cn.irving.zhao.util.poi.annotation.MergedFormat``或``cn.irving.zhao.util.poi.annotation.MergedPosition``注解，并配置合并结束坐标或设置结束坐标计算类

##### 工作表引入

> 工作表引入分为：外部引入，内部引入。外部引入：新创建一个工作表并将对象中的内容写入至新的工作表中，可进行多次循环创建引入。内部引入：将一个对象中的内容写入至当前工作表中的指定位置。
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

> 读取写入样例在工程``test``目录下，``Read``为读取测试类，``Write``为写入测试类