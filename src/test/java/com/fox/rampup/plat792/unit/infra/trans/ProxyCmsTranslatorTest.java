/**
 * 
 */
package com.fox.rampup.plat792.unit.infra.trans;

import static org.junit.Assert.*;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.commons.text.StrSubstitutor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.fox.platform.lib.cfg.ConfigLibFactory;
import com.fox.rampup.plat792.dom.ent.ContentChannels;
import com.fox.rampup.plat792.infra.conf.ProxyCmsChannelsVerticleConfig;
import com.fox.rampup.plat792.infra.conf.RestCMSChannelsVerticleConfig;
import com.fox.rampup.plat792.infra.conf.impl.RestCMSChannelsVerticleConfigImpl;
import com.fox.rampup.plat792.infra.trans.impl.ProxyCmsTranslatorImpl;
import io.vertx.core.json.JsonObject;

/**
 * @author andersonv
 *
 */
@RunWith(JUnit4.class)
public class ProxyCmsTranslatorTest
{

  private static final String CONFIG_FILE = "/default-config.json";
  private static final String VALIDJSON_REQUEST =
      "/mocks/ValidJosnFromBodyStringToBodyJoson.json";
  private static final String TEST_INPUTS_FILE = "/mocks/TestJsonInputs.json";
  private ProxyCmsTranslatorImpl proxyCmsTranslatorImpl;
  private RestCMSChannelsVerticleConfigImpl restCMSChannelsVerticleConfigImpl;

  private static JsonObject TEST_JSON_INPUTS;

  @Before
  public void setUp() throws Exception
  {
    TEST_JSON_INPUTS = loadJsonResource(TEST_INPUTS_FILE);
    restCMSChannelsVerticleConfigImpl =
        (RestCMSChannelsVerticleConfigImpl) ConfigLibFactory.FACTORY.createServiceConfig(
            loadJsonResource(CONFIG_FILE), RestCMSChannelsVerticleConfigImpl.CONFIG_FIELD,
            RestCMSChannelsVerticleConfigImpl.class);

    proxyCmsTranslatorImpl = new ProxyCmsTranslatorImpl();

  }

  /**
   * With valid StringPayload
   */
  @Test
  public void shouldReturnJsonPayloadWithValidString()
  {
    String testBodyPayload =
        TEST_JSON_INPUTS.getJsonObject("TestInputs").getString("testValidStringPayload");

    Map<String, String> mockParams = new HashMap<>();
    mockParams.put("country", "GT");
    StrSubstitutor substitutor = new StrSubstitutor(mockParams);
    JsonObject correctJsonRequest =
        new JsonObject(substitutor.replace(loadJsonResource(VALIDJSON_REQUEST)));

    JsonObject testJsonToSend =
        proxyCmsTranslatorImpl.mapRequestBodyGetConentToJson(testBodyPayload, mockParams);
    assertEquals(testJsonToSend, correctJsonRequest);

  }

  /**
   * With invalid StringPayload
   */

  @Test
  public void shouldReturnJsonErrorWithInvalidString()
  {
    String testBodyPayload = TEST_JSON_INPUTS.getJsonObject("TestInputs")
        .getString("testInvalidStringPayload");

    Map<String, String> mockParams = new HashMap<>();
    mockParams.put("country", "GT");
    StrSubstitutor substitutor = new StrSubstitutor(mockParams);
    JsonObject correctJsonRequest =
        new JsonObject(substitutor.replace(loadJsonResource(VALIDJSON_REQUEST)));

    JsonObject testJsonToSend =
        proxyCmsTranslatorImpl.mapRequestBodyGetConentToJson(testBodyPayload, mockParams);
    assertNotEquals(testJsonToSend, correctJsonRequest);
  }


  /**
   * 
   */

  @Test
  public void shouldReturnContentChannels_withValidJsonResponse()
  {
    JsonObject jsonResultChannels = new JsonObject(TEST_JSON_INPUTS
        .getJsonObject("TestInputs").getString("testValidJsonResponseCms"));
    ContentChannels contentChannels =
        proxyCmsTranslatorImpl.mapCmsResponseJsonToContentChannels(jsonResultChannels);
    assertNotNull(contentChannels);
    contentChannels.getChannels().forEach(channel -> {
      assertNotNull(channel.getContentId());
      assertNotNull(channel.getContentFields());
    });
  }

  @Test
  public void shouldReturnErrorWith_withInvalidJsonResponse()
  {
    JsonObject jsonResultChannels = new JsonObject(TEST_JSON_INPUTS
        .getJsonObject("TestInputs").getString("testValidJsonResponseCms"));
    ContentChannels contentChannels =
        proxyCmsTranslatorImpl.mapCmsResponseJsonToContentChannels(jsonResultChannels);
    assertNotNull(contentChannels);
    contentChannels.getChannels().forEach(channel -> {
      assertNotNull(channel.getContentId());
      assertNotNull(channel.getContentFields());
    });
  }

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

}
