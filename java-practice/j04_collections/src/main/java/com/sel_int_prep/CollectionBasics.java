package com.sel_int_prep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollectionBasics {
    public static void main(String[] args) {
        // List: ordered, allows duplicates, access by index
        List<String> steps = new ArrayList<>();
        steps.add("login");
        steps.add("cart");
        steps.add("login");
        System.out.println(steps);          // [login, cart, login]
        System.out.println(steps.size());   // 3
        System.out.println(steps.get(1));   // cart
        steps.remove("login");              // removes the first match only
        System.out.println(steps);          // [cart, login]
        // List only has two versions:

        // remove(int index) removes by position.
        // remove(Object o) removes the first matching value.
        // There's a trap with List<Integer>: list.remove(2) removes the element at index 2, not the value 2. To remove the value, write list.remove(Integer.valueOf(2)).

        // Set: no duplicates, no index
        Set<String> browsers = new HashSet<>();
        browsers.add("chrome");
        browsers.add("firefox");
        boolean added = browsers.add("chrome"); // false, already present
        System.out.println(browsers.size());    // 2
        System.out.println(added);              // false
        System.out.println(browsers.contains("firefox")); // true

        // Map: key -> value, keys are unique
        // LinkedHashMap keeps insertion order when you loop over it. A plain HashMap doesn't guarantee any order.
        Map<String, String> credentials = new LinkedHashMap<>();
        credentials.put("standard_user", "secret_sauce");
        credentials.put("locked_out_user", "secret_sauce");
        credentials.put("standard_user", "new_password"); // replaces the old value
        System.out.println(credentials.get("standard_user"));   // new_password
        System.out.println(credentials.get("nobody"));          // null
        System.out.println(credentials.getOrDefault("nobody", "none")); // none
        System.out.println(credentials.containsKey("locked_out_user")); // true
        System.out.println(credentials.size());                 // 2
        
        // Map<String, String> credentials = new HashMap<>();
        // credentials.put("standard_user", "secret_sauce");
        // credentials.put("locked_out_user", "secret_sauce");

        // credentials.remove("locked_out_user");      // removes the key and its value
        // credentials.remove("nobody");               // no error; returns null

        // credentials.remove("standard_user", "wrong"); // removes only if the value matches; returns false here
        // credentials.entrySet().removeIf(entry -> entry.getValue().equals("secret_sauce"));
        
        for (Map.Entry<String, String> entry : credentials.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}