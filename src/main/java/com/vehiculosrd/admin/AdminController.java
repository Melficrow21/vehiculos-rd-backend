package com.vehiculosrd.admin;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

// Todas las rutas aqui ya estan protegidas en SecurityConfig: solo usuarios con rol ADMIN entran.
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // GET /api/admin/vehiculos -> todas las publicaciones, para moderar
    @GetMapping("/vehiculos")
    public List<AdminVehiculoResponse> listarVehiculos() {
        return adminService.listarVehiculos();
    }

    // PATCH /api/admin/vehiculos/{id}/estado -> aprobar, rechazar, pausar
    @PatchMapping("/vehiculos/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable UUID id, @Valid @RequestBody CambiarEstadoRequest request) {
        try {
            return ResponseEntity.ok(adminService.cambiarEstado(id, request.getEstado()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // DELETE /api/admin/vehiculos/{id} -> eliminar publicacion (ej: contenido inapropiado)
    @DeleteMapping("/vehiculos/{id}")
    public ResponseEntity<?> eliminarVehiculo(@PathVariable UUID id) {
        try {
            adminService.eliminarVehiculo(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    // GET /api/admin/usuarios -> lista de usuarios registrados
    @GetMapping("/usuarios")
    public List<AdminUsuarioResponse> listarUsuarios() {
        return adminService.listarUsuarios();
    }
}
