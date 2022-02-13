package edu.esoft.sdp.cw.pickandgoapi.facade;

import java.security.SecureRandom;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import xyz.downgoon.snowflake.Snowflake;

@Component
public class InternalIdGenerator implements InitializingBean {

  private Snowflake snowflake;

  public String getId() {
    final long id = snowflake.nextId();

    return String.format("%s => id:%d", snowflake.formatId(id), id).split("id:")[1].trim();
  }

  @Override
  public void afterPropertiesSet() {
    snowflake = new Snowflake(getDatacenterId(), getWorkerId());
  }

  private int getDatacenterId() {
    return getRandom(31);
  }

  private int getWorkerId() {
    return getRandom(31);
  }

  private int getRandom(final int bound) {
    return new SecureRandom().nextInt(bound);
  }
}
