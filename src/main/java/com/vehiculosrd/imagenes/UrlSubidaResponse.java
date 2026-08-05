package com.vehiculosrd.imagenes;

public class UrlSubidaResponse {

    private final String uploadUrl;
    private final String publicUrl;

    public UrlSubidaResponse(String uploadUrl, String publicUrl) {
        this.uploadUrl = uploadUrl;
        this.publicUrl = publicUrl;
    }

    public String getUploadUrl() { return uploadUrl; }
    public String getPublicUrl() { return publicUrl; }
}
