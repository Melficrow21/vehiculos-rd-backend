package com.vehiculosrd.auth;

import java.util.UUID;

public class AuthResponse {

    private String token;
    private UUID usuarioId;
    private String nombre;
    private String rol;

    public AuthResponse(String token, UUID usuarioId, String nombre, String rol) {
        this.token = token;
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.rol = rol;
    }

    public String getToken() { return token; }
    public UUID getUsuarioId() { return usuarioId; }
    public String getNombre() { return nombre; }
    public String getRol() { return rol; }
}
