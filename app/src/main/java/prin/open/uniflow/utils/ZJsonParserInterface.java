package prin.open.uniflow.utils;

import java.util.Map;

public interface ZJsonParserInterface {

	public <T> T jsonToModel(String json, Class<T> clazz);

	public Map<String, Object> jsonToMap(String json);

	public String objToJson(Object obj);
}
