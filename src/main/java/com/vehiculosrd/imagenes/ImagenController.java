package com.vehiculosrd.imagenes;

import com.vehiculosrd.vehiculos.Vehiculo;
import com.vehiculosrd.vehiculos.VehiculoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/vehiculos/{vehiculoId}/imagenes")
public class ImagenController {

    private final StorageService storageService;
    private final ImagenRepository imagenRepository;
    private final VehiculoRepository vehiculoRepository;

    public ImagenController(StorageService storageService, ImagenRepository imagenRepository,
                             VehiculoRepository vehiculoRepository) {
        this.storageService = storageService;
        this.imagenRepository = imagenRepository;
        this.vehiculoRepository = vehiculoRepository;
    }

    // Paso 1: el frontend pide una URL a la que subir el archivo directamente
    @PostMapping("/presigned")
    public ResponseEntity<?> solicitarUrl(@PathVariable UUID vehiculoId,
                                           @Valid @RequestBody SolicitarUrlRequest request,
                                           Authentication auth) {
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId).orElse(null);
        if (vehiculo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Vehiculo no encontrado"));
        }
        if (!vehiculo.getUsuario().getEmail().equalsIgnoreCase(auth.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "No tienes permiso sobre este vehiculo"));
        }

        UrlSubidaResponse respuesta = storageService.generarUrlSubida(request.getContentType(), request.getExtension());
        return ResponseEntity.ok(respuesta);
    }

    // Paso 2: una vez el frontend subio el archivo con exito, confirma y se guarda el registro
    @PostMapping
    public ResponseEntity<?> confirmar(@PathVariable UUID vehiculoId,
                                        @Valid @RequestBody ConfirmarImagenRequest request,
                                        Authentication auth) {
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId).orElse(null);
        if (vehiculo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Vehiculo no encontrado"));
        }
        if (!vehiculo.getUsuario().getEmail().equalsIgnoreCase(auth.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "No tienes permiso sobre este vehiculo"));
        }

        int siguienteOrden = imagenRepository.findByVehiculoIdOrderByOrdenAsc(vehiculoId).size();

        Imagen imagen = new Imagen();
        imagen.setVehiculo(vehiculo);
        imagen.setUrl(request.getUrl());
        imagen.setOrden(siguienteOrden);
        imagenRepository.save(imagen);

        return ResponseEntity.status(HttpStatus.CREATED).body(ImagenResponse.desde(imagen));
    }

    // Lista publica: cualquiera puede ver las fotos de un vehiculo
    @GetMapping
    public List<ImagenResponse> listar(@PathVariable UUID vehiculoId) {
        return imagenRepository.findByVehiculoIdOrderByOrdenAsc(vehiculoId).stream()
                .map(ImagenResponse::desde)
                .toList();
    }

    @DeleteMapping("/{imagenId}")
    public ResponseEntity<?> eliminar(@PathVariable UUID vehiculoId, @PathVariable UUID imagenId, Authentication auth) {
        Imagen imagen = imagenRepository.findById(imagenId).orElse(null);
        if (imagen == null || !imagen.getVehiculo().getId().equals(vehiculoId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Imagen no encontrada"));
        }
        if (!imagen.getVehiculo().getUsuario().getEmail().equalsIgnoreCase(auth.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "No tienes permiso sobre esta imagen"));
        }

        imagenRepository.delete(imagen);
        return ResponseEntity.noContent().build();
    }
}
