package cn.irving.zhao.util.base.zip;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Irving Zhao
 */
public class ZipUtils {

    public File zipFiles(String output, Map<String, File> entries, String password) {
        File result = new File(output);
        Map<String, InputStream> streamMap = new HashMap<>();
        entries.forEach((key, value) -> {
            try {
                streamMap.put(key, new FileInputStream(value));
            } catch (FileNotFoundException e) {
                throw new RuntimeException("文件读取异常", e);
            }
        });
        try {
            ZipFile zipFile = new ZipFile(result);
            addZipEntries(zipFile, streamMap, password);
        } catch (ZipException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void addZipEntries(ZipFile zipFile, Map<String, InputStream> entries, String password) {
        try {
            zipFile.setFileNameCharset("UTF8");
            for (Map.Entry<String, InputStream> item : entries.entrySet()) {
                zipFile.addStream(item.getValue(), generateZipParam(item.getKey(), password));
            }
        } catch (ZipException e) {
            e.printStackTrace();
            throw new RuntimeException("压缩包添加文件异常", e);
        }
    }

    private ZipParameters generateZipParam(String name, String password) {
        ZipParameters result = new ZipParameters();
        if (password != null && !password.trim().equals("")) {
            result.setPassword(password);
            result.setEncryptFiles(true);
            result.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
            result.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
        }
        result.setSourceExternalStream(true);
        result.setFileNameInZip(name);
        return result;
    }

    public static void main(String[] args) throws ZipException, IOException {
        File file = new File("d:/basepath/basepath.zip");
//        if (file.exists()) {
//            file.delete();
//        }
//        file.createNewFile();
        ZipFile zipFile = new ZipFile(file);
        ZipParameters parameters = new ZipParameters();
        parameters.setPassword("123456");
        parameters.setEncryptFiles(true);
        parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
        parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
        parameters.setFileNameInZip("测试名称");
        zipFile.setFileNameCharset("UTF8");
//        zipFile.createZipFile(file, parameters);
        System.out.println("1");
        File in = new File("d:/basepath/b.xlsx");
//        zipFile.addFile(in, parameters);
        parameters.setSourceExternalStream(true);
        zipFile.addStream(new FileInputStream(in), parameters);
        System.out.println("2");

//        zipFile.setPassword("123456");
//        zipFile.getFileHeaders().stream().forEach(item -> {
//            FileHeader fileHeader = (FileHeader) item;
//            try {
////                fileHeader.setPassword("123456".toCharArray());
//                System.out.println(fileHeader.getFileName());
//                InputStream inputStream = zipFile.getInputStream(fileHeader);
//            } catch (ZipException e) {
//                e.printStackTrace();
//            }
//        });
    }
}
