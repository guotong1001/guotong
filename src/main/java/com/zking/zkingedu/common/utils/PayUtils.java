package com.zking.zkingedu.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class PayUtils {
    // 生成唯一编号
    public static String createUnilCode() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate = sdf.format(new Date());
        String result = "";
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            result += random.nextInt(10);
        }
        return newDate + result;
    }
}
