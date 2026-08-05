package com.vehiculosrd.vehiculos;

import com.vehiculosrd.usuarios.Usuario;
import com.vehiculosrd.usuarios.UsuarioRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.vehiculosrd.vehiculos.VehiculoSpecifications.*;

@Service
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final UsuarioRepository usuarioRepository;

    public VehiculoService(VehiculoRepository vehiculoRepository, UsuarioRepository usuarioRepository) {
        this.vehiculoRepository = vehiculoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<VehiculoResponse> listar(String marca, String modelo, Integer anio,
                                          BigDecimal precioMin, BigDecimal precioMax, String provincia) {
        Specification<Vehiculo> filtros = Specification
                .where(marcaContiene(marca))
                .and(modeloContiene(modelo))
                .and(anioIgual(anio))
                .and(precioMinimo(precioMin))
                .and(precioMaximo(precioMax))
                .and(provinciaIgual(provincia))
                // Las busquedas publicas solo muestran publicaciones disponibles
                .and(estadoIgual("disponible"));

        return vehiculoRepository.findAll(filtros).stream()
                .map(VehiculoResponse::desde)
                .toList();
    }

    public VehiculoResponse obtener(UUID id) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehiculo no encontrado"));
        return VehiculoResponse.desde(vehiculo);
    }

    // Todas las publicaciones del usuario logueado, sin filtrar por estado
    // (asi puede ver tambien las pausadas, vendidas o rechazadas)
    public List<VehiculoResponse> listarPorUsuario(String emailUsuario) {
        Specification<Vehiculo> filtro = (root, query, cb) ->
                cb.equal(root.get("usuario").get("email"), emailUsuario);

        return vehiculoRepository.findAll(filtro).stream()
                .map(VehiculoResponse::desde)
                .toList();
    }

    public VehiculoResponse crear(VehiculoRequest request, String emailUsuario) {
        Usuario usuario = obtenerUsuarioPorEmail(emailUsuario);

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setUsuario(usuario);
        aplicarDatos(vehiculo, request);

        vehiculoRepository.save(vehiculo);
        return VehiculoResponse.desde(vehiculo);
    }

    public VehiculoResponse editar(UUID id, VehiculoRequest request, String emailUsuario) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehiculo no encontrado"));

        verificarPropietario(vehiculo, emailUsuario);
        aplicarDatos(vehiculo, request);

        vehiculoRepository.save(vehiculo);
        return VehiculoResponse.desde(vehiculo);
    }

    public void eliminar(UUID id, String emailUsuario) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehiculo no encontrado"));

        verificarPropietario(vehiculo, emailUsuario);
        vehiculoRepository.delete(vehiculo);
    }

    // --- Helpers internos ---

    private void aplicarDatos(Vehiculo vehiculo, VehiculoRequest request) {
        vehiculo.setMarca(request.getMarca());
        vehiculo.setModelo(request.getModelo());
        vehiculo.setAnio(request.getAnio());
        vehiculo.setKilometraje(request.getKilometraje());
        vehiculo.setPrecio(request.getPrecio());
        vehiculo.setProvincia(request.getProvincia());
        vehiculo.setDescripcion(request.getDescripcion());
    }

    private Usuario obtenerUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }

    // Solo el dueño de la publicacion puede editarla o eliminarla
    private void verificarPropietario(Vehiculo vehiculo, String emailUsuario) {
        if (!vehiculo.getUsuario().getEmail().equalsIgnoreCase(emailUsuario)) {
            throw new SecurityException("No tienes permiso para modificar esta publicacion");
        }
    }
}
