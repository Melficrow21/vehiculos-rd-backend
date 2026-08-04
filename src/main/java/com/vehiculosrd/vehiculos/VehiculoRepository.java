package com.vehiculosrd.vehiculos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface VehiculoRepository extends JpaRepository<Vehiculo, UUID>, JpaSpecificationExecutor<Vehiculo> {
}
