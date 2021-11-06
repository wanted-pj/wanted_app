package org.techtown.wanted_app_main.ServerRequest;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class GetPersonalsRequest extends StringRequest {

    final static private String URL = "http://13.125.214.178:8080/personal";

    public GetPersonalsRequest(Response.Listener<String> listener) {
        super(Method.GET, URL, listener, null);
    }
}
