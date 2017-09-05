package org.springrain.sinova.util;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

public abstract class Token {
	private static final String TOKEN_LIST_NAME = "tokenList";

    public static final String TOKEN_STRING_NAME = "token";
    private static ArrayList getTokenList(HttpSession session) {
       Object obj = session.getAttribute(TOKEN_LIST_NAME);
        if (obj != null) {
          return (ArrayList) obj;
        } else {
          ArrayList tokenList = new ArrayList();
          session.setAttribute(TOKEN_LIST_NAME, tokenList);
          return tokenList;
        }
     }

     private static void saveTokenString(String tokenStr, HttpSession session) {
       ArrayList tokenList = getTokenList(session);
       tokenList.add(tokenStr);
       session.setAttribute(TOKEN_LIST_NAME, tokenList);
     }
   
     private static String generateTokenString(){
       return new Long(System.currentTimeMillis()).toString();
     }

     
     public static String getTokenString(HttpSession session) {
       String tokenStr = generateTokenString();
       saveTokenString(tokenStr, session);
       return tokenStr;
     }

     
     public static boolean isTokenStringValid(String tokenStr, HttpSession session) {
       boolean valid = false;
        if(session != null){
          ArrayList tokenList = getTokenList(session);
           if (tokenList.contains(tokenStr)) {
             valid = true;
             tokenList.remove(tokenStr);
           }
        }
       return valid;
     }
}
