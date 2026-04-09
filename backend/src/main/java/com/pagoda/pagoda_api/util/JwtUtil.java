package com.pagoda.pagoda_api.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class JwtUtil {
    private static final String SECRET = "pagoda-rest-api-secret-key-2026-super-secure";
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000; // 24 horas

    public static String generateToken(Integer adminId, String usuario) {
        long expirationTime = System.currentTimeMillis() + EXPIRATION_TIME;
        String payload = adminId + ":" + usuario + ":" + expirationTime;
        String token = Base64.getEncoder().encodeToString(payload.getBytes(StandardCharsets.UTF_8));
        String signature = Base64.getEncoder().encodeToString(
            (token + SECRET).getBytes(StandardCharsets.UTF_8)
        );
        return token + "." + signature;
    }

    public static boolean validateToken(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 2) return false;

            String payload = parts[0];
            String signature = parts[1];

            String expectedSignature = Base64.getEncoder().encodeToString(
                (payload + SECRET).getBytes(StandardCharsets.UTF_8)
            );

            if (!signature.equals(expectedSignature)) return false;

            String decodedPayload = new String(Base64.getDecoder().decode(payload), StandardCharsets.UTF_8);
            String[] payloadParts = decodedPayload.split(":");
            long expirationTime = Long.parseLong(payloadParts[2]);

            return expirationTime > System.currentTimeMillis();
        } catch (Exception e) {
            return false;
        }
    }

    public static Integer getAdminId(String token) {
        try {
            String payload = token.split("\\.")[0];
            String decodedPayload = new String(Base64.getDecoder().decode(payload), StandardCharsets.UTF_8);
            return Integer.parseInt(decodedPayload.split(":")[0]);
        } catch (Exception e) {
            return null;
        }
    }
}
