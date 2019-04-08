package formatter;

import cn.irving.zhao.util.poi.formatter.RepeatIVFormatter;
import entity.WorkbookContainer;

public class RepeatIvFormatter implements RepeatIVFormatter {

    @Override
    public int[] getRowColIv(int loop, Object source) {
        WorkbookContainer work = (WorkbookContainer) source;
        int length = work.getEntity4List().get(loop).getEntity5().size();
        return new int[]{length, 0};
    }

}
