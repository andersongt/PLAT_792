package com.fox.rampup.plat792.infra.serv;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fox.platform.lib.vrt.AbstractConfigurationVerticle;
import com.fox.rampup.plat792.dom.ent.ContentChannels;
import com.fox.rampup.plat792.infra.conf.RestCMSChannelsVerticleConfig;
import com.fox.rampup.plat792.infra.dep.ProxyCMSModule;
import com.fox.rampup.plat792.infra.repo.ChannelCMSRepository;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.newrelic.agent.deps.org.apache.http.HttpStatus;
import io.vertx.core.Future;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

/**
 * This Verticle processes messages send by API endpoints
 * 
 * @author andersonv
 *
 */
public class ProxyCMSVerticle extends AbstractConfigurationVerticle
{

  @Inject
  private ChannelCMSRepository cmsRepository;
  private JsonObject jsonResultChannels;
  @Inject
  private RestCMSChannelsVerticleConfig restCMSChannelsVerticleConfig;


  public void onMessage(Message<JsonObject> message)
  {
    String countryCode = message.body().getString("country");

    countryCode = countryCode.toUpperCase();

    this.cmsRepository.getChannelsFields(countryCode, resultChannels -> {
      if (resultChannels.succeeded())
      {
        ContentChannels contentChannels = resultChannels.result();

        ObjectMapper mapper = new ObjectMapper();
        try
        {
          jsonResultChannels = new JsonObject(mapper.writeValueAsString(contentChannels));
        } catch (JsonProcessingException e)
        {
          e.printStackTrace();
        }

        message.reply(jsonResultChannels);

      } else
      {
        message.fail(HttpStatus.SC_BAD_REQUEST, resultChannels.cause().getMessage());
      }
    });
  }

  @Override
  public void start(Future<Void> future) throws Exception
  {
    Future<Void> startFuture = Future.future();
    super.start(startFuture);


    Guice.createInjector(new ProxyCMSModule(vertx, config())).injectMembers(this);

    startFuture.setHandler(handler -> {

      if (handler.succeeded())
      {
        vertx.eventBus().consumer(restCMSChannelsVerticleConfig
            .getProxyCMSChannelsVerticleConfig().getAddress(), this::onMessage);

        future.complete();
      } else
      {
        future.fail(handler.cause());
      }
    });
  }


}
