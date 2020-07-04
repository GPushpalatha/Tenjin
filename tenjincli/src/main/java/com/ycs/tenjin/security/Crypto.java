/***

Yethi Consulting Private Ltd. CONFIDENTIAL


Name of this file:  Crypto.java


Module: TENJIN - INTELLIGENT ENTERPRISE TESTING ENGINE


Copyright � 2016-17 by Yethi Consulting Private Ltd.


This source file is part of the TENJIN Software Product and System 
and is copyrighted by Yethi Consulting Private Ltd.

All rights reserved.  No part of this work may be reproduced, copied, 
duplicated, adopted, distributed, reverse engineered, stored in a retrieval  
system, transmitted in any form or by any means, electronic, 
mechanical, photographic, graphic, optic recording or otherwise, translated 
in any language or computer language, sold, rented, leased without the prior 
written permission of Yethi Consulting Services Private Ltd.

Notice: All information and source code contained in this file is, and remains 
the property of Yethi Consulting Services Private Ltd., and its suppliers, if any. 
The intellectual and technical concepts contained herein are proprietary to Yethi 
Consulting Services Private Ltd., and its suppliers and may be covered under patents 
and patents in process and are protected by trade secret or copyright laws. Dissemination 
of this information or reproduction of this material is strictly forbidden unless prior 
written permission is obtained from Yethi Consulting Services Private Ltd. 


Yethi Consulting Private Ltd.
# 1308, 4th Floor, Shetty Plaza,JB Nagar Main Road,
HAL 3rd Stage, Bangalore - 560 075,
Karnataka-560075,India
	 
*	 
*/

/******************************************
* CHANGE HISTORY
* ==============
*
* DATE                 CHANGED BY              DESCRIPTION
* 
*/

package com.ycs.tenjin.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import static org.apache.commons.codec.binary.Hex.*;
import sun.misc.*;

/* Change History
 * 02-Mar-2015 Authentication Changes (Mahesh): User/AUT/Schema Password Encryption/Decryption changes
 *
*/

public class Crypto {
	private static final String ALGORITHM = "AES";

	/*
	 * Generate a Key(generate random key) 
	 * Out Param - Generated random key in format:"[91, 66, 64, 51, 53, 100, 51, 53, 99, 50, 100]"
	 * 1. Generate key using KeyGenerator class
	 * 2. Key generators are constructed using one of the getInstance class methods of this class. 
	 * 3. Returns a KeyGenerator object that generates secret keys for the specified algorithm.
	 * 4. Constructs a secure random number generator. 
	 * 5. Initializes this key generator for a certain keysize. 
	 * 6. Generates a secret key using generateKey() method.
	 * 4. Returns the keys.
	 */
	private Key generateKey() throws NoSuchAlgorithmException
	{
		KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
		SecureRandom random = new SecureRandom();
		keyGen.init(128);
		keyGen.init(random);
		SecretKey secretKey = keyGen.generateKey();
		Key key = secretKey;
		return key;
	}
	
	/*
	 * Generate a salt(generate random data) 
	 * Out Param - Generated salt in format:"4acfedc7dc72a9003a0dd721d7642bde"
	 * 1. Generate salt using SecureRandom class
	 * 2. Fills the elements of a specified array of bytes with random numbers using random.nextBytes method.
	 * 3. converts bytes into String.
	 * 4. Returns the salt values randomly generated data.
	 */
	public String generateSalt() 
	{
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[20];
		random.nextBytes(bytes);
		String s = new String(bytes);
		return s;
	}
	
	/* In Param - password string
	 * Out Param -Containing ENCRYPTED KEY and PASSWORD. 
	 * 1. Generate a key
	 * 2. Generate a salt
	 * 3. Obtain an instance of Cipher for AES Algorithm
	 * 4. Initiate Cipher in ENCRYPT mode using the generated key
	 * 5. Hash the password using the generated salt and given password value
	 * 6. Populate the key (in encrypted form) and the encrypted password
	 */
	public String encrypt(String value) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		//HashMap<String, String> encrypt = new HashMap<String, String>();
		Key key = generateKey();
		String salt = generateSalt();
		Cipher c = Cipher.getInstance(ALGORITHM);
		c.init(Cipher.ENCRYPT_MODE, key);
		/*encrypt.put("ENCRYPTED_KEY", key.getEncoded().toString());
		encrypt.put("ENCRYPTED_PASSWORD", new BASE64Encoder().encode(c.doFinal((salt + value).getBytes())));*/
	   //System.out.println("Key Array is: " +Arrays.toString(key.getEncoded()));
		return String.valueOf(encodeHex(key.getEncoded())) + "!#!" + new BASE64Encoder().encode(c.doFinal((salt + value).getBytes()));
	}
	
	/* In Param - password string and keybytes (contains bytes of keys:[91, 66, 64, 53, 49, 55, 56, 101, 102, 100, 53])
	 * Out Param - Generated salt in format:"4acfedc7dc72a9003a0dd721d7642bde"
	 * 1. key - Convert bytes into key using SecretKeySpec class  
	 * 2. Generate a salt - to compare existing salt value
	 * 3. Obtain instance of Cipher for AES Algorithm
	 * 4. Initiate Cipher in DECRYPT_MODE using the generated key
	 * 5. dValue - It returns decoded value
	 */
	public String decrypt(String value, byte[] keybytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException 
	{
		System.out.println("Input key bytes is: " +Arrays.toString(keybytes));
		Key key = new SecretKeySpec(keybytes, 0, keybytes.length, ALGORITHM);
		System.out.println("Generated key is: " +Arrays.toString(key.getEncoded()));
		String salt = generateSalt();
		Cipher c = Cipher.getInstance(ALGORITHM);
		c.init(Cipher.DECRYPT_MODE, key);
		String dValue = null ,valueToDecrypt = value;
		byte[] decodedValue = new BASE64Decoder().decodeBuffer(valueToDecrypt);
		byte[] decValue = c.doFinal(decodedValue);
		dValue = new String(decValue).substring(salt.length());
		return dValue;
	}
	
	/*- Tenjin User Password Authentication 
	 *  In Param - password string
	 *  Out Param -Containing Cryptographic hash function,Iteration Number,Encrypted key and PASSWORD. 
	 * 1. generate a random iterationNo.
	 * 2. Generate a salt
	 * 3. Get bytes of salt value
	 * 4. Get Hash of IterationNb,Password,and Salt
	 * 4. Generated Hash format:"[B@19509443"
	 * 5. Append SHA-512, iterationNo, Salt, and the hash password delimited by ! and store it has password
	 * 5. Populate the encrypted password
	 */
	public String encryptOneWay(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException 
	{
		int iterationNb=0;
		iterationNb=new Random().nextInt(100);
	    String encodedPassword = null;
	    // byte[] btPass={};
	    String saltvalue =generateSalt();
	    byte salt[]=saltvalue.getBytes();
	    String encrypted=String.valueOf(encodeHex(salt));
	    byte[] hashkey=getHash(iterationNb,password,salt); 
	    /*16-Mar-2015 R2.1:--By--Mahesh:Encrypt password with hashkey not with salt: Starts*/
	    //encodedPassword ="SHA-512"+"!#!"+iterationNb+"!#!"+encrypted+"!#!"+String.valueOf(encodeHex(salt));
	    encodedPassword ="SHA-512"+"!#!"+iterationNb+"!#!"+encrypted+"!#!"+String.valueOf(encodeHex(hashkey));
	    /*16-Mar-2015 R2.1:--By--Mahesh:Encrypt password with hashkey not with salt: Ends*/
	    System.out.println(encodedPassword);
	    return encodedPassword;
	}
	public String encryptOneWay(int iterationNb, byte[] salt, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException 
	{
		String encrypted=String.valueOf(encodeHex(salt));
		byte[] hashkey=getHash(iterationNb,password,salt);
		//23-Mar-2015 by Mahesh: for Defect:#1017: authentication failes:starts
		//String encodedPassword ="SHA-512"+"!#!"+iterationNb+"!#!"+encrypted+"!#!"+String.valueOf(encodeHex(salt));
		String encodedPassword ="SHA-512"+"!#!"+iterationNb+"!#!"+encrypted+"!#!"+String.valueOf(encodeHex(hashkey));
	   	//23-Mar-2015 by Mahesh: for Defect:#1017: authentication failes:Ends
	    return encodedPassword;
	}
	
	/*-Generate a Hash 
	 * In Param -Hash the password by passing the random iterationNo, Salt, and the user input password
	 *  Out Param -Containing Cryptographic hash function. 
	 * 1. Using MessageDigest get an instance for SHA-512 and 
	 * 2. Using MessageDigest update the salt value.
	 * 3. Input- returns the array of bytes for the resulting hash value.
	 */
	public byte[] getHash(int iterationNb, String password, byte[] salt) throws NoSuchAlgorithmException, UnsupportedEncodingException 
	 {
	       MessageDigest digest = MessageDigest.getInstance("SHA-512");
	       digest.reset();
	       digest.update(salt);
	       byte[] input = digest.digest(password.getBytes("UTF-8"));
	       for (int i = 0; i < iterationNb; i++) 
	       {
	    	   //System.out.println(Arrays.toString(input));
	           digest.reset();
	           input = digest.digest(input);
	       }
	       return input;
	   }
	public static void main(String args[]){
		 try {
			new Crypto().encryptOneWay("admin123");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
