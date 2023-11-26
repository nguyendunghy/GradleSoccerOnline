package com.example.gradlesocceronline.utils;


import com.viettel.security.PassTranformer;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author @created Jul 7, 2016
 */
@Slf4j
@Component
public class PasswordUtils {

    private static String SECURITY_KEY;

    @Value("${security.key}")
    public void setSecurityKey(String securityKey){
        SECURITY_KEY = securityKey;
    }


    /**
     * easypass
     */
    protected final static String[] easyPass = {"123", "963", "852", "741", "789", "147", "258", "369", "abc", "456"};

    /**
     * blacklist mat khau khong duoc phep
     */
    protected final static String[] blackList = {"qwerty", "password", "passw0rd", "abc123", "iloveyou", "viettel@123", "admin@123", "123qwea@", "abc123", "abc@123",
            "password@123", "qwerty@123", "vtt@2014", "123@123", "123123", "696969"};
    /*
     # Start of group
     (?=.*\d)	#   must contains one digit from 0-9
     (?=.*[a-z])	#   must contains one lowercase characters
     (?=.*[A-Z])	#   must contains one uppercase characters
     (?=.*[@#$%])#   must contains one special symbols in the list "@#$%"
     .		#   match anything with previous condition checking
     {8,20}	#   length at least 8 characters and maximum of 20
     )		# End of group
     */
    private static final String PASSWORD_PATTERN
            = "((?=.*\\d)"
            + "(?=.*[a-z])"
            + "(?=.*[A-Z])"
            + "(?=.*[@#$%~!%^&()])"
            + ".{8,20})";
    private static final Pattern PASSWORD_PATTERN_OBJ = Pattern.compile(PASSWORD_PATTERN);
    private static Matcher matcher;
    public static final String DEFAULT_PASSWORD = "123456a@";
    private static final String VALID_CHARACTER = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%~!%^&()";
    private static final String UPPER_CASE_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER_CASE_CHAR = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBER = "0123456789";
    private static final String SPECIAL_STRING = "@#$%~!%^&()";
    private static final String SALT_DEVIDER = "#";

    /**
     * Validate password with regular expression
     *
     * @param password password for validation
     * @return true valid password, false invalid password
     */
    public static boolean validateStrongPassword(final String password) {
//        matcher = pattern.matcher(password).m;
//        return matcher.matches();
        return PASSWORD_PATTERN_OBJ.matcher(password).matches() && !isInBlackList(password);
    }

    public static boolean isInBlackList(String password) {
        if (org.springframework.util.StringUtils.isEmpty(password)) {
            return true;
        }

        return Arrays.asList(blackList).contains(password);
    }

    public static String genPassAuto() {
        String password = generatePswd(8, 20);
        //return encryptPasswordBySHA256(password);
        return password;
    }

    private static String generatePswd(int minlen, int maxlen) {
        Random rand = new Random();
        int passwdlen = rand.nextInt((maxlen - minlen) + 1) + minlen;
        char[] password = new char[passwdlen];

        password[0] = UPPER_CASE_CHAR.charAt(rand.nextInt(UPPER_CASE_CHAR.length()));
        password[1] = LOWER_CASE_CHAR.charAt(rand.nextInt(LOWER_CASE_CHAR.length()));
        password[2] = NUMBER.charAt(rand.nextInt(NUMBER.length()));
        password[3] = SPECIAL_STRING.charAt(rand.nextInt(SPECIAL_STRING.length()));
        int validateCharLen = VALID_CHARACTER.length();
        for (int i = 4; i < passwdlen; i++) {
            password[i] = VALID_CHARACTER.charAt(rand.nextInt(validateCharLen));
        }
        return String.valueOf(password);
    }

    /**
     * <p>
     * Encrypt password using SHA-256 algorithm
     *
     * @param password
     * @return
     */
    private static String encryptPasswordBySHA256(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (Exception e) {
            log.error("Exception", e);
        }
        if (md == null) {
            return "";
        }
        try {
            md.update(password.getBytes("UTF-8"));
        } catch (Exception e) {
            log.error("Exception", e);
        }
        byte raw[] = md.digest();
        return Base64.encodeBase64String(raw);

    }

    /**
     * <p>
     * Append the password with a salt</p>
     *
     * @param password   The real password
     * @param saltLength The length of the salt appending with the password
     * @return
     */
    private static String encryptPassword(String password, int saltLength) {
        String salt = createSalt(saltLength);
        String input = password + salt;
        String hash = encryptPasswordBySHA256(input);
        return hash + SALT_DEVIDER + salt;
    }

    public static String encryptPassword(String password) {
        Random rand = new Random();
        int saltLength = rand.nextInt(13) + 8;// 8 <= saltLength <= 20
        return encryptPassword(password, saltLength);
    }

    /**
     * <p>
     * Check the password is true or not </p>
     *
     * @param inPassword       The pass a user enters
     * @param encrytedPassword
     * @return
     */
    public static boolean checkPassword(String inPassword, String encrytedPassword) {
        if (org.springframework.util.StringUtils.isEmpty(inPassword) || org.springframework.util.StringUtils.isEmpty((encrytedPassword))) {
            return false;
        }

        int index = encrytedPassword.lastIndexOf(SALT_DEVIDER);
        if (index == -1) {
            return false;
        }

        String password = encrytedPassword.substring(0, index);
        String salt = encrytedPassword.substring(index + SALT_DEVIDER.length(), encrytedPassword.length());
        return encryptPasswordBySHA256(inPassword + salt).equals(password);

    }

    /**
     * <p>
     * Create a String salt</p>
     *
     * @param length The Length of the salt will be created
     * @return A salt string
     */
    private static String createSalt(int length) {
        char[] salt = new char[length];
        int validateWordSize = VALID_CHARACTER.length();
        Random rand = new Random();
        do {
            for (int i = 0; i < length; i++) {
                int pos = rand.nextInt(validateWordSize);
                salt[i] = VALID_CHARACTER.charAt(pos);
            }
        } while (String.valueOf(salt).contains(SALT_DEVIDER));

        return String.valueOf(salt);
    }

    public static String encryptPassHibernate(String pass) {
        return encrypt(pass);
    }

    public static String decryptPasswordHibernate(String enscryptPass) {
        return decrypt(enscryptPass);
    }

    public static String encrypt(String data) {
        try {
            PassTranformer.setInputKey(SECURITY_KEY);
            return PassTranformer.encrypt(data);
        } catch (Exception ex) {
            log.error("Have error", ex);
            return "";
        }
    }

    public static String decrypt(String data) {
        try {
            PassTranformer.setInputKey(SECURITY_KEY);
            return PassTranformer.decrypt(data);
        } catch (Exception ex) {
            log.error("Have error", ex);
        }
        return "";
    }



    public static void main(String[] args) throws Exception {
        //System.out.println(decryptPasswordHibernate("aaf1f59c81072642303cebe350be029506757388d4d8c751ad6885ae79b72f8e"));
        System.out.println(encrypt("123123a@"));
        System.out.println(decrypt("493ac3de7bf42ba7ae6bef9c811f08e3"));
//        System.out.println(PassTranformer.decrypt("3d10b5aa3ac6e1a359b7bff88baeec23"));
//        System.out.println(PassTranformer.encrypt("123a@XYt$"));
//        System.out.println(viettelDecryptDefault(""));
    }
}

