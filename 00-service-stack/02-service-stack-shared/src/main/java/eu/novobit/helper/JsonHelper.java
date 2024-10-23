package eu.novobit.helper;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The type Json helper.
 */
@Slf4j
@Component
public final class JsonHelper {

    private JsonHelper() {
        super();
    }

    /**
     * From json t.
     *
     * @param <T>       the type parameter
     * @param json      the json
     * @param valueType the value type
     * @return the t
     * @throws JsonParseException   the json parse exception
     * @throws JsonMappingException the json mapping exception
     * @throws IOException          the io exception
     */
    public static <T> T fromJson(String json, Class<T> valueType) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, valueType);
    }

    /**
     * To json string.
     *
     * @param obj the obj
     * @return the string
     * @throws JsonGenerationException the json generation exception
     * @throws JsonMappingException    the json mapping exception
     * @throws IOException             the io exception
     */
    public static String toJson(Object obj) throws JsonGenerationException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

    /**
     * From json file t.
     *
     * @param <T>        the type parameter
     * @param jsonReader the json reader
     * @param valueType  the value type
     * @return the t
     * @throws JsonParseException   the json parse exception
     * @throws JsonMappingException the json mapping exception
     * @throws IOException          the io exception
     */
    public static <T> T fromJsonFile(FileReader jsonReader, Class<T> valueType) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonReader, valueType);
    }

    /**
     * To json file.
     *
     * @param jsonWriter the json writer
     * @param obj        the obj
     * @throws JsonParseException   the json parse exception
     * @throws JsonMappingException the json mapping exception
     * @throws IOException          the io exception
     */
    public static void toJsonFile(FileWriter jsonWriter, Object obj) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(jsonWriter, obj);
    }
}
