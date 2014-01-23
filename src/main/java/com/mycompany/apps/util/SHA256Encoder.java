/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.apps.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DB に保存するパスワードを SHA-256 のメッセージ・ダイジェストとして保存します。<br>
 * プレーン・テキストとメッセージ・ダイジェストの変換ユーティリティ
 *
 * @author stnetadmin
 */
public class SHA256Encoder {

    /**
     * パスワード文字列を SHA-256 ハッシュとして保存します。
     *
     * @return SHA-256 ハッシュ文字列
     */
    public String encodePassword(String originalPassword) {
        String returnValue = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(originalPassword.getBytes());
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < digest.length; i++) {
                String tmp = Integer.toHexString(digest[i] & 0xff);
                if (tmp.length() == 1) {
                    builder.append('0').append(tmp);
                } else {
                    builder.append(tmp);
                }
            }
            returnValue = builder.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(SHA256Encoder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnValue;
    }
}
