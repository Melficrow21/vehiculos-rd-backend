package com.vehiculosrd.vehiculos;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    private final VehiculoService vehiculoService;

    public VehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    // GET /api/vehiculos?marca=toyota&anio=2020&precioMin=200000&precioMax=800000&provincia=Santo+Domingo
    // Publico: no requiere token
    @GetMapping
    public List<VehiculoResponse> listar(
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String modelo,
            @RequestParam(required = false) Integer anio,
            @RequestParam(required = false) BigDecimal precioMin,
            @RequestParam(required = false) BigDecimal precioMax,
            @RequestParam(required = false) String provincia
    ) {
        return vehiculoService.listar(marca, modelo, anio, precioMin, precioMax, provincia);
    }

    // GET /api/vehiculos/mios -> las publicaciones del usuario logueado, en cualquier estado.
    // Requiere estar logueado. Va antes de /{id} para que no choque la ruta.
    @GetMapping("/mios")
    public List<VehiculoResponse> listarMios(Authentication auth) {
        return vehiculoService.listarPorUsuario(auth.getName());
    }

    // GET /api/vehiculos/{id} -> detalle. Publico.
    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(vehiculoService.obtener(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    // POST /api/vehiculos -> crear publicacion. Requiere estar logueado.
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody VehiculoRequest request, Authentication auth) {
        VehiculoResponse creado = vehiculoService.crear(request, auth.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // PUT /api/vehiculos/{id} -> editar. Solo el dueño de la publicacion.
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable UUID id, @Valid @RequestBody VehiculoRequest request, Authentication auth) {
        try {
            return ResponseEntity.ok(vehiculoService.editar(id, request, auth.getName()));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    // DELETE /api/vehiculos/{id} -> eliminar. Solo el dueño de la publicacion.
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable UUID id, Authentication auth) {
        try {
            vehiculoService.eliminar(id, auth.getName());
            return ResponseEntity.noContent().build();
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }
}
