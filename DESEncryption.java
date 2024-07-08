import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Scanner;

public class DESEncryption {

    private SecretKey key;
    private Cipher encryptionCipher;
    private Cipher decryptionCipher;

    public void init(String keyString) throws Exception {
        byte[] keyBytes = keyString.getBytes();
        key = new SecretKeySpec(keyBytes, "DES");

        encryptionCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key);

        decryptionCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        decryptionCipher.init(Cipher.DECRYPT_MODE, key);
    }

    public String encrypt(String message) throws Exception {
        byte[] messageInBytes = message.getBytes();
        byte[] encryptedBytes = encryptionCipher.doFinal(messageInBytes);
        return encode(encryptedBytes);
    }

    public String decrypt(String encryptedMessage) throws Exception {
        byte[] messageInBytes = decode(encryptedMessage);
        byte[] decryptedBytes = decryptionCipher.doFinal(messageInBytes);
        return new String(decryptedBytes);
    }

    private String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    private byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }

    public static void main(String[] args) {
        try {
            DESEncryption des = new DESEncryption();

            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter the DES key (8 characters): ");
            String keyString = scanner.nextLine();

            // Ensure the key is 8 characters long
            if (keyString.length() != 8) {
                System.out.println("Invalid key length. Please enter a key with exactly 8 characters.");
                return;
            }

            des.init(keyString);

            // User input for encryption
            System.out.print("Enter the text to encrypt: ");
            String originalText = scanner.nextLine();
            String encryptedMessage = des.encrypt(originalText);
            System.out.println("Encrypted Message: " + encryptedMessage);

            // User input for decryption
            System.out.print("Do you want to decrypt the message? (y/n): ");
            char choice = scanner.nextLine().charAt(0);

            if (choice == 'y' || choice == 'Y') {
                String decryptedMessage = des.decrypt(encryptedMessage);
                System.out.println("Decrypted Message: " + decryptedMessage);
            } else {
                System.out.println("Goodbye!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
