package com.vehiculosrd.vehiculos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class VehiculoResponse {

    private UUID id;
    private String marca;
    private String modelo;
    private Integer anio;
    private Integer kilometraje;
    private BigDecimal precio;
    private String provincia;
    private String descripcion;
    private String estado;
    private LocalDateTime creadoEn;

    // Datos basicos del vendedor, para mostrar en la tarjeta de la publicacion
    private UUID vendedorId;
    private String vendedorNombre;
    private String vendedorRol;
    private String vendedorTelefono;

    public static VehiculoResponse desde(Vehiculo v) {
        VehiculoResponse r = new VehiculoResponse();
        r.id = v.getId();
        r.marca = v.getMarca();
        r.modelo = v.getModelo();
        r.anio = v.getAnio();
        r.kilometraje = v.getKilometraje();
        r.precio = v.getPrecio();
        r.provincia = v.getProvincia();
        r.descripcion = v.getDescripcion();
        r.estado = v.getEstado();
        r.creadoEn = v.getCreadoEn();
        r.vendedorId = v.getUsuario().getId();
        r.vendedorNombre = v.getUsuario().getNombre();
        r.vendedorRol = v.getUsuario().getRol();
        r.vendedorTelefono = v.getUsuario().getTelefono();
        return r;
    }

    // --- Getters (sin setters, es un DTO de solo lectura hacia el frontend) ---

    public UUID getId() { return id; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public Integer getAnio() { return anio; }
    public Integer getKilometraje() { return kilometraje; }
    public BigDecimal getPrecio() { return precio; }
    public String getProvincia() { return provincia; }
    public String getDescripcion() { return descripcion; }
    public String getEstado() { return estado; }
    public LocalDateTime getCreadoEn() { return creadoEn; }
    public UUID getVendedorId() { return vendedorId; }
    public String getVendedorNombre() { return vendedorNombre; }
    public String getVendedorRol() { return vendedorRol; }
    public String getVendedorTelefono() { return vendedorTelefono; }
}
