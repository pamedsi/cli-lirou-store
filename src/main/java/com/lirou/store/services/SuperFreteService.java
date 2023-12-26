package com.lirou.store.services;

import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SuperFreteService {
    public SuperFreteService() throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"from\":{\"postal_code\":\"01153000\"},\"to\":{\"postal_code\":\"20020050\"},\"services\":\"1,2,17\",\"options\":{\"own_hand\":false,\"receipt\":false,\"insurance_value\":0,\"use_insurance_value\":false},\"package\":{\"height\":2,\"width\":11,\"length\":16,\"weight\":0.3}}");
        Request request = new Request.Builder()
                .url("https://sandbox.superfrete.com/api/v0/calculator")
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("User-Agent", "Lirou Store API 1.0 (email para contato técnico)")
                .addHeader("content-type", "application/json")
                .build();

        Response response = client.newCall(request).execute();
    }
}
