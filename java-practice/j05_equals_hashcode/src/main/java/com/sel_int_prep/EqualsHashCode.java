package com.sel_int_prep;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class EqualsHashCode {
    public static void main(String[] args) {

        // Without equals/hashCode: two objects with the same data are different
        Set<PlainUser> plainSet = new HashSet<>();
        plainSet.add(new PlainUser("standard_user"));
        plainSet.add(new PlainUser("standard_user"));
        System.out.println(plainSet.size());   // 2

        // With equals/hashCode: same data counts as the same element
        Set<User> users = new HashSet<>();
        users.add(new User("standard_user"));
        users.add(new User("standard_user"));
        System.out.println(users.size());      // 1

        System.out.println(new User("a").equals(new User("a")));  // true
        System.out.println(new User("a") == new User("a"));       // false
    }

    static class PlainUser {
        
        private final String username;

        PlainUser(String username) {
            this.username = username;
        }
    }

    static class User {

        private final String username;

        User(String username) {
            this.username = username;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            // old way
            // if (!(o instanceof User)) return false;
            // User other = (User) o;   // explicit cast, necessary bc o was declared as type Object
            // return username.equals(other.username);

            // The case is necessary because o is declared as Object, and Object has no username field. Java checks field names against the declared type at compile time, so the instanceof check doesn't help here:

            // new way
            if ( !(o instanceof User other) ) return false;
            return username.equals(other.username);
        }

        @Override
        public int hashCode() {
            return Objects.hash(username);
        }
    }
}