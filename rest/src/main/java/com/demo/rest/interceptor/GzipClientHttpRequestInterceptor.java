package com.demo.rest.interceptor;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class GzipClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        request.getHeaders().add("Content-Encoding", "gzip");
        byte[] gzipped = getGzip(body);
        return execution.execute(request, gzipped);
    }

    private byte[] getGzip(byte[] body) throws IOException {
        try(
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            GZIPOutputStream zipStream = new GZIPOutputStream(byteStream)
        ) {
            zipStream.write(body);
            byte[] compressedData = byteStream.toByteArray();
            return compressedData;
        }
    }
}
