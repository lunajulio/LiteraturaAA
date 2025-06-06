//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.alura.LiteraturA.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.springframework.stereotype.Service;


@Service
public class ConsumoAPI {
    public ConsumoAPI() {
    }

    public String obtenerDatos(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = null;

        try {
            response = client.send(request, BodyHandlers.ofString());
        } catch (IOException var6) {
            IOException e = var6;
            throw new RuntimeException(e);
        } catch (InterruptedException var7) {
            InterruptedException e = var7;
            throw new RuntimeException(e);
        }

        String json = (String)response.body();
        return json;
    }
}
