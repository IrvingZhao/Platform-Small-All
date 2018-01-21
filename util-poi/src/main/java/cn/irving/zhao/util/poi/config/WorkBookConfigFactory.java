package cn.irving.zhao.util.poi.config;

import cn.irving.zhao.util.poi.annonation.*;
import cn.irving.zhao.util.poi.enums.SheetType;
import cn.irving.zhao.util.poi.inter.IWorkbook;
import org.apache.commons.collections4.map.ReferenceMap;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.function.Function;

/**
 * 工作簿配置工厂类。
 */
public final class WorkBookConfigFactory {

    private WorkBookConfigFactory() {
    }

    //TODO 将配置信息序列化后 写入 tmp dir
    private ReferenceMap classConfigCache = new ReferenceMap();

    private static final WorkBookConfigFactory me = new WorkBookConfigFactory();

    /**
     * 获取工作簿配置信息
     *
     * @param type 工作簿类型
     * @return 工作簿配置
     */
    public static WorkBookConfig getWorkbookConfig(Class<? extends IWorkbook> type) {
        WorkBookConfig result = null;
        Object tempConfig = me.classConfigCache.get(type.getName());//读取缓存
        if (tempConfig == null) {//缓存未命中
            result = new WorkBookConfig();
            Class<?> tempType = type;

            while (tempType != Object.class) {
                Field[] fields = tempType.getDeclaredFields();//获得属性
                for (Field field : fields) {
                    if (field.isAnnotationPresent(Sheet.class)) {//检查是不是一个工作表配置
                        SheetConfig sheetConfig = me.getSheetConfig(field);//获取工作表配置
                        if (sheetConfig.getSheetType() == SheetType.OUTER) {//检查工作表类型
                            result.addSheetConfig(sheetConfig);//添加外联工作表
                        } else if (sheetConfig.getSheetType() == SheetType.INNER) {
                            result.addDefaultInnerSheetConfig(sheetConfig);//添加内嵌工作表
                        }
                    } else if (field.isAnnotationPresent(Cell.class)) {
                        CellConfig cellConfig = me.getCellConfig(field);
                        result.addDefaultSheetCellConfig(cellConfig);
                    }
                }
                tempType = tempType.getSuperclass();
            }

            me.classConfigCache.put(type.getName(), result);//写入缓存

        } else {
            if (WorkBookConfig.class.isInstance(tempConfig)) {
                result = (WorkBookConfig) tempConfig;
            } else if (SheetCellConfig.class.isInstance(tempConfig)) {
                result = new WorkBookConfig();
                result.addDefaultInnerSheetConfig(SheetConfig.createInnerSheet(0, 0, (SheetCellConfig) tempConfig, null));
            }
        }

        return result;
    }

    /**
     * 获得属性的工作表配置信息
     *
     * @param field 单元表对应的属性
     * @return 工作表配置信息
     */
    private SheetConfig getSheetConfig(Field field) {
        Sheet sheet = field.getAnnotation(Sheet.class);//工作表注解
        Repeatable repeatable = field.getAnnotation(Repeatable.class);//是否重复

        SheetConfig sheetConfig = SheetConfig.createSheetConfig(sheet, me.getDataGetter(field));//构建单元表
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
        sheetConfig.setSheetCellConfig(getClassSheetConfig(fieldType));
        return sheetConfig;
    }

    /**
     * 获取属性单元格配置信息
     *
     * @param field 属性对象
     * @return 属性对应的单元格配置
     */
    private CellConfig getCellConfig(Field field) {
        Cell cell = field.getAnnotation(Cell.class);//表格坐标
        Repeatable repeatable = field.getAnnotation(Repeatable.class);//是否循环
        MergedRegion mergedRegion = field.getAnnotation(MergedRegion.class);//是否合并单元格
        Formatter formatter = field.getAnnotation(Formatter.class);
        return new CellConfig(cell, repeatable, mergedRegion, formatter, getDataGetter(field));
    }

    /**
     * 获得工作表中单元格配置信息
     *
     * @param type 工作表对象类型
     * @return 工作表配置信息
     */
    private SheetCellConfig getClassSheetConfig(Class<?> type) {
        SheetCellConfig result = new SheetCellConfig();//初始化
        Class<?> tempType = type;
        while (tempType != Object.class) {
            Field[] fields = tempType.getDeclaredFields();//获得所有属性
            for (Field field : fields) {
                if (field.isAnnotationPresent(Cell.class)) {
                    result.addCellConfig(getCellConfig(field));//添加一个单元格信息
                } else if (field.isAnnotationPresent(Sheet.class)) {
                    result.addSheetConfig(getSheetConfig(field));//嵌入另外一个工作表
                }
            }
            tempType = tempType.getSuperclass();
        }
        me.classConfigCache.put(type.getName(), result);//写入缓存
        return result;
    }

    private Function<Object, Object> getDataGetter(Field field) {
        return (source) -> {
            try {
                field.setAccessible(true);
                return field.get(source);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        };
    }


}
