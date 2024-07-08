import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;

public class RSAEncryption {
    private final BigInteger n;
    private final BigInteger e;
    private final BigInteger d;

    public RSAEncryption(int bitLength) {
        SecureRandom random = new SecureRandom();
        BigInteger p = BigInteger.probablePrime(bitLength / 2, random);
        BigInteger q = BigInteger.probablePrime(bitLength / 2, random);

        n = p.multiply(q);
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        e = BigInteger.valueOf(65537); // Common public exponent
        d = e.modInverse(phi);
    }

    public BigInteger encrypt(BigInteger message) {
        return message.modPow(e, n);
    }

    public BigInteger decrypt(BigInteger encryptedMessage) {
        return encryptedMessage.modPow(d, n);
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter the key length (in bits): ");
            int keyLength = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            // RSA key generation
            System.out.println("RSA Key Generation:");
            RSAEncryption rsa = new RSAEncryption(keyLength);

            // User input for the message
            System.out.print("Enter a message to encrypt: ");
            String message = scanner.nextLine();

            // Encryption
            BigInteger encryptedMessage = rsa.encrypt(new BigInteger(message.getBytes()));
            System.out.println("Encrypted message: " + encryptedMessage);

            System.out.print("Do you want to decrypt the message? (y/n): ");
            char decryptChoice = scanner.nextLine().charAt(0);

            if (decryptChoice == 'y' || decryptChoice == 'Y') {
                // Decryption
                BigInteger decryptedMessage = rsa.decrypt(encryptedMessage);
                System.out.println("Decrypted message: " + new String(decryptedMessage.toByteArray()));
            } else {
                System.out.println("Goodbye!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
