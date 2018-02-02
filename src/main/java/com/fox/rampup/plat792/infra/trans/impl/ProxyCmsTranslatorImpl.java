package com.fox.rampup.plat792.infra.trans.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.text.StrSubstitutor;
import com.fox.rampup.plat792.dom.ent.ContentChannels;
import com.fox.rampup.plat792.dom.ent.ContentObject;
import com.fox.rampup.plat792.dom.ent.Field;
import com.fox.rampup.plat792.infra.trans.ProxyCmsTranslator;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * This class implements operations to translate requests and responses between systems.
 * 
 * @author andersonv
 *
 */
public class ProxyCmsTranslatorImpl implements ProxyCmsTranslator
{


  @Override
  public JsonObject mapRequestBodyGetConentToJson(String payload,
      Map<String, String> pMap)
  {
    StrSubstitutor substitutor = new StrSubstitutor(pMap);
    return new JsonObject(substitutor.replace(payload));
  }


  @Override
  public ContentChannels mapCmsResponseJsonToContentChannels(
      JsonObject jsonResultChannels)
  {
    ContentChannels resultContent = new ContentChannels();

    List<ContentObject> listChannels = new ArrayList<>();
    JsonArray arrayHits = jsonResultChannels.getJsonObject("hits").getJsonArray("hits");
    for (Object object : arrayHits)
    {
      JsonObject currentHit = (JsonObject) object;


      JsonArray arrayInnerHits = currentHit.getJsonObject("inner_hits")
          .getJsonObject("groups").getJsonObject("hits").getJsonArray("hits");

      for (Object object2 : arrayInnerHits)
      {
        ContentObject contentObj = new ContentObject();

        JsonObject innerHit = (JsonObject) object2;
        contentObj.setContentId(innerHit.getJsonObject("_source").getString("id"));
        JsonObject objectFields =
            innerHit.getJsonObject("_source").getJsonObject("fields");
        List<Field> listFields = new ArrayList<>();
        objectFields.forEach(item -> {
          Field currentField = new Field(item.getKey(), item.getValue().toString());
          listFields.add(currentField);
        });
        contentObj.setContentFields(listFields);
        listChannels.add(contentObj);
      }

    }
    resultContent.setChannels(listChannels);



    return resultContent;
  }



}
