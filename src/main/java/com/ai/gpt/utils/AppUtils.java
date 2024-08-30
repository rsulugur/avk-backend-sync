package com.ai.gpt.utils;

import java.net.URI;
import java.net.URISyntaxException;

public class AppUtils {

    public static String JWT_TOKEN = "X-Auth-Token";

    public static Float convertPrice(String price) {
        try {
            price = price.trim();
            if (price.startsWith("$")) {
                price = price.substring(1);
            }
            return Float.parseFloat(price);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static String shortenURL(String url) {
        try {
            URI uri = new URI(url);
            URI newUri = new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), null, null);
            return newUri.toString();
        } catch (URISyntaxException ex) {
            return url;
        }
    }
}
