package com.ace.encry;

import java.security.Key;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Coder {

	public static final String ALGORITHM = "DES";

	public static void main(String[] args) throws Exception {
		String data = "���ܲ���";
		String key = "123";

		// ����һ
		byte[] keys = encryptMD5(key.getBytes());
		byte[] enArr = encrypt(data.getBytes(), keys);
		String enStr = encryptBASE64(enArr);
		String deStr = new String(decrypt(enArr, keys));
		System.out.println("���ܺ�" + enStr);
		System.out.println("���ܺ�" + deStr);

		System.out.println("========================");
		// ������
		String enStr2 = encrypt(data, key);
		String deStr2 = decrypt(enStr2, key);
		System.out.println("���ܺ�2��" + enStr2);
		System.out.println("���ܺ�2��" + deStr2);
	}

	/**
	 * ����
	 * 
	 * @param data
	 *            ��������
	 * @param key
	 *            ��Կ(�Զ���)
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String data, String key) throws Exception {
		return encryptBASE64(encrypt(data.getBytes(),
				encryptMD5(key.getBytes())));
	}

	/**
	 * ����
	 * 
	 * @param data
	 * @param key
	 *            size>=8
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		Key k = genKey(key);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, k);
		return cipher.doFinal(data);
	}

	/**
	 * ����
	 * 
	 * @param data
	 *            ��������
	 * @param key
	 *            ��Կ(�������Կһ��)
	 * @return String
	 * @throws Exception
	 */
	public static String decrypt(String data, String key) throws Exception {
		return new String(decrypt(decryptBASE64(data),
				encryptMD5(key.getBytes())));
	}

	/**
	 * ����
	 * 
	 * @param data
	 * @param key
	 *            size>=8
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		Key k = genKey(key);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, k);
		return cipher.doFinal(data);
	}

	/**
	 * ������Կ
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private static Key genKey(byte[] key) throws Exception {
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		SecretKey secretKey = keyFactory.generateSecret(dks);
		return secretKey;
	}

	/**
	 * MD5����
	 * 
	 * @param data
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] encryptMD5(byte[] data) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(data);
		return md5.digest();
	}

	/**
	 * BASE64����
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	/**
	 * BASE64����
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) throws Exception {
		return (new BASE64Encoder()).encodeBuffer(key);
	}
}
