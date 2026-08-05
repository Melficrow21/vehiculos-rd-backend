package com.vehiculosrd.imagenes;

import jakarta.validation.constraints.NotBlank;

public class SolicitarUrlRequest {

    // ej: "image/jpeg"
    @NotBlank
    private String contentType;

    // ej: "jpg"
    @NotBlank
    private String extension;

    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }

    public String getExtension() { return extension; }
    public void setExtension(String extension) { this.extension = extension; }
}
