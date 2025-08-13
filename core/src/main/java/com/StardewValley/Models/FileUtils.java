package com.StardewValley.Models;

import java.io.*;
import java.util.Base64;

public class FileUtils {

    /**
     * Serializes a Serializable object to a byte array.
     */
    public static byte[] serializeToBytes(Object object) throws IOException {
        if (!(object instanceof Serializable)) {
            throw new IllegalArgumentException("Object must implement Serializable");
        }

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(object);
            return bos.toByteArray();
        }
    }

    /**
     * Deserializes a byte array back to an object.
     */
    @SuppressWarnings("unchecked")
    public static <T> T deserializeFromBytes(byte[] data) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            return (T) ois.readObject();
        }
    }

    /**
     * Serializes an object to a Base64 String (good for text-based protocols).
     */
    public static String serializeToBase64(Object object) throws IOException {
        return Base64.getEncoder().encodeToString(serializeToBytes(object));
    }

    /**
     * Deserializes an object from a Base64 String.
     */
    public static <T> T deserializeFromBase64(String base64) throws IOException, ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(base64);
        return deserializeFromBytes(data);
    }
}
