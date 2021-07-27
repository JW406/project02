package org.Foo.Bar.UtilityServices;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class HTTPUtils {
  public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
  public static final MediaType FORM_ENCODED = MediaType.get("application/x-www-form-urlencoded; charset=utf-8");

  OkHttpClient client = new OkHttpClient();

  public String post(String url, String json, MediaType mediaType) throws IOException {
    RequestBody body = RequestBody.create(json, mediaType);
    Headers headers = Headers.of(new HashMap<String, String>(){{
      put("Accept", "application/json");
    }});
    Request request = new Request.Builder().url(url).post(body).headers(headers).build();
    try (Response response = client.newCall(request).execute()) {
      return response.body().string();
    }
  }

  public String encodeQueryString(Map<String, String> in) {
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<String, String> e : in.entrySet()) {
      sb.append("&" + e.getKey() + "=" + e.getValue());
    }
    return sb.substring(1);
  }

  public String normalizeRemoteHost(String scheme, String remoteHost, int serverPort) {
    return scheme + "://" + (remoteHost.equals("127.0.0.1") ? "localhost" : remoteHost) + ":" + serverPort;
  }
}
