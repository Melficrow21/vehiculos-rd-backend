package com.vehiculosrd.admin;

import com.vehiculosrd.usuarios.Usuario;

import java.time.LocalDateTime;
import java.util.UUID;

public class AdminUsuarioResponse {

    private UUID id;
    private String nombre;
    private String email;
    private String telefono;
    private String rol;
    private LocalDateTime creadoEn;

    public static AdminUsuarioResponse desde(Usuario u) {
        AdminUsuarioResponse r = new AdminUsuarioResponse();
        r.id = u.getId();
        r.nombre = u.getNombre();
        r.email = u.getEmail();
        r.telefono = u.getTelefono();
        r.rol = u.getRol();
        r.creadoEn = u.getCreadoEn();
        return r;
    }

    public UUID getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }
    public String getRol() { return rol; }
    public LocalDateTime getCreadoEn() { return creadoEn; }
}
