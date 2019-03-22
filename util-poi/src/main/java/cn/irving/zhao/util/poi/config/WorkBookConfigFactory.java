package cn.irving.zhao.util.poi.config;

import cn.irving.zhao.util.poi.annonation.*;
import cn.irving.zhao.util.poi.exception.ExportException;
import cn.irving.zhao.util.poi.formatter.FormatterFactory;
import cn.irving.zhao.util.poi.formatter.SheetNameFormatter;
import cn.irving.zhao.util.poi.inter.IWorkbook;
import org.apache.commons.collections4.map.ReferenceMap;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * 工作簿配置工厂类。
 */
public final class WorkBookConfigFactory {

    private static FormatterFactory<SheetNameFormatter> factory = FormatterFactory.getFormatterFactory(SheetNameFormatter.class);

    private WorkBookConfigFactory() {
    }

    private ReferenceMap<String, WorkBookConfig> workBookCache = new ReferenceMap<>();//WorkBook配置缓存

    private ReferenceMap<String, CellSheetCache> sheetConfigCache = new ReferenceMap<>();

    private static final WorkBookConfigFactory me = new WorkBookConfigFactory();

    /**
     * 获取工作簿配置信息
     *
     * @param workbook 工作簿对象
     * @return 工作簿配置
     */
    public static WorkBookConfig getWorkBookConfig(IWorkbook workbook) {
        var type = workbook.getClass();
        var result = me.workBookCache.get(type.getName());//读取缓存;
        if (result == null) {//缓存未命中
            result = WorkBookConfig.createWorkBookConfig(workbook.getWorkbookType(), workbook.getSheetName(), workbook.getDefaultSheetNameFormatter());//创建配置

            me.setWorkBookConfig(result, type);//设置配置

            me.workBookCache.put(type.getName(), result);//写入缓存

        }

        return result;
    }

    public static WorkBookConfig getWorkBookConfig(Object workBook) {
        if (workBook instanceof IWorkbook) {
            return getWorkBookConfig((IWorkbook) workBook);
        }
        var type = workBook.getClass();
        var result = me.workBookCache.get(type.getName());
        if (result == null) {
            WorkBook bookConfig = type.getAnnotation(WorkBook.class);
            if (bookConfig == null) {
                throw new ExportException(type.getName() + "未找到 " + WorkBook.class.getName() + " 注解");
            }
            result = WorkBookConfig.createWorkBookConfig(bookConfig.type(), bookConfig.defaultSheetName(), factory.getFormatter(bookConfig.defaultSheetNameFormatter()));

            me.setWorkBookConfig(result, type);

            me.workBookCache.put(type.getName(), result);//写入缓存
        }

        return result;
    }

    private void setWorkBookConfig(WorkBookConfig workBookConfig, Class<?> workbookType) {
        var tempType = workbookType;
        while (tempType != Object.class) {
            var fields = tempType.getDeclaredFields();//获得属性
            for (var field : fields) {
                if (field.isAnnotationPresent(Sheet.class)) {//检查是不是一个工作表配置
                    var sheetConfig = me.getSheetConfig(field);//获取工作表配置
                    workBookConfig.addSheetConfig(sheetConfig);//添加工作表配置进工作簿
                } else if (field.isAnnotationPresent(Cell.class)) {
                    var cellConfig = me.getCellConfig(field);//单元格配置
                    workBookConfig.addCellConfig(cellConfig);
                }
            }
            tempType = tempType.getSuperclass();
        }
    }

    /**
     * 获得属性的工作表配置信息
     *
     * @param field 单元表对应的属性
     * @return 工作表配置信息
     */
    private SheetConfig getSheetConfig(Field field) {
        var sheet = field.getAnnotation(Sheet.class);//工作表注解
        var repeatable = field.getAnnotation(Repeatable.class);//是否重复

        var sheetConfig = SheetConfig.createSheetConfig(sheet, this.getDataGetter(field), this.getDataSetter(field), field.getType());//构建单元表
        Class<?> fieldType;
        if (repeatable == null) {
            fieldType = field.getType();
        } else {
            sheetConfig.setRepeatConfig(new RepeatConfig(repeatable));//是否重复
            if (Iterable.class.isAssignableFrom(field.getType())) {
                if (ParameterizedType.class.isAssignableFrom(field.getGenericType().getClass())) {
                    fieldType = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                } else {
                    throw new RuntimeException(field.getName() + "未找到泛型设置");
                }
            } else {
                throw new RuntimeException(field.getType().getName() + "不是一个有效的Iterable对象");
            }
        }
        this.generateSheetCellConfig(sheetConfig, fieldType);
        return sheetConfig;
    }

    private void generateSheetCellConfig(SheetConfig sheetConfig, Class<?> fieldType) {
        var cache = me.sheetConfigCache.get(fieldType.getName());
        if (cache == null) {
            cache = new CellSheetCache();
            var tempType = fieldType;
            while (tempType != Object.class) {
                var fields = tempType.getDeclaredFields();//获得所有属性
                for (var field : fields) {
                    if (field.isAnnotationPresent(Cell.class)) {
                        cache.cellConfigList.add(this.getCellConfig(field));//添加一个单元格信息
                    } else if (field.isAnnotationPresent(Sheet.class)) {
                        cache.sheetConfigLis.add(this.getSheetConfig(field));//嵌入另外一个工作表
                    }
                }
                tempType = tempType.getSuperclass();
            }
            System.out.println(fieldType.getName());
            me.sheetConfigCache.put(fieldType.getName(), cache);
        }
        sheetConfig.getRefSheetConfigs().addAll(cache.sheetConfigLis);
        sheetConfig.getCellConfigs().addAll(cache.cellConfigList);
    }

    /**
     * 获取属性单元格配置信息
     *
     * @param field 属性对象
     * @return 属性对应的单元格配置
     */
    private CellConfig getCellConfig(Field field) {
        var cell = field.getAnnotation(Cell.class);//表格坐标
        var repeatable = field.getAnnotation(Repeatable.class);//是否循环
        var mergedPosition = field.getAnnotation(MergedPosition.class);//是否合并单元格
        var mergedFormat = field.getAnnotation(MergedFormat.class);//合并单元格格式化注解
        var formatter = field.getAnnotation(Formatter.class);//单元格内容格式化注解
        return new CellConfig(cell, repeatable, mergedFormat, mergedPosition, formatter, this.getDataGetter(field), this.getDataSetter(field), field.getType());
    }

    private Function<Object, Object> getDataGetter(Field field) {
        field.setAccessible(true);
        return (source) -> {
            try {
                return field.get(source);
            } catch (IllegalAccessException | IllegalArgumentException e) {
                e.printStackTrace();
            }
            return null;
        };
    }

    private BiConsumer<Object, Object> getDataSetter(Field field) {
        field.setAccessible(true);
        return (source, data) -> {
            try {
                if (field.getName().equals("merge")) {
                    System.out.println(field.getType());
                }
                field.set(source, data);
            } catch (IllegalAccessException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        };
    }

    private static class CellSheetCache {
        public List<CellConfig> cellConfigList = new ArrayList<>();
        public List<SheetConfig> sheetConfigLis = new ArrayList<>();
    }


}
