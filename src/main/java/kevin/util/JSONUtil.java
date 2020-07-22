package kevin.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

/**
 * JSON工具类
 * 对JSON进行统一处理
 * ObjectMapper 是一个使用 Swift 编写的用于 model 对象（类和结构体）和 JSON 之间转换的框架。
 * ObjectMapper 主要是可以和JSON之间转化
 */
public class JSONUtil {
    private static ObjectMapper MAPPER;
    static {
        MAPPER = new ObjectMapper();
        MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }
    public static <T> T read(InputStream is,Class<T> clazz){
        try {
            return MAPPER.readValue(is,clazz);
        } catch (IOException e) {
            throw new RuntimeException("json反序列化操作出错",e);
        }
    }
}
