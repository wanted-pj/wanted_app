package org.techtown.wanted_app_main.ServerRequest;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class GetMajorRegionSchoolRequest extends StringRequest {

    public GetMajorRegionSchoolRequest(String url, Response.Listener<String> listener) {
        super(Method.GET, url, listener, null);
    }
}
