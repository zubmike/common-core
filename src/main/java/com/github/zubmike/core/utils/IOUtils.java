package com.github.zubmike.core.utils;

import javax.validation.constraints.Null;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class IOUtils {

	private static final int DEFAULT_BYTE_BUFFER_SIZE = 4096;

	private static final char[] HEX_VALUES = "0123456789abcdef".toCharArray();

	public static <T extends Serializable> byte[] serialize(T object) {
		try (var byteOutputStream = new ByteArrayOutputStream();
		     var objectOutputStream = new ObjectOutputStream(byteOutputStream)) {
			objectOutputStream.writeObject(object);
			return byteOutputStream.toByteArray();
		} catch (IOException e) {
			throw new InternalException(e);
		}
	}

	public static String serializeToBase64String(Serializable object) {
		return Base64.getEncoder().encodeToString(serialize(object));
	}

	public static <T extends Serializable> T deserialize(byte[] bytes) {
		try (var byteArrayInputStream = new ByteArrayInputStream(bytes)) {
			return deserialize(byteArrayInputStream);
		} catch (IOException e) {
			throw new InternalException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T deserialize(InputStream inputStream) {
		try (var objectOutputStream = new ObjectInputStream(inputStream)) {
			return (T) objectOutputStream.readObject();
		} catch (ClassNotFoundException | IOException e) {
			throw new InternalException(e);
		}
	}

	public static byte[] compress(byte[] bytes) {
		try (var byteArrayOutputStream = new ByteArrayOutputStream();
		     var gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
			gzipOutputStream.write(bytes);
			gzipOutputStream.close();
			return byteArrayOutputStream.toByteArray();
		} catch (IOException e) {
			throw new InternalException(e);
		}
	}

	public static String compressToString(byte[] bytes) {
		return new String(compress(bytes), StandardCharsets.UTF_8);
	}

	public static String encodeBase64FromString(String text) {
		return encodeBase64(text.getBytes(StandardCharsets.UTF_8));
	}

	public static String encodeBase64(byte[] bytes) {
		return Base64.getEncoder().encodeToString(bytes);
	}

	public static String decodeBase64ToString(String text) {
		return new String(decodeBase64(text), StandardCharsets.UTF_8);
	}

	public static byte[] decodeBase64(String text) {
		return Base64.getDecoder().decode(text);
	}

	public static byte[] decompress(byte[] bytes) {
		try (var gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(bytes));
		     var byteArrayOutputStream = new ByteArrayOutputStream()) {
			byte[] buffer = new byte[DEFAULT_BYTE_BUFFER_SIZE];
			int len;
			while ((len = gzipInputStream.read(buffer)) > 0) {
				byteArrayOutputStream.write(buffer, 0, len);
			}
			return byteArrayOutputStream.toByteArray();
		} catch (IOException e) {
			throw new InternalException(e);
		}
	}

	public static String toString(InputStream inputStream) {
		try (var outputStream = new ByteArrayOutputStream()) {
			byte[] buffer = new byte[DEFAULT_BYTE_BUFFER_SIZE];
			int length;
			while ((length = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, length);
			}
			return outputStream.toString(StandardCharsets.UTF_8.name());
		} catch (IOException e) {
			throw new InternalException(e);
		}
	}

	public static byte[] toBytes(InputStream inputStream) {
		try (var outputStream = new ByteArrayOutputStream()) {
			byte[] buffer = new byte[DEFAULT_BYTE_BUFFER_SIZE];
			int length;
			while ((length = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, length);
			}
			outputStream.flush();
			return outputStream.toByteArray();
		} catch (IOException e) {
			throw new InternalException(e);
		}
	}

	public static boolean equalHex(byte value, String hexValue) {
		String otherValue = toHex(value);
		return hexValue.equalsIgnoreCase(otherValue);
	}

	public static String toHex(byte value) {
		String hex = String.valueOf(HEX_VALUES[(value >> 4) & 0xf]) + HEX_VALUES[(value & 0xf)];
		return hex.length() > 1 ? hex : "0" + hex;
	}

	public static String toHex(byte[] bytes) {
		return toHex(bytes, null);
	}

	public static String toHex(byte[] bytes, @Null Character separator) {
		var stringBuilder = new StringBuilder();
		boolean useSeparator = separator != null;
		for (byte b : bytes) {
			String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1) {
				stringBuilder.append('0');
			}
			stringBuilder.append(hex);
			if (useSeparator) {
				stringBuilder.append(separator);
			}
		}
		if (useSeparator) {
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		}
		return stringBuilder.toString();
	}

	public static byte[] parseHex(String value) {
		int length = value.length();
		if (length % 2 != 0) {
			throw new IllegalArgumentException("hexBinary needs to be even-length: " + value);
		}
		byte[] out = new byte[length / 2];
		for (int i = 0; i < length; i +=2) {
			int h = toDec(value.charAt(i));
			int l = toDec(value.charAt(i + 1));
			if (h ==-1 || l == -1) {
				throw new IllegalArgumentException("contains illegal character for hexBinary: " + value);
			}
			out[i / 2] = (byte) (h * 16 + l);
		}
		return out;
	}

	public static int toDec(char value) {
		if ('0' <= value && value <= '9' )
			return value-'0';
		if ('A' <= value && value <= 'F' )
			return value-'A'+10;
		if ('a' <= value && value <= 'f' )
			return value-'a'+10;
		return -1;
	}

	public static int toDec(byte v1, byte v2) {
		return ((v1 & 0xff) << 8) | (v2 & 0xff);
	}

	public static int toDec(byte[] bytes) {
		if (bytes.length > 4) {
			throw new IllegalArgumentException("Array is too big");
		}
		int result = 0;
		for (byte value : bytes) {
			result = result << 8;
			result = result | (value & 0xff);
		}
		return result;
	}

	public static int toBit(int value, int index) {
		return (value >> index) & 1;
	}

	public static String hashSha256(File file) {
		try (var inputStream = new FileInputStream(file)) {
			var messageDigest = MessageDigest.getInstance("SHA-256");
			byte[] buffer = new byte[DEFAULT_BYTE_BUFFER_SIZE];
			int length;
			while ((length = inputStream.read(buffer)) != -1) {
				messageDigest.update(buffer, 0, length);
			}
			return toHex(messageDigest.digest());
		} catch (Exception e) {
			throw new InternalException(e);
		}
	}

	public static String hashSha256(byte[] bytes) {
		try {
			var messageDigest = MessageDigest.getInstance("SHA-256");
			return toHex(messageDigest.digest(bytes));
		} catch (Exception e) {
			throw new InternalException(e);
		}
	}

	public static String hashSha256(String value) {
		return hashSha256(value.getBytes(StandardCharsets.UTF_8));
	}

}
