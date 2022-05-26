import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;

public class RSASignature {
  private RSAKeyPairGenerator generator;
  
  public RSASignature() throws NoSuchAlgorithmException, FileNotFoundException {
    generator = new RSAKeyPairGenerator();
    generator.storeKeysInTheFiles();
  }

  public void signToFile(String file) throws IOException, 
      NoSuchAlgorithmException, InvalidKeyException, SignatureException {
    Path filePath = Path.of(file);
    String messege = Files.readString(filePath);
    Signature sign = Signature.getInstance("SHA256withRSA");
    FileOutputStream signatureFile = new FileOutputStream("rsaSignature");
    sign.initSign(generator.getPrivateKey());
    sign.update(messege.getBytes());

    byte[] signature = sign.sign();
    signatureFile.write(signature);
    signatureFile.close();
  }

  public boolean verify(String file, String signatureFile) throws 
      InvalidKeyException, NoSuchAlgorithmException, IOException, 
      SignatureException {
    Signature publicSignature = Signature.getInstance("SHA256withRSA");
    Path filePath = Path.of(file);
    String messege = Files.readString(filePath);
    byte[] signature = Files.readAllBytes(Path.of(signatureFile));
    publicSignature.initVerify(generator.getPublicKey());
    publicSignature.update(messege.getBytes());

    //byte[] signatureBytes = Base64.getDecoder().decode(signature);

    return publicSignature.verify(signature);
  }
  
  
  public static void main(String[] args) throws NoSuchAlgorithmException, 
      InvalidKeyException, SignatureException, IOException {
    RSASignature signature = new RSASignature();
    signature.signToFile("text.txt");

    System.out.println(signature.verify("text.txt", "rsaSignature"));
  }
}
