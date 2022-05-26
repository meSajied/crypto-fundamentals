import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class AESKeyGenerator {
  private Cipher cipher;
  private SecretKey key;
  private IvParameterSpec iv;
  
  public void init() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
    keyGenerator.init(128); // init 256, When use 256 bit...
    key = keyGenerator.generateKey();
    iv = generateIv();

    // AES/ECB/NoPadding, When use ECB...
    cipher = Cipher.getInstance("AES/CFB/NoPadding");
  }

  public String encryptToTheFile(String data) throws NoSuchAlgorithmException, 
      InvalidKeyException, IllegalBlockSizeException, 
      BadPaddingException, InvalidAlgorithmParameterException {
        cipher.init(Cipher.ENCRYPT_MODE, key, iv); // remove iv, When use ECB...
    byte[] encrypted = cipher.doFinal(data.getBytes());

    return Base64.getEncoder().encodeToString(encrypted);
  }

  public String decrypt(String data) throws InvalidKeyException, 
      InvalidAlgorithmParameterException, IllegalBlockSizeException, 
      BadPaddingException {
    cipher.init(Cipher.DECRYPT_MODE, key, iv);
    byte[] text = cipher.doFinal(Base64.getDecoder().decode(data));
    
    return new String(text);
  }

  private IvParameterSpec generateIv() {
    byte[] iv = new byte[16];
    new SecureRandom().nextBytes(iv);
    return new IvParameterSpec(iv);
  }

  public static void main(String[] args) throws InvalidKeyException, 
      NoSuchAlgorithmException, NoSuchPaddingException, IOException, 
      IllegalBlockSizeException, BadPaddingException, 
      InvalidAlgorithmParameterException {
    AESKeyGenerator generator = new AESKeyGenerator();
    generator.init();

    String data = Files.readString(Path.of("text.txt"));
    String encryptedData = generator.encryptToTheFile(data);
    Files.writeString(Path.of("encryptAES"), encryptedData);

    String codeMessage = Files.readString(Path.of("encryptAES"));
    System.out.println(generator.decrypt(codeMessage));
    
  }
  
}
