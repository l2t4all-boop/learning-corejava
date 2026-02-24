package com.careerit.cj.abs;

public class EmailSendService {


    public static void sendEmail(String accountId, String toEmail, String fromEmail, String header, String body){
            if(isAccountExists(accountId)){
                String emailProvider = getEmailProvider(accountId);
                if(emailProvider != null && !emailProvider.isEmpty()){
                    sendEmail(toEmail,fromEmail,header,body);
                }
            }
    }

    private static void sendEmail(String toEmail, String fromEmail, String header, String body) {
        //TODO
    }

    private static boolean isAccountExists(String accountId){
        return true;
    }
    private static  String getEmailProvider(String accountId){
        return "";
    }


}
