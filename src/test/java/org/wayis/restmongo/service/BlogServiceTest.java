package org.wayis.restmongo.service;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.apache.openejb.jee.WebApp;
import org.apache.openejb.junit.ApplicationComposer;
import org.apache.openejb.loader.IO;
import org.apache.openejb.testing.Classes;
import org.apache.openejb.testing.EnableServices;
import org.apache.openejb.testing.Module;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wayis.restmongo.application.ApplicationConfig;
import org.wayis.restmongo.mongo.DBConnection;

import javax.ws.rs.core.Application;
import java.io.IOException;
import java.net.URL;

@EnableServices("jaxrs")
@RunWith(ApplicationComposer.class)
public class BlogServiceTest {

    private static MongodExecutable mongodExe;
    private static MongodProcess mongod;

    // TODO: Create ClassRule to replace this code
    @BeforeClass
    public static void setUp() throws IOException {
        MongodStarter starter = MongodStarter.getDefaultInstance();
        mongodExe = starter.prepare(new MongodConfig(Version.Main.PRODUCTION, 12345, Network.localhostIsIPv6()));
        mongod = mongodExe.start();
    }

    // TODO: Create ClassRule to replace this code
    @AfterClass
    public static void tearDown() {
        mongod.stop();
        mongodExe.stop();
    }

    @Module
    @Classes(cdi = true, value = {BlogService.class, DBConnection.class})
    public WebApp app() {
        return new WebApp().contextRoot("test").addServlet("REST Application", Application.class.getName())
                .addInitParam("REST Application", "javax.ws.rs.Application", ApplicationConfig.class.getName());
    }

    @Test
    public void testList() throws IOException, JSONException {
        JSONArray posts = new JSONArray(IO.slurp(new URL("http://localhost:4204/test/api/blog/list")));
        // TODO: Add test on length when init Rule will be done
    }

    @Test
    public void testFindById() throws IOException, JSONException {
        final String postId = "5143ddf3bcf1bf4ab37d9c6d";
        JSONObject post = new JSONObject(IO.slurp(new URL("http://localhost:4204/test/api/blog/get/" + postId)));

        // TODO: Add tests on post attribute
    }
}
