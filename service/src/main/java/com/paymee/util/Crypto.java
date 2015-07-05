package com.paymee.util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import java.util.Scanner;

public class Crypto {

    public static void main(String[] args) {
        final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("password@aws");
		Scanner scanner = new Scanner(System.in);
		String line;
		while ((line = scanner.nextLine()) != null) {
			String[] split = line.split(" ");
			if (split[0].equals("enc")) {
				System.out.println(encryptor.encrypt(split[1]));
			} else {
				System.out.println(encryptor.decrypt(split[1]));
			}
		}
    }
}
