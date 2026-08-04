package com.vehiculosrd.admin;

import jakarta.validation.constraints.NotBlank;

public class CambiarEstadoRequest {

    // "disponible", "pausado", "vendido", "rechazado"
    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
