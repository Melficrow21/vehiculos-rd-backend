package com.vehiculosrd.vehiculos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class VehiculoRequest {

    @NotBlank(message = "La marca es obligatoria")
    private String marca;

    @NotBlank(message = "El modelo es obligatorio")
    private String modelo;

    @NotNull(message = "El año es obligatorio")
    private Integer anio;

    private Integer kilometraje;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a cero")
    private BigDecimal precio;

    private String provincia;
    private String descripcion;

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }

    public Integer getKilometraje() { return kilometraje; }
    public void setKilometraje(Integer kilometraje) { this.kilometraje = kilometraje; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
