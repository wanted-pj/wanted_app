package org.techtown.wanted_app_main.ServerRequest;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class PostMakeTeamRequest extends StringRequest {

    public PostMakeTeamRequest(Long postingId, Response.Listener<String> listener) {
        super(Method.POST, "http://13.125.214.178:8080/team/" + postingId, listener, null);
    }
}
