package eu.novobit.dto.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.json.JSONObject;

import java.util.StringTokenizer;

/**
 * The type Menu tree dto.
 */
@Data
public class MenuTreeDto {


    private MenuDto root;

    /**
     * Instantiates a new Menu tree dto.
     */
    public MenuTreeDto() {
        root = new MenuDto("");
    }

    /**
     * Add.
     *
     * @param str the str
     */
    public void add(String str) {
        MenuDto current = root;
        StringTokenizer s = new StringTokenizer(str, "/");
        while (s.hasMoreElements()) {

            str = (String) s.nextElement();
            MenuDto child = current.getChild(str);

            if (child == null) {
                current.addChild(new MenuDto(str));
                child = current.getChild(str);
            }
            current = child;
        }
    }

    /**
     * To json json object.
     *
     * @return the json object
     */
    public JSONObject toJSON() {
        try {
            return new JSONObject(new ObjectMapper().writeValueAsString(this.root));
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}