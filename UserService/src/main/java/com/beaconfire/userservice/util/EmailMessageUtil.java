package com.beaconfire.userservice.util;

public class EmailMessageUtil {
    public static final String SUBJECT = "Secure two-step verification notification";
    public static final String DOMAIN = "localhost:5173";

    public static final String PROTOCOL = "http";

    public static String generateBody(String firstname, String tokenizedUrl) {
        return "Hello " + firstname + ",<br><br>" +
                "Congratulations! You have successfully registered an account at RedditHub. Welcome! " +
                "To unlock and explore more features, please click <a href='" + tokenizedUrl + "'>here</a> to verify your email account within 3 hours. <br><br>" +
                "Have fun!<br><br>" +
                "Warm regards,<br>" +
                "RedditHub Team";
    }
}
