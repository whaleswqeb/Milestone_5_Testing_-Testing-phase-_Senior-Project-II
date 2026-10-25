package com.samp.dto.response;

import com.samp.enums.Role;

public class AuthResponse {

    private String token;
    private String tokenType = "Bearer";
    private Long userId;
    private String email;
    private String fullName;
    private Role role;

    public AuthResponse() {}

    public AuthResponse(String token, String tokenType, Long userId, String email, String fullName, Role role) {
        this.token = token;
        this.tokenType = tokenType != null ? tokenType : "Bearer";
        this.userId = userId;
        this.email = email;
        this.fullName = fullName;
        this.role = role;
    }

    public static AuthResponseBuilder builder() {
        return new AuthResponseBuilder();
    }

    public static class AuthResponseBuilder {
        private String token;
        private String tokenType = "Bearer";
        private Long userId;
        private String email;
        private String fullName;
        private Role role;

        public AuthResponseBuilder token(String token) { this.token = token; return this; }
        public AuthResponseBuilder tokenType(String tokenType) { this.tokenType = tokenType; return this; }
        public AuthResponseBuilder userId(Long userId) { this.userId = userId; return this; }
        public AuthResponseBuilder email(String email) { this.email = email; return this; }
        public AuthResponseBuilder fullName(String fullName) { this.fullName = fullName; return this; }
        public AuthResponseBuilder role(Role role) { this.role = role; return this; }

        public AuthResponse build() {
            return new AuthResponse(token, tokenType, userId, email, fullName, role);
        }
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}