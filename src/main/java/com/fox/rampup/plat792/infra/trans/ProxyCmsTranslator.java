package com.fox.rampup.plat792.infra.trans;

import java.util.Map;
import com.fox.rampup.plat792.dom.ent.ContentChannels;
import io.vertx.core.json.JsonObject;

/**
 * This interface define translations for requests to external systems and responses to our
 * infrastructure.
 * 
 * @author andersonv
 *
 */
public interface ProxyCmsTranslator
{
  public JsonObject mapRequestBodyGetConentToJson(String payload,
      Map<String, String> params);

  public ContentChannels mapCmsResponseJsonToContentChannels(JsonObject resultChannels);



}
