import java.security.MessageDigest;
import java.security.SecureRandom;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptDecrypt {
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 9; i++){
        	for (int j = 0; j < 9; j++){
        		for (int k = 0; k < 9; k++){
        			for (int l = 0; l < 9; l++){
        				String Pin = Integer.toString(i) + Integer.toString(j) + Integer.toString(k) + Integer.toString(l);
        				String finalPin = Pin + Pin + Pin + Pin;
        				try{

					// setup AES cipher in CBC mode with PKCS #5 padding
					Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

					String paramString2 = "1234567812345678"; //use the IV given in the apk code
					Charset charset = Charset.forName("UTF-8");
					byte[] arrayOfByte3 = paramString2.getBytes(charset);
					IvParameterSpec ivSpec = new IvParameterSpec(arrayOfByte3);

					byte[] arrayOfByte2 = finalPin.getBytes(charset);
					SecretKeySpec keySpec = new SecretKeySpec(arrayOfByte2, "AES");

					// decrypt
					String str1st = "CiMwl2sIlzwtT4Mdm0dmmtOVV79W1dV1kIhWVWJqcYaSZu0ti0aVIkFD6Gim3Uhx";
					String str2nd = "OpFUeq8AsLskv9nSZ1FHYRGvM912ufXYUGI82aiOeX7eFvno9VANOIyH9VXkRkeJYDD74nTLWF22pGsu1G6B4tKGNnjGZ9di1QEIhyDDoxU=";
				    	Base64.Decoder decoder = Base64.getDecoder();
				   	byte[] arrayOfStr1 = decoder.decode(str1st);
				   	byte[] arrayOfStr2 = decoder.decode(str2nd);
					cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
					byte[] decrypted = cipher.doFinal(arrayOfStr1);
					System.out.println("decrypted: " + new String(decrypted, "UTF-8")); //this is the key
					
					
					keySpec = new SecretKeySpec(decrypted, "AES");
					
					cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
					decrypted = cipher.doFinal(arrayOfStr2);
					System.out.println("decrypted: " + new String(decrypted, "UTF-8")); //this is the content, if we got this far without one of the below exceptions
					break;
					}
					catch (javax.crypto.BadPaddingException e){
						continue;
					}
					catch (java.security.InvalidKeyException e){
						continue;
					}
					
				}
			}
		}
	}
    }
}
