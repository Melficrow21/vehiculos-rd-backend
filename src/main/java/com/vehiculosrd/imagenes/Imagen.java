package com.vehiculosrd.imagenes;

import com.vehiculosrd.vehiculos.Vehiculo;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "imagenes")
public class Imagen {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehiculo_id", nullable = false)
    private Vehiculo vehiculo;

    @Column(nullable = false, length = 500)
    private String url;

    // Orden de aparicion (0 = foto principal)
    @Column(nullable = false)
    private Integer orden = 0;

    public Imagen() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Vehiculo getVehiculo() { return vehiculo; }
    public void setVehiculo(Vehiculo vehiculo) { this.vehiculo = vehiculo; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }
}
