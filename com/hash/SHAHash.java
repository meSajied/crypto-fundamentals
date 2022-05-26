import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAHash {
  public String digestOfFile(String file) throws IOException, 
      NoSuchAlgorithmException {
    byte[] messageByte = Files.readAllBytes(Path.of(file));

    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    byte[] encodedhash = digest.digest(messageByte);

     return bytesToHex(encodedhash);
  }

  private String bytesToHex(byte[] hash) {
    StringBuilder hexString = new StringBuilder(2 * hash.length);
    for(int i = 0; i < hash.length; i++) {
      String hex = Integer.toHexString(0xff & hash[i]);
      if(hex.length() == 1) {
        hexString.append('0');
      }
      hexString.append(hex);
    }
    return hexString.toString();
  }

  public static void main(String[] args) throws NoSuchAlgorithmException, 
      IOException {
    SHAHash shaHash = new SHAHash();
     System.out.println(shaHash.digestOfFile("text.txt"));
  }
}
