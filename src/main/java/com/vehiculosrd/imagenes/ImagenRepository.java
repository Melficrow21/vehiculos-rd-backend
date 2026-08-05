package com.vehiculosrd.imagenes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ImagenRepository extends JpaRepository<Imagen, UUID> {
    List<Imagen> findByVehiculoIdOrderByOrdenAsc(UUID vehiculoId);
}
