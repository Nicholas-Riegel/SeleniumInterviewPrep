package com.sel_int_prep;

public class StringMethods {
    public static void main(String[] args) {
        String raw = "   Selenium WebDriver   ";
        String trimmed = raw.trim();
        System.out.println("[" + trimmed + "]");                  // [Selenium WebDriver]
        System.out.println(trimmed.length());                     // 18
        System.out.println(trimmed.charAt(0));                    // S
        System.out.println(trimmed.substring(0, 8));              // Selenium
        System.out.println(trimmed.substring(9));                 // WebDriver
        System.out.println(trimmed.indexOf("Driver"));            // 12
        System.out.println(trimmed.indexOf("xyz"));               // -1
        System.out.println(trimmed.contains("Web"));              // true
        System.out.println(trimmed.startsWith("Sel"));            // true
        System.out.println(trimmed.replace("e", "3"));            // S3l3nium W3bDriv3r
        System.out.println("selenium".equalsIgnoreCase("SELENIUM")); // true
        System.out.println("   ".isBlank());                      // true
        System.out.println("   ".isEmpty());                      // false

        String csv = "login,cart,checkout";
        String[] steps = csv.split(",");
        System.out.println(steps.length);                         // 3
        System.out.println(steps[1]);                             // cart
        System.out.println(String.join(" | ", steps));            // login | cart | checkout

        StringBuilder sb = new StringBuilder();
        for (String step : steps) {
            sb.append(step.toUpperCase()).append(";");
        }
        sb.setLength(sb.length() - 1);                            // drop the last ";"
        System.out.println(sb);                                   // LOGIN;CART;CHECKOUT
    }
}