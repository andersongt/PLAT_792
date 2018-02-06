package com.fox.rampup.plat792.unit.infra.anti;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.fox.rampup.plat792.dom.ent.ContentChannels;
import com.fox.rampup.plat792.infra.anti.CMSAnticorruption;
import com.fox.rampup.plat792.infra.dep.ProxyCMSModule;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

/**
 * @author andersonv
 *
 */

@RunWith(VertxUnitRunner.class)
public class CMSAnticorruptionTest
{

  private static final String CONFIG_FILE = "/default-config.json";
  private static final String ENDPOINT_CONFIG = "endpoint";
  private static final String FIRST_PARAM_KEY = "country";
  private static final String PROXYCMS_CONFIG = "proxyCmsChannelsVerticleConfig";
  private static final String RESTCMS_CONFIG = "restCMSChannelsVerticleConfig";
  private static final String VALID_GETCHANNELS_RESPONSE_OK =
      "/mocks/Getchannels_response_ok.json";
  @Inject
  private CMSAnticorruption cmsAnticorruption;
  private JsonObject config = null;
  private JsonObject configProxyCms;
  private Vertx vertx;

  /**
   * 
   */
  @Rule
  public WireMockRule wireMockRule =
      new WireMockRule(wireMockConfig().dynamicPort(), false);

  /**
   * Load the config file from test classpath
   * 
   * @return
   */
  private JsonObject loadJsonResource(String path)
  {
    try
    {
      InputStream in = this.getClass().getResourceAsStream(path);
      String content = IOUtils.toString(in, Charset.forName("UTF-8"));
      return new JsonObject(content);
    } catch (Exception ex)
    {
      return new JsonObject();
    }
  }

  @Before
  public void setUp() throws Exception
  {
    int httpPort = wireMockRule.port();
    config = loadJsonResource(CONFIG_FILE);
    configProxyCms = config.getJsonObject(RESTCMS_CONFIG).getJsonObject(PROXYCMS_CONFIG);
    config.getJsonObject(RESTCMS_CONFIG).getJsonObject(PROXYCMS_CONFIG)
        .getJsonObject(ENDPOINT_CONFIG).put("port", httpPort).put("ssl", false)
        .put("host", "localhost");
    config.getJsonObject(RESTCMS_CONFIG).getJsonObject("cmsAnticorruptionConfig")
        .getJsonObject("channelQuery").put("host", "localhost");
    VertxOptions vertxOptions =
        new VertxOptions().setBlockedThreadCheckInterval(1000 * 60 * 60);
    vertx = Vertx.vertx(vertxOptions);
    Guice.createInjector(new ProxyCMSModule(vertx, config)).injectMembers(this);
  }

  /**
   * @param context
   */
  @Test
  public void shouldReturnChannels_validParams(TestContext context)
  {
    Async async = context.async();
    Map<String, String> params = new HashMap<>();
    params.put(FIRST_PARAM_KEY, "AR");
    try
    {
      stubFor(post(urlMatching("/omnix_es/contentObjects/_search")).willReturn(aResponse()
          .withStatus(200).withHeader("Content-Type", "application/json")
          .withBody(loadJsonResource(VALID_GETCHANNELS_RESPONSE_OK).encodePrettily())));


    } catch (Exception e)
    {
      fail("The test failed " + e.getMessage());

    }


    this.cmsAnticorruption.getContentChannels(params, resultChannels -> {
      if (resultChannels.succeeded())
      {
        ContentChannels contentChannels = resultChannels.result();
        assertNotNull(contentChannels);
        async.complete();

      } else
      {
        context.fail(resultChannels.cause());
        async.isFailed();
      }
    });

  }

  @After
  public void tearDown(TestContext context)
  {
    vertx.close(context.asyncAssertSuccess());
  }


}
