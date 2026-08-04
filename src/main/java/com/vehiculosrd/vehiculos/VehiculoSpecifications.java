package com.vehiculosrd.vehiculos;

import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

// Cada método arma un filtro opcional. Se combinan con .and() en el servicio,
// y si un parametro llega nulo, ese filtro simplemente no se aplica.
public class VehiculoSpecifications {

    public static Specification<Vehiculo> marcaContiene(String marca) {
        return (root, query, cb) -> marca == null ? null :
                cb.like(cb.lower(root.get("marca")), "%" + marca.toLowerCase() + "%");
    }

    public static Specification<Vehiculo> modeloContiene(String modelo) {
        return (root, query, cb) -> modelo == null ? null :
                cb.like(cb.lower(root.get("modelo")), "%" + modelo.toLowerCase() + "%");
    }

    public static Specification<Vehiculo> anioIgual(Integer anio) {
        return (root, query, cb) -> anio == null ? null : cb.equal(root.get("anio"), anio);
    }

    public static Specification<Vehiculo> precioMinimo(BigDecimal min) {
        return (root, query, cb) -> min == null ? null : cb.greaterThanOrEqualTo(root.get("precio"), min);
    }

    public static Specification<Vehiculo> precioMaximo(BigDecimal max) {
        return (root, query, cb) -> max == null ? null : cb.lessThanOrEqualTo(root.get("precio"), max);
    }

    public static Specification<Vehiculo> provinciaIgual(String provincia) {
        return (root, query, cb) -> provincia == null ? null :
                cb.equal(cb.lower(root.get("provincia")), provincia.toLowerCase());
    }

    public static Specification<Vehiculo> estadoIgual(String estado) {
        return (root, query, cb) -> estado == null ? null : cb.equal(root.get("estado"), estado);
    }
}
