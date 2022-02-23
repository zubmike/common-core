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
	private static final String TEST_STRING_HEX = "4c6f72656d20497073756d2069732073696d706c792064756d6d792074657874206f6620746865207072696e74696e6720616e64207479706573657474696e6720696e6475737472792e";

	private static final String TEST_RU_STRING = "Съешь ещё этих мягких французских булок.";
	private static final String TEST_RU_STRING_HEX = "d0a1d18ad0b5d188d18c20d0b5d189d19120d18dd182d0b8d18520d0bcd18fd0b3d0bad0b8d18520d184d180d0b0d0bdd186d183d0b7d181d0bad0b8d18520d0b1d183d0bbd0bed0ba2e";

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

	@Test
	public void hexValue() {
		var hexValue = IOUtils.toHex(TEST_STRING.getBytes(StandardCharsets.UTF_8));
		Assert.assertEquals(TEST_STRING_HEX, hexValue);
		var textValue = new String(IOUtils.parseHex(hexValue), StandardCharsets.UTF_8);
		Assert.assertEquals(TEST_STRING, textValue);

		Assert.assertEquals("ab", IOUtils.toHex((byte) 0xab));
		Assert.assertEquals("0f", IOUtils.toHex((byte) 0xf));
	}

	@Test
	public void hexRuValue() {
		var hexValue = IOUtils.toHex(TEST_RU_STRING.getBytes(StandardCharsets.UTF_8));
		Assert.assertEquals(TEST_RU_STRING_HEX, hexValue);
		var textValue = new String(IOUtils.parseHex(hexValue), StandardCharsets.UTF_8);
		Assert.assertEquals(TEST_RU_STRING, textValue);
	}

	@Test
	public void hexValueWithSeparator() {
		var hex = IOUtils.toHex("qwerty".getBytes(StandardCharsets.UTF_8), ' ');
		Assert.assertEquals("71 77 65 72 74 79", hex);
	}

	@Test
	public void equalsByteAndHexValues() {
		Assert.assertTrue(IOUtils.equalHex((byte) 0xab, "ab"));
		Assert.assertTrue(IOUtils.equalHex((byte) 0xab, "ab".toUpperCase()));

		Assert.assertFalse(IOUtils.equalHex((byte) 0xff, "fe"));
	}

	@Test
	public void convertHexToInt() {
		Assert.assertEquals(10, IOUtils.toDec('a'));
		Assert.assertEquals(11, IOUtils.toDec('b'));
	}

	@Test
	public void convertByteToInt() {
		Assert.assertEquals(0xabcd, IOUtils.toDec((byte) 0xab, (byte) 0xcd));
		Assert.assertEquals(0xabcd, IOUtils.toDec(new byte[] {(byte) 0xab, (byte) 0xcd}));
		Assert.assertEquals(0xabcddcba, IOUtils.toDec(new byte[] {(byte) 0xab, (byte) 0xcd, (byte) 0xdc, (byte) 0xba}));
	}

	@Test(expected = IllegalArgumentException.class)
	public void convertLargeByteArrayToInt() {
		IOUtils.toDec(new byte[] {1, 2, 3, 4, 5});
	}

	@Test
	public void conventByteToBit() {
		Assert.assertEquals(1, IOUtils.toBit(13, 0));
		Assert.assertEquals(0, IOUtils.toBit(13, 1));
		Assert.assertEquals(1, IOUtils.toBit(13, 2));
		Assert.assertEquals(1, IOUtils.toBit(13, 3));
		Assert.assertEquals(0, IOUtils.toBit(13, 4));
	}
}
