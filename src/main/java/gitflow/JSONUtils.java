package gitflow;

import com.intellij.json.psi.JsonObject;
import com.intellij.json.psi.JsonProperty;
import com.intellij.json.psi.JsonPsiUtil;
import com.intellij.json.psi.JsonValue;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Optional;

/**
 * @author rubin 2022年01月12日
 */
public class JSONUtils {

    @SuppressWarnings("unchecked")
    public static <T> T getValue(JsonObject obj, String property, Class<T> clazz) {
        String valueStr = JsonPsiUtil.stripQuotes(Optional.ofNullable(obj.findProperty(property)).map(JsonProperty::getValue).map(JsonValue::getText).orElse(""));
        if(clazz == String.class) {
            return (T)valueStr;
        } else if(clazz == Boolean.class) {
            return (T)Boolean.valueOf(valueStr);
        }
        return (T)valueStr;
    }

}
