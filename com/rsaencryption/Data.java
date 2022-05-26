import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Data {
  private Cipher encryptCipher, decryptCipher;
  private byte[] byteMessege;

  public Data() throws NoSuchAlgorithmException, InvalidKeySpecException, 
      NoSuchPaddingException, InvalidKeyException, FileNotFoundException, 
      NoSuchProviderException {
    RSAKeyPairGenerator generator = new RSAKeyPairGenerator();
    encryptCipher = Cipher.getInstance("RSA");
    decryptCipher = Cipher.getInstance("RSA");
    
    encryptCipher.init(Cipher.ENCRYPT_MODE, generator.getPublicKey());
    decryptCipher.init(Cipher.DECRYPT_MODE, generator.getPrivateKey());
  }

  public void encrypt() throws IllegalBlockSizeException, BadPaddingException, 
      IOException {
    Path filePath = Path.of("text.txt");
    String messege = Files.readString(filePath);

    byteMessege = encryptCipher.doFinal(messege.getBytes("UTF-8"));
  }

  public void decrypt() throws IOException, IllegalBlockSizeException, 
      BadPaddingException {
    Path filePath = Path.of("encryptedmessege.txt");
    String messege = Files.readString(filePath);
    System.out.println(decryptCipher.doFinal(messege.getBytes("UTF-8")));
  }

  public void writeIntoFile() throws IOException {
    String message = new String(byteMessege);
    try (FileWriter file = new FileWriter("encryptedmessege.txt")) {
      file.write(message);
      file.close();
    }
  }

  public static void main(String[] args) throws NoSuchAlgorithmException, 
      NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, 
      IllegalBlockSizeException, BadPaddingException, IOException, 
      NoSuchProviderException {
    RSAKeyPairGenerator gen = new RSAKeyPairGenerator();
    gen.storeKeysInTheFiles();
    Data data = new Data();

    data.encrypt();
    data.writeIntoFile();

    // RSA cant encypt or decrypt lomger file
    //data.decrypt();
  }
}
