package com.example.fit5046;

public class Credential {

        private User userId;
        private String username;
        private String passwordHash;
        private String signUpDate;

        public Credential(User userId, String username, String passwordHash, String signUpDate) {
            this.userId = userId;
            this.username = username;
            this.passwordHash = passwordHash;
            this.signUpDate = signUpDate;
        }


}
