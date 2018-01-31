package com.example.lq.myapplication.encrypt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lq.myapplication.R;

import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;

public class EncryptActivity extends AppCompatActivity {
    public static final String public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCmvmPKHfC3d8WlzfH8jG2SKpPL\n" +
            "G7cIasRwo6M+ecmbQxUfOXxIDN1uH6HNMnNsh08HGHTjzup6A+mcSkHHJpph6/PQ\n" +
            "cxkPb1NkZaFuvtE0bxm0dIO3MwLGbYH/W30B9yCxqhvsaWcHunH+WSUp/sbT2pCD\n" +
            "OP51YPsV+bkkLzJrUQIDAQAB";
    public static final String private_key = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKa+Y8od8Ld3xaXN\n" +
            "8fyMbZIqk8sbtwhqxHCjoz55yZtDFR85fEgM3W4foc0yc2yHTwcYdOPO6noD6ZxK\n" +
            "QccmmmHr89BzGQ9vU2RloW6+0TRvGbR0g7czAsZtgf9bfQH3ILGqG+xpZwe6cf5Z\n" +
            "JSn+xtPakIM4/nVg+xX5uSQvMmtRAgMBAAECgYAKyvQGlx2HopcGfmgVYrmM5nie\n" +
            "gvK79r00VUMxvgxQomzxknT67OpAQIfmkkdqVO/Gk8ytGi2PLEWouh272EHRzfpN\n" +
            "1vdKRpWN/F0zUSgeMfx0dIcFjm4oAEppEeS81/f9vSuesn+M4h45uSzTZ4BSuEGx\n" +
            "oEpuJmwmcVsb2EWSgQJBANuJFEnN8WCPStcuVlgH6mDAqZ7j2pZrTHfdEpAViFqR\n" +
            "bMDQWiyKVuqaX48xZEcPS9rioV9J68NVwP+4lHgujvkCQQDCcIodo2SsaFXKH5d5\n" +
            "h06D/SooF7WozK2XWrwDqTX6aYIn2vfbr3dhN1fa0Rjb0m76tKX8OKzO31XZ7HP9\n" +
            "5l0ZAkBJ/YFg/pADw0euDmf7c2+C/GwuqQza9X7oRxIijZ9PJwRIV/CMYnO4zh62\n" +
            "3Ca37GfcddV9kCRtyVEq3sLrXoXBAkBLXsxrCLcxjZATtYNF/IkW6L+6otYsBIsV\n" +
            "rHpNAP6HfvaQ9HQ3mCCycn1/uKXmMAAM9pGQ33c9CZ2ByAJ+XRmhAkEAuBqcGFsY\n" +
            "ICI31hR6tg8ZJnxQi7EZxIGpvtgyKIHITef5L8Y4n5icHqp0R3pItKhkxoGA++20\n" +
            "VCe/6WSZoXtCWQ==";
    private EditText srcData;
    private Button encrypt;
    private EditText encryptData;
    private Button decrypt;
    private TextView decryptData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt);
        srcData = findViewById(R.id.src_data);
        encrypt = findViewById(R.id.encrypt);
        encryptData = findViewById(R.id.encrypt_data);
        decrypt = findViewById(R.id.decrypt);
        decryptData = findViewById(R.id.decrypt_data);
        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                encrypt();
            }
        });

        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrypt();
            }
        });
    }

    private void decrypt() {
        byte[] privateKey = Base64.decode(private_key, Base64.DEFAULT);
        String s = encryptData.getText().toString();
        try {
            byte[] bytes1 = decryptByPrivateKey(Base64.decode(s,Base64.DEFAULT), privateKey);
            decryptData.setText(new String(bytes1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void encrypt() {
        byte[] publicKey = Base64.decode(public_key, Base64.DEFAULT);
        try {
            byte[] bytes = encryptByPublicKey(srcData.getText().toString().getBytes(), publicKey);
            String encode = Base64.encodeToString(bytes, Base64.DEFAULT);
            encryptData.setText(encode);
        } catch (Exception e) {
            e.printStackTrace();
            encryptData.setText("加密出错");
        }
    }

    /**
     * 使用公钥加密
     */
    public static byte[] encryptByPublicKey(byte[] data, byte[] publicKey) throws Exception {
        // 得到公钥对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        // 加密数据
        Cipher cp = Cipher.getInstance("RSA/None/PKCS1Padding");
        cp.init(Cipher.ENCRYPT_MODE, pubKey);
        return cp.doFinal(data);
    }

    /**
     * 使用私钥解密
     */
    public static byte[] decryptByPrivateKey(byte[] encrypted, byte[] privateKey) throws Exception {
        // 得到私钥对象
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey keyPrivate = kf.generatePrivate(keySpec);
        // 解密数据
        Cipher cp = Cipher.getInstance("RSA/None/PKCS1Padding");
        cp.init(Cipher.DECRYPT_MODE, keyPrivate);
        byte[] arr = cp.doFinal(encrypted);
        return arr;
    }
}
