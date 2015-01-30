package org.tehlug.app;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/*
 * Created by behdad on 1/30/15.
 */

public interface GetMeetingsApi {
    @GET("/store/data/98387756-3559-44b9-a5ac-713e986e66e8/_query?input/webpage/url=http%3A%2F%2Ftehlug.org%2Findex.php%3Fpage%3Dsessions&_user=836ca869-89be-4ded-a1d6-de641ee1747e&_apikey={api_key}/")
    public void getMeetings(
            @Path("api_key") String api_key,
            Callback<MeetingsResponse> callback);
}
