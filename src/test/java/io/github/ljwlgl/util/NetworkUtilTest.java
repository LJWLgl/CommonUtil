package io.github.ljwlgl.util;

import io.github.ljwlgl.util.NetworkUtil;
import org.junit.Test;

public class NetworkUtilTest {


  @Test
  public void getLocalHostAddress() throws Exception {
    System.out.println(NetworkUtil.getLocalHostAddress());
  }

  @Test
  public void getLocalHostName() throws Exception {
    System.out.println(NetworkUtil.getLocalHostName());
  }
}