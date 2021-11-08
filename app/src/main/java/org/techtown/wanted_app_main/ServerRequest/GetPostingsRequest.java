package org.techtown.wanted_app_main.ServerRequest;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class GetPostingsRequest extends StringRequest {

    static final String url = "http://13.125.214.178:8080/posting";

    public GetPostingsRequest(Response.Listener<String> listener) {
        super(Method.GET, url, listener, null);
    }
}
