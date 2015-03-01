package it.siletto.ms.auth;



import io.dropwizard.auth.Authenticator;

import com.sun.jersey.api.model.Parameter;
import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.core.spi.component.ComponentScope;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.InjectableProvider;

/**
 * <p>Authentication provider to provide the following to Jersey:</p>
 * <ul>
 * <li>Bridge between Dropwizard and Jersey for HMAC authentication</li>
 * <li>Provides additional {@link uk.co.froot.demo.openid.model.security.Authority} information</li>
 * </ul>
 *
 * @param <T>    the principal type.
 * @since 0.0.1
 */
public class RestrictedToProvider<T> implements InjectableProvider<RestrictedTo, Parameter> {

  private final Authenticator<Credentials, T> authenticator;
  private final String realm;

  /**
   * Creates a new {@link RestrictedToProvider} with the given {@link com.yammer.dropwizard.auth.Authenticator} and realm.
   *
   * @param authenticator the authenticator which will take the {@link Credentials} and
   *                      convert them into instances of {@code T}
   * @param realm         the name of the authentication realm
   */
  public RestrictedToProvider(Authenticator<Credentials, T> authenticator, String realm) {
    this.authenticator = authenticator;
    this.realm = realm;
  }

  @Override
  public ComponentScope getScope() {
    return ComponentScope.PerRequest;
  }

  @Override
  public Injectable<?> getInjectable(ComponentContext ic,
                                     RestrictedTo a,
                                     Parameter c) {
    return new RestrictedToInjectable<T>(authenticator, realm, a.value());
  }
}

