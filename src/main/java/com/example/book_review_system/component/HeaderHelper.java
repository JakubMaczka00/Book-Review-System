package com.example.book_review_system.component;
import org.springframework.http.HttpHeaders;
public class HeaderHelper {

    private final static String ADDITIONAL_MESSAGE = "messageSuccess";

    public static HttpHeaders getHeadersMessage(String message){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(ADDITIONAL_MESSAGE, message);
        return responseHeaders;
    }
}
