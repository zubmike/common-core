package com.github.zubmike.core.utils;

import com.github.zubmike.core.types.BasicDictItem;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class IOUtilsTest {

	private static final String TEST_STRING = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.";

	@Test
	public void serializeDeserialize() {
		BasicDictItem item = new BasicDictItem(123, "Test");
		byte[] serializedItem = IOUtils.serialize(item);
		BasicDictItem deserializedItem = IOUtils.deserialize(serializedItem);
		Assert.assertEquals(item, deserializedItem);
	}

	@Test
	public void convertBytesToString() {
		byte[] testBytes = TEST_STRING.getBytes(StandardCharsets.UTF_8);
		try (InputStream inputStream = new ByteArrayInputStream(testBytes)) {
			String testString = IOUtils.toString(inputStream);
			Assert.assertEquals(TEST_STRING, testString);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
