import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.util.Base64;
import java.util.Scanner;

public class AESEncryption {
    private SecretKey key;
    private int KEY_SIZE;
    private final int T_LEN = 128;
    private Cipher encryptionCipher;

    public void init(int keySize) throws Exception {
        if (keySize != 128 && keySize != 192 && keySize != 256) {
            throw new IllegalArgumentException("Invalid key size. Supported sizes are 128, 192, and 256 bits.");
        }

        KEY_SIZE = keySize;

        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(KEY_SIZE);
        key = generator.generateKey();
    }

    public String encrypt(String message) throws Exception {
        byte[] messageInBytes = message.getBytes();
        encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = encryptionCipher.doFinal(messageInBytes);
        return encode(encryptedBytes);
    }

    public String decrypt(String encryptedMessage) throws Exception {
        byte[] messageInBytes = decode(encryptedMessage);
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(T_LEN, encryptionCipher.getIV());
        decryptionCipher.init(Cipher.DECRYPT_MODE, key, spec);
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
            AESEncryption  aes = new AESEncryption ();

            Scanner sr = new Scanner(System.in);

            System.out.print("Enter the key size (128, 192, or 256): ");
            int keySize = sr.nextInt();
            aes.init(keySize);

            sr.nextLine(); // Consume the newline character

            System.out.print("Enter the text to encrypt: ");
            String originalText = sr.nextLine();
            String encryptedMessage = aes.encrypt(originalText);
            System.out.println("Encrypted Message: " + encryptedMessage);

            System.out.print("Do you want to decrypt the message? (y/n): ");
            char choice = sr.nextLine().charAt(0);

            if (choice == 'y' || choice == 'Y') {
                String decryptedMessage = aes.decrypt(encryptedMessage);
                System.out.println("Decrypted Message: " + decryptedMessage);
            } else {
                System.out.println("Goodbye!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
