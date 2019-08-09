package ru.zubmike.core.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class IOUtils {

	public static <T extends Serializable> byte[] serialize(Serializable object) {
		try (ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
		     ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream)) {
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
		try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes)) {
			return deserialize(byteArrayInputStream);
		} catch (IOException e) {
			throw new InternalException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T deserialize(InputStream inputStream) {
		try (ObjectInputStream objectOutputStream = new ObjectInputStream(inputStream)) {
			return (T) objectOutputStream.readObject();
		} catch (ClassNotFoundException | IOException e) {
			throw new InternalException(e);
		}
	}

	public static byte[] compress(byte[] bytes) {
		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		     GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
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
		try (GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(bytes));
		     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
			byte[] buffer = new byte[1024];
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
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			byte[] buffer = new byte[4096];
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
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			int length;
			byte[] buffer = new byte[4096];
			while ((length = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, length);
			}
			outputStream.flush();
			return outputStream.toByteArray();
		} catch (IOException e) {
			throw new InternalException(e);
		}
	}

}
