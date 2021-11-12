package org.techtown.wanted_app_main.ServerRequest;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class PutJoinTeamRequest extends StringRequest {

    public PutJoinTeamRequest(Long connectId, Response.Listener<String> listener) {
        super(Method.PUT, "http://13.125.214.178:8080/team/join/" + connectId, listener, null);
    }
}
