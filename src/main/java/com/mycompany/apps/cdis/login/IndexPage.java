/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.apps.cdis.login;

import java.io.IOException;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * ログインページの ManagedBean クラス
 *
 * @author stnetadmin
 */
@Named(value = "indexPage")
@RequestScoped
public class IndexPage {

    private String username;
    private String password;
    private String password2;
    private String email;
    private boolean rememberMe;

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    /**
     * Creates a new instance of IndexPage
     */
    public IndexPage() {
    }

    /**
     * 既にログイン済みだった場合、ログイン後のページ(home.xhtml)へリダイレクトし login.xhtml を非表示にします。
     */
    public void onPageLoad() throws ServletException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            try {
                StringBuilder redirectURL = new StringBuilder(request.getContextPath());
                redirectURL.append("/faces/view/login/home.xhtml");
                FacesContext.getCurrentInstance().getExternalContext().redirect(redirectURL.toString());
            } catch (IOException ex) {
                request.logout();
            }
        }
    }

    /**
     * ログインボタンが押下された際の処理
     *
     * @return ホームページへリダイレクト
     */
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

//        String name = getUsername();
//        String pass = getPassword();
//        Logger.getLogger(IndexPage.class.getName()).log(Level.INFO, name + ":" + pass);
        try {
            request.login(getUsername(), getPassword());
            return "home.xhtml?faces-redirect=true";
        } catch (ServletException ex) {
            context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "ログインに失敗しました。", "ユーザ名、パスワードを正しく入力してください。"));
            Logger.getLogger(IndexPage.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    /**
     * メール送信ボタンが押下された際の処理
     *
     * @return ログインページへリダイレクト
     */
    public String sendMe() {
        String email = getEmail();
        return "index.xhtml&faces-redirect=true";
    }

    /**
     * ログアウトボタンが押下された際の処理
     *
     * @return セッション破棄とともにログインページにリダイレクト
     */
    public String logout() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.invalidateSession();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        try {
            request.logout();
        } catch (ServletException ex) {
            Logger.getLogger(IndexPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "index.xhtml?faces-redirect=true";
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
