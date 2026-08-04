package com.vehiculosrd.admin;

import com.vehiculosrd.vehiculos.Vehiculo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class AdminVehiculoResponse {

    private UUID id;
    private String marca;
    private String modelo;
    private Integer anio;
    private BigDecimal precio;
    private String estado;
    private LocalDateTime creadoEn;
    private String vendedorNombre;
    private String vendedorEmail;

    public static AdminVehiculoResponse desde(Vehiculo v) {
        AdminVehiculoResponse r = new AdminVehiculoResponse();
        r.id = v.getId();
        r.marca = v.getMarca();
        r.modelo = v.getModelo();
        r.anio = v.getAnio();
        r.precio = v.getPrecio();
        r.estado = v.getEstado();
        r.creadoEn = v.getCreadoEn();
        r.vendedorNombre = v.getUsuario().getNombre();
        r.vendedorEmail = v.getUsuario().getEmail();
        return r;
    }

    public UUID getId() { return id; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public Integer getAnio() { return anio; }
    public BigDecimal getPrecio() { return precio; }
    public String getEstado() { return estado; }
    public LocalDateTime getCreadoEn() { return creadoEn; }
    public String getVendedorNombre() { return vendedorNombre; }
    public String getVendedorEmail() { return vendedorEmail; }
}
