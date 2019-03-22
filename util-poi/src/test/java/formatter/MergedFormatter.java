package formatter;

import cn.irving.zhao.util.poi.formatter.MergedRegionFormatter;
import entity.Entity4;

public class MergedFormatter implements MergedRegionFormatter {
    @Override
    public int[] endIndex(Object source) {
//        System.out.println(source.getClass());
        Entity4 e4 = (Entity4) source;
        return new int[]{e4.getEntity5().size() - 1, 0};
    }
}
