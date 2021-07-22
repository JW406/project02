package org.Foo.Bar;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Component
public class SpringContextAccessor implements ApplicationContextAware {
  private static ApplicationContext applicationContext;

  final public static String googleClientID;
  final public static String googleClientSecret;
  final public static String githubClientID;
  final public static String githubClientSecret;
  final public static String stripeClientSecret;
  static {
    googleClientID = System.getenv("proj02_google_client_id");
    googleClientSecret = System.getenv("proj02_google_client_secret");
    githubClientID = System.getenv("proj02_github_client_id");
    githubClientSecret = System.getenv("proj02_github_client_secret");
    stripeClientSecret = System.getenv("proj02_stripe_client_secret");
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    SpringContextAccessor.applicationContext = applicationContext;
  }

  public static Object getBean(String name) {
    return applicationContext.getBean(name);
  }

  public static <T> T getBean(String name, Class<T> clazz) {
    return applicationContext.getBean(name, clazz);
  }
}
