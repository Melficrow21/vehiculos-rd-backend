package com.vehiculosrd.vehiculos;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    // GET /api/vehiculos -> lista y filtra publicaciones
    @GetMapping
    public String listar() {
        return "Endpoint de vehiculos funcionando";
    }

    // TODO: POST /api/vehiculos          -> crear publicacion (requiere auth)
    // TODO: GET  /api/vehiculos/{id}     -> detalle
    // TODO: PUT  /api/vehiculos/{id}     -> editar (requiere auth)
    // TODO: DELETE /api/vehiculos/{id}   -> eliminar (requiere auth)
}
