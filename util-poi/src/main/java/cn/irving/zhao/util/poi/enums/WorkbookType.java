package cn.irving.zhao.util.poi.enums;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * 工作簿文件类型
 */
public enum WorkbookType {
    /**
     * excel2003
     */
    XLS {
        @Override
        public Workbook getWorkbook(InputStream template) {
            if (template == null) {
                return new HSSFWorkbook();
            } else {
                try {
                    return new HSSFWorkbook(template);
                } catch (IOException e) {
                    LOGGER.warn("模板流读取异常", e);
                    throw new RuntimeException("模板流读取异常", e);
                }
            }
        }
    },
    /**
     * excel2007+
     */
    XLSX {
        @Override
        public Workbook getWorkbook(InputStream template) {
            if (template == null) {
                return new XSSFWorkbook();
            } else {
                try {
                    return new XSSFWorkbook(template);
                } catch (IOException e) {
                    LOGGER.warn("模板流读取异常", e);
                    throw new RuntimeException("模板流读取异常", e);
                }
            }
        }
    },
    /**
     * 异步excel2007+
     */
    SXLSX {
        @Override
        public Workbook getWorkbook(InputStream template) {
            if (template == null) {
                return new SXSSFWorkbook();
            } else {
                try {
                    return new SXSSFWorkbook(new XSSFWorkbook(template));
                } catch (IOException e) {
                    LOGGER.warn("模板流读取异常", e);
                    throw new RuntimeException("模板流读取异常", e);
                }
            }
        }
    };

    public abstract Workbook getWorkbook(InputStream template);

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkbookType.class);
}
