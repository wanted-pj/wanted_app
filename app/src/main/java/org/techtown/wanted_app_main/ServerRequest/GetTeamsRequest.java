package org.techtown.wanted_app_main.ServerRequest;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class GetTeamsRequest extends StringRequest {


    final static private String URL = "http://13.125.214.178:8080/team";
    public GetTeamsRequest(Response.Listener<String> listener,Long id) {
        super(Method.GET, URL+"/"+id, listener, null);
    }

}
