package com.fd.productfeed.utils;

import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

public class FDJacksonUtils {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	/**
	 * @return the mAPPER
	 */
	public static ObjectMapper getMAPPER() {
		return MAPPER;
	}

	public static String convertToJsonStr(Object model) throws IOException {
		return MAPPER.writeValueAsString(model);
	}

	/**
	 * Read from the String and convert into java bean
	 * 
	 * @param <T>
	 * @param content
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	public static <T> T readFromStr(String content, Class<T> clazz)
			throws IOException {
		MAPPER
				.configure(
						DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
						false);
		return MAPPER.readValue(content, clazz);
	}

	/**
	 * Read from the File and convert into java bean
	 * 
	 * @param <T>
	 * @param file
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	public static <T> T readFromFile(File file, Class<T> clazz)
			throws IOException {
		MAPPER
				.configure(
						DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
						false);
		return MAPPER.readValue(file, clazz);
	}

	/**
	 * Convert into java bean into json with pretty print
	 * 
	 * @param <T>
	 * @param object
	 * @return
	 * @throws IOException
	 */
	public static <T> String writeToStr(Object object) throws IOException {
		return MAPPER.defaultPrettyPrintingWriter().writeValueAsString(object);
	}
}