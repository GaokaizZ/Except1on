package org.springrain.frame.util;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtils {
	public static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

	static ObjectMapper mapper = null;
	private static final String YYYYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";

	static {
		mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat(YYYYMMDD_HHMMSS));

		// 设置输出包含的属性
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		// 设置输入时忽略JSON字符串中存在而Java对象实际没有的属性
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		// 键支持不带双引号
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

		// mapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
	}

	public static <T> T readValue(String content, Class<T> clazz) {
		T t = null;
		try {
			t = mapper.readValue(content, clazz);
		} catch (JsonParseException e) {
			logger.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return t;
	}

	public static String writeValueAsString(Object o) {
		String str = null;
		try {
			str = mapper.writeValueAsString(o);
		} catch (JsonGenerationException e) {
			logger.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return str;
	}

	public static Object readValues(String content, Class<?> collectionType, Class<?>... clazz) {
		Object o = null;

		try {
			o = mapper.readValue(content, getCollectionType(collectionType, clazz));
		} catch (JsonParseException e) {
			logger.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		return o;
	}

	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}

}
