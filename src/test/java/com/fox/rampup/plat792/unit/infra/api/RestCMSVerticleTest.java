package com.fox.rampup.plat792.unit.infra.api;

import static org.junit.Assert.*;
import java.net.ServerSocket;
import org.junit.Before;
import org.junit.Test;
import io.vertx.core.Vertx;

/**
 * @author andersonv
 *
 */
public class RestCMSVerticleTest
{
  private Vertx vertx;
  private int port;
  

  /**
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception
  {
    try {
      ServerSocket socket = new ServerSocket(0);
      port = socket.getLocalPort();
      socket.close();
    } catch (Exception ex) {
      port = 8080;
    }
    
  }
  
  

  @Test
  public void test()
  {
    assertNull(null);
  }

}
