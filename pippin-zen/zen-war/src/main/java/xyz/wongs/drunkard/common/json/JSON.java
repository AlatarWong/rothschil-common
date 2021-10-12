package xyz.wongs.drunkard.common.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * JSON解析处理
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/10/9 - 21:30
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class JSON {
    public static final String DEFAULT_FAIL = "\"Parse failed\"";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final ObjectWriter OBJECT_WRITER = OBJECT_MAPPER.writerWithDefaultPrettyPrinter();

    public static void marshal(File file, Object value) throws Exception {
        try {
            OBJECT_WRITER.writeValue(file, value);
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    public static void marshal(OutputStream os, Object value) throws Exception {
        try {
            OBJECT_WRITER.writeValue(os, value);
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    public static String marshal(Object value) throws Exception {
        try {
            return OBJECT_WRITER.writeValueAsString(value);
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    public static byte[] marshalBytes(Object value) throws Exception {
        try {
            return OBJECT_WRITER.writeValueAsBytes(value);
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    public static <T> T unmarshal(File file, Class<T> valueType) throws Exception {
        try {
            return OBJECT_MAPPER.readValue(file, valueType);
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    public static <T> T unmarshal(InputStream is, Class<T> valueType) throws Exception {
        try {
            return OBJECT_MAPPER.readValue(is, valueType);
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    public static <T> T unmarshal(String str, Class<T> valueType) throws Exception {
        try {
            return OBJECT_MAPPER.readValue(str, valueType);
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    public static <T> T unmarshal(byte[] bytes, Class<T> valueType) throws Exception {
        try {
            if (bytes == null) {
                bytes = new byte[0];
            }
            return OBJECT_MAPPER.readValue(bytes, 0, bytes.length, valueType);
        } catch (IOException e) {
            throw new Exception(e);
        }
    }
}
