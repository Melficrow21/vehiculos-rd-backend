package com.vehiculosrd.admin;

import com.vehiculosrd.usuarios.UsuarioRepository;
import com.vehiculosrd.vehiculos.Vehiculo;
import com.vehiculosrd.vehiculos.VehiculoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class AdminService {

    private static final Set<String> ESTADOS_VALIDOS = Set.of("disponible", "pausado", "vendido", "rechazado");

    private final VehiculoRepository vehiculoRepository;
    private final UsuarioRepository usuarioRepository;

    public AdminService(VehiculoRepository vehiculoRepository, UsuarioRepository usuarioRepository) {
        this.vehiculoRepository = vehiculoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Panel de admin ve TODAS las publicaciones, sin filtrar por "disponible" como en la busqueda publica
    public List<AdminVehiculoResponse> listarVehiculos() {
        return vehiculoRepository.findAll().stream()
                .map(AdminVehiculoResponse::desde)
                .toList();
    }

    public AdminVehiculoResponse cambiarEstado(UUID id, String nuevoEstado) {
        if (!ESTADOS_VALIDOS.contains(nuevoEstado)) {
            throw new IllegalArgumentException("Estado invalido. Usa: " + ESTADOS_VALIDOS);
        }

        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehiculo no encontrado"));

        vehiculo.setEstado(nuevoEstado);
        vehiculoRepository.save(vehiculo);
        return AdminVehiculoResponse.desde(vehiculo);
    }

    public void eliminarVehiculo(UUID id) {
        if (!vehiculoRepository.existsById(id)) {
            throw new IllegalArgumentException("Vehiculo no encontrado");
        }
        vehiculoRepository.deleteById(id);
    }

    public List<AdminUsuarioResponse> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(AdminUsuarioResponse::desde)
                .toList();
    }
}
