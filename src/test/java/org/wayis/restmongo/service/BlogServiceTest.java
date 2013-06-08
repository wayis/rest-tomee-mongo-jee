package org.wayis.restmongo.service;

import org.apache.openejb.jee.WebApp;
import org.apache.openejb.junit.ApplicationComposer;
import org.apache.openejb.loader.IO;
import org.apache.openejb.testing.Classes;
import org.apache.openejb.testing.EnableServices;
import org.apache.openejb.testing.Module;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wayis.restmongo.application.ApplicationConfig;
import org.wayis.restmongo.mongo.DBConnection;

import javax.ws.rs.core.Application;
import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@EnableServices("jaxrs")
@RunWith(ApplicationComposer.class)
public class BlogServiceTest {

    @Module
    @Classes(cdi = true, value = {BlogService.class, DBConnection.class})
    public WebApp app() {
        return new WebApp().contextRoot("test").addServlet("REST Application", Application.class.getName())
                .addInitParam("REST Application", "javax.ws.rs.Application", ApplicationConfig.class.getName());
    }

    @Test
    public void testList() throws IOException, JSONException {
        JSONArray posts = new JSONArray(IO.slurp(new URL("http://localhost:4204/test/api/blog/list")));
        assertEquals(4, posts.length());
    }

    public void testFindById() throws IOException, JSONException {
        final String postId = "XXXXXX";
        JSONObject post = new JSONObject(IO.slurp(new URL("http://localhost:4204/test/api/blog/get/" + postId)));

        assertNotNull(post);
        // TODO: Add tests on post attribute
    }
}
