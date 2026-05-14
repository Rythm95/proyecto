package com.simao.tarea3AD2024base.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {

	public static String md5(String texto) {
		try {
			
			MessageDigest md = MessageDigest.getInstance("MD5");

			byte[] hash = md.digest(texto.getBytes());

			StringBuilder sb = new StringBuilder();

			for (byte b : hash) {
				sb.append(String.format("%02x", b));
			}

			return sb.toString();

		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
