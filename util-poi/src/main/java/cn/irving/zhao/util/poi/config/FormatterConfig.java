package cn.irving.zhao.util.poi.config;

import cn.irving.zhao.util.poi.annonation.Formatter;
import cn.irving.zhao.util.poi.formatter.CellDataFormatter;
import cn.irving.zhao.util.poi.formatter.FormatterFactory;
import lombok.Getter;
import lombok.Setter;

/**
 * 单元格格式化配置信息
 */
@Getter
public class FormatterConfig {

    private static FormatterFactory<CellDataFormatter> factory = FormatterFactory.getFormatterFactory(CellDataFormatter.class);

    /**
     * 通过注解的方式进行配置
     */
    FormatterConfig(Formatter formatter) {
        this.formatString = formatter.cellFormatPattern();
        if (formatter.cellDataFormatter() != CellDataFormatter.class) {
            this.cellDataFormatter = factory.getFormatter(formatter.cellDataFormatter());
        } else {
            this.cellDataFormatter = null;
        }
    }

    FormatterConfig(String formatString, CellDataFormatter cellDataFormatter) {
        this.formatString = formatString;
        this.cellDataFormatter = cellDataFormatter;
    }

    FormatterConfig(String formatString) {
        this.formatString = formatString;
        this.cellDataFormatter = null;
    }

    public FormatterConfig(CellDataFormatter cellDataFormatter) {
        this.cellDataFormatter = cellDataFormatter;
        this.formatString = null;
    }

    private final String formatString;// excel 单元格格式化字符串

    private final CellDataFormatter cellDataFormatter;//单元格格式化转换

}
