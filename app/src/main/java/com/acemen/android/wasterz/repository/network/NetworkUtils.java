package com.acemen.android.wasterz.repository.network;

import com.acemen.android.wasterz.repository.network.api.ApiClient;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Audrik ! on 19/04/2017.
 */

public class NetworkUtils {
    public static class URL {

        private static final String CLIENT_ID = "clientId";

        private static final String TIMESTAMP = "timestamp";

        private static final String SIGNATURE = "signature";

        public static String encode(ApiClient apiClient, String method, String route) {
            final String timestamp = String.valueOf(System.currentTimeMillis() / 1000L);

            final String url = new StringBuilder().append(apiClient.getBaseUrl()).append(route).append("?")
                    .append(CLIENT_ID).append("=").append(apiClient.getClientId()).append("&").append(TIMESTAMP)
                    .append("=").append(timestamp).toString();

            final String salt = MD5.encode(
                    new StringBuilder().append(apiClient.getClientSecret()).append("#").append(timestamp).toString());

            return new StringBuilder().append(url).append("&").append(SIGNATURE).append("=")
                    .append(Signature.encode(method, url, salt)).toString();
        }
    }

    private static class Signature {
        private static final String PUBLIC_KEY_FILE_NAME = "rsa/public.key";

        private static final String PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----"
                + "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAum4DcYFWCYFyqNzfOwr2"
                + "ERJ0P+OOjiU94btu82+0/46Mx9kBaQ45BerdHGzYaTU8ebwmOy4wjZcINANra+H1"
                + "jGVagXw/5tv3n1QIPX/PReFdHcrreZVGaHEF3opDbg92NKIAH9txp/SX1RuZWQCv"
                + "BmMAhc+mthJ6YxqOQGQTdGojCNg/REwgzHcKh6TfsA6PiSHoECkOrUDcA/ufa3Fk"
                + "qfYDhI7sOLPF1nIhNQKt7WDkpEAn4dQoFoxrHBh0OSrVJVJxD9fRpMEUUfxcA1Rl"
                + "SPeRX179187idIlPOq2UOaddBNH9tdynkgObB9fkUjKdH5ySY94NCNHgxnHzoNlq" + "OwIDAQAB"
                + "-----END PUBLIC KEY-----";

        static String encode(String method, String url, String salt) {
            byte[] signature = HmacSha1.encode(method + ":" + url, salt);

            signature = Base64.encode(signature);
            signature = RSA.encode(PUBLIC_KEY, signature);
            signature = Base64.encode(signature);

            return URI.encode(new String(signature));
        }
    }

    private static class RSA {

        private static final String ALGORITHM = "RSA/ECB/PKCS1Padding";

        static byte[] encode(String publicKey, byte[] bytes) {
            try {
                final Cipher cipher = Cipher.getInstance(ALGORITHM);

                cipher.init(Cipher.ENCRYPT_MODE, PublicKeyResource.load(sanitize(publicKey), "RSA"));

                return cipher.doFinal(bytes);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            }
            return null;
        }

        static String sanitize(String source) {
            return source.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "")
                    .replace("\n", "").replace("\r", "").replace(" ", "");
        }
    }

    static class PublicKeyResource {
        static PublicKey load(final String source, final String algorithm) {
            try {
                final KeySpec keySpec = new X509EncodedKeySpec(Base64.decode(source.getBytes()));
                final KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

                return keyFactory.generatePublic(keySpec);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private static class Base64 {
        public static String encode(String str) {
            return (str != null) ? encode(str.getBytes()).toString() : null;
//            return (str != null) ? DatatypeConverter.printBase64Binary(str.getBytes()) : null;
        }

        static byte[] encode(byte[] bytes) {
            return android.util.Base64.encodeToString(bytes, android.util.Base64.DEFAULT).getBytes();
//            return DatatypeConverter.printBase64Binary(bytes).getBytes();
        }

        public static String decode(String str) {
            return android.util.Base64.decode(str, android.util.Base64.DEFAULT).toString();
//            return new String(DatatypeConverter.parseBase64Binary(str));
        }

        static byte[] decode(byte[] bytes) {
            return android.util.Base64.decode(bytes, android.util.Base64.DEFAULT);
//            return DatatypeConverter.parseBase64Binary(new String(bytes));
        }
    }

    private static class HmacSha1 {
        private static final String ALGORITHM = "HmacSHA1";

        static byte[] encode(String str, String salt) {
            try {
                final SecretKeySpec secretKeySpec = new SecretKeySpec(salt.getBytes(), ALGORITHM);

                final Mac mac = Mac.getInstance(ALGORITHM);
                mac.init(secretKeySpec);

                return mac.doFinal(str.getBytes());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private static class MD5 {
        static String encode(String str) {
            try {
                final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                messageDigest.update(str.getBytes(), 0, str.length());

                return new BigInteger(1, messageDigest.digest()).toString(16);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static class URI {
        public static String encode(String str) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
