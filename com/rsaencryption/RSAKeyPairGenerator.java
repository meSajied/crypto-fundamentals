import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.PrivateKey;

public class RSAKeyPairGenerator {
  private PublicKey publicKey;
  private PrivateKey privateKey;
  public RSAKeyPairGenerator() throws NoSuchAlgorithmException {
    KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
    generator.initialize(1024);

    KeyPair pair = generator.generateKeyPair();

    publicKey = pair. getPublic();
    privateKey = pair.getPrivate();
  }

  public void storeKeysInTheFiles() throws FileNotFoundException {
    FileOutputStream fosPublicKey = new FileOutputStream("publicKey");
    FileOutputStream fosPrivateKey = new FileOutputStream("privateKey");

    try {
      fosPublicKey.write(publicKey.getEncoded());
      fosPrivateKey.write(privateKey.getEncoded());

      fosPublicKey.close();
      fosPrivateKey.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public PublicKey getPublicKey() {
    return publicKey;
  }

  public PrivateKey getPrivateKey() {
    return privateKey;
  }
}