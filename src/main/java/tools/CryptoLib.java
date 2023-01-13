package tools;

import enums.EncryptionTypes;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.io.*;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class CryptoLib {
    final private static String encryptAlgo = "DES/ECB/PKCS5Padding";

    public static String encrypt(String fileName, Path tmpDir, String privateKey) {
        DESKeySpec dks;
        SecretKeyFactory skf;
        SecretKey desKey;
        Cipher cipher;
        try {
            dks = new DESKeySpec(privateKey.getBytes());
            skf = SecretKeyFactory.getInstance("DES");
            desKey = skf.generateSecret(dks);
            cipher = Cipher.getInstance(encryptAlgo);
            cipher.init(Cipher.ENCRYPT_MODE, desKey);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }

        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(fileName);
            fos = new FileOutputStream(ToolsLib.formPathToTmpDir(tmpDir, fileName) + "." + EncryptionTypes.axx);
            CipherInputStream cis = new CipherInputStream(fis, cipher);
            doCopy(cis, fos);
            if (fis != null) {
                fis.close();
            }
            if (fos != null) {
                fos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "." + EncryptionTypes.axx;
    }

    public static void decrypt(String fileName, String privateKey)  {
        if (!(fileName.substring(fileName.lastIndexOf(".") + 1).equals(EncryptionTypes.axx.toString()))) {
            throw new RuntimeException("Error! File not encrypted.");
        }
        DESKeySpec dks;
        SecretKeyFactory skf;
        SecretKey desKey;
        Cipher cipher;
        try {
            dks = new DESKeySpec(privateKey.getBytes());
            skf = SecretKeyFactory.getInstance("DES");
            desKey = skf.generateSecret(dks);
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, desKey);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }

        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(fileName);
            fos = new FileOutputStream(fileName.substring(0, fileName.lastIndexOf(".") + 1));
            CipherOutputStream cos = new CipherOutputStream(fos, cipher);
            doCopy(fis, cos);
            if (fis != null) {
                fis.close();
            }
            if (fos != null) {
                fos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void doCopy(InputStream is, OutputStream os) throws IOException {
        byte[] bytes = new byte[64];
        int numBytes;
        while ((numBytes = is.read(bytes)) != -1) {
            os.write(bytes, 0, numBytes);
        }
        os.flush();
        os.close();
        is.close();
    }
}
