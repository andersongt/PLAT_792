package com.fox.rampup.plat792.infra.api;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fox.platform.lib.vrt.AbstractConfigurationVerticle;
import com.fox.rampup.plat792.infra.conf.ApiEndpoint;
import com.fox.rampup.plat792.infra.conf.RestCMSChannelsVerticleConfig;
import com.fox.rampup.plat792.infra.dep.RestCMSChannelsModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.newrelic.agent.deps.org.apache.http.HttpStatus;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * This verticle exposes a set of API Endpoints to interact with many content 
 * servers through our infrastructure.
 * 
 * @author andersonv
 *
 */
public class RestCMSVerticle extends AbstractConfigurationVerticle
{
  private static final String API_ENDPOINT_CHANNELS = "getChannels";
  private static final String FIRST_PARAM = "country";
  private Logger logger = LoggerFactory.getLogger(RestCMSVerticle.class);
  @Inject
  private RestCMSChannelsVerticleConfig restCMSChannelsVerticleConfig;


  @Override
  public void start(Future<Void> future) throws Exception
  {
    Future<Void> startFuture = Future.future();
    super.start(startFuture);
    startFuture.compose(this::startVerticle).setHandler(future.completer());

  }

  private Future<Void> startVerticle(Void voidHandler)
  {
    Future<Void> verticleFuture = Future.future();
    Guice.createInjector(new RestCMSChannelsModule(vertx, config())).injectMembers(this);

    Router router = createRouter(restCMSChannelsVerticleConfig.getApiEndpoints());


    vertx.createHttpServer(restCMSChannelsVerticleConfig.getHttpServerOptions())
        .requestHandler(router::accept).listen(result -> {
          if (result.succeeded())
          {
            verticleFuture.complete();
          } else
          {
            verticleFuture.fail(result.cause());
          }
        });
    return verticleFuture;
  }


  private Router createRouter(Map<String, ? extends ApiEndpoint> apiEndpoints)
  {
    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());
    ApiEndpoint apiEndpointChannels = apiEndpoints.get(API_ENDPOINT_CHANNELS);
    router.route(apiEndpointChannels.getHttpMethod(), apiEndpointChannels.getApiPaht())
        .handler(context -> proccessRequest(context));
    return router;

  }


  private void proccessRequest(RoutingContext context)
  {
    JsonObject paramsMessage = new JsonObject();

    paramsMessage.put(FIRST_PARAM, context.request().getParam(FIRST_PARAM));
    vertx.eventBus().send(
        restCMSChannelsVerticleConfig.getProxyCMSChannelsVerticleConfig().getAddress(),
        paramsMessage, response -> {
          if (response.succeeded())
          {
            replyOkToClient((JsonObject) response.result().body(), context);

          } else
          {
            replyFailToClient(response.cause(), context);
          }
        });

  }

  private void reply(JsonObject response, RoutingContext context)
  {
    context.response().putHeader("content-type", "application/json; charset=utf-8");
    context.response().end(response.encode());
  }

  private void replyFailToClient(Throwable error, RoutingContext context)
  {
    logger.error("Reply an error to client: " + error.getMessage(), error);
    JsonObject response = new JsonObject();

    response.put("code", HttpStatus.SC_INTERNAL_SERVER_ERROR);
    response.put("message", error.getMessage());
    response.put("description", error.getMessage());
    context.response().setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    reply(response, context);
  }

  private void replyOkToClient(JsonObject response, RoutingContext context)
  {
    context.response().setStatusCode(HttpStatus.SC_OK);
    reply(response, context);
  }


}


