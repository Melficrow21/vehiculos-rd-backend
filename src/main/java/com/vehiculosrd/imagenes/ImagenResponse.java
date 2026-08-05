package com.vehiculosrd.imagenes;

import java.util.UUID;

public class ImagenResponse {

    private UUID id;
    private String url;
    private Integer orden;

    public static ImagenResponse desde(Imagen img) {
        ImagenResponse r = new ImagenResponse();
        r.id = img.getId();
        r.url = img.getUrl();
        r.orden = img.getOrden();
        return r;
    }

    public UUID getId() { return id; }
    public String getUrl() { return url; }
    public Integer getOrden() { return orden; }
}
