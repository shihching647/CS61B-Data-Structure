package textbook.ch3_1;

import java.util.Arrays;

public class Caesar {
    public static final int ALPHA_SIZE = 26;
    public static final char[] alpha = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    protected char[] encrypt = new char[ALPHA_SIZE];
    protected char[] decrypt = new char[ALPHA_SIZE];

    public Caesar(){
        for(int i = 0; i < encrypt.length; i++){
            encrypt[i] = alpha[(i + 3) % ALPHA_SIZE];
        }
        for(int i = 0; i < decrypt.length; i++){
            decrypt[encrypt[i] - 'A'] = alpha[i];
        }

    }

    public String encrypt(String secret){
        char[] mes = secret.toCharArray();
        for(int i = 0; i < mes.length; i++){
            //只針對大寫字母轉換
            if(Character.isUpperCase(mes[i])){
                mes[i] = encrypt[mes[i] - 'A'];
            }
        }
        return new String(mes);
    }

    public String decrypt(String secret){
        char[] mes = secret.toCharArray();
        for(int i = 0; i < mes.length; i++){
            if(Character.isUpperCase(mes[i])){
                mes[i] = decrypt[mes[i] - 'A'];
            }
        }
        return new String(mes);
    }
    public static void main(String[] args) {
        Caesar cipher = new Caesar();
        System.out.println("Encryption order = " + new String(cipher.encrypt));
        System.out.println("Decryption order = " + new String(cipher.decrypt));

        String secret = "THE EAGLE IS IN PLAY ; MEET AT JOE'S.";
        secret = cipher.encrypt(secret);
        System.out.println(secret);
        secret = cipher.decrypt(secret);
        System.out.println(secret);
    }
}