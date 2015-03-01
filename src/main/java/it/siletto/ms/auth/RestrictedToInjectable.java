package it.siletto.ms.auth;


import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

import java.util.Arrays;
import java.util.Set;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.server.impl.inject.AbstractHttpContextInjectable;

/**
 * <p>Injectable to provide the following to {@link RestrictedToProvider}:</p>
 * <ul>
 * <li>Performs decode from HTTP request</li>
 * <li>Carries OpenID authentication data</li>
 * </ul>
 *
 * @since 0.0.1
 */
class RestrictedToInjectable<T> extends AbstractHttpContextInjectable<T> {

  private static final Logger log = LoggerFactory.getLogger(RestrictedToInjectable.class);

  private final Authenticator<Credentials, T> authenticator;
  private final String realm;
  private final Set<String> requiredAuthorities;

  /**
   * @param authenticator The Authenticator that will compare credentials
   * @param realm The authentication realm
   * @param requiredAuthorities The required authorities as provided by the RestrictedTo annotation
   */
  RestrictedToInjectable(
    Authenticator<Credentials, T> authenticator,
    String realm,
    String[] requiredAuthorities) {
    this.authenticator = authenticator;
    this.realm = realm;
    this.requiredAuthorities = Sets.newHashSet(Arrays.asList(requiredAuthorities));
  }

  public Authenticator<Credentials, T> getAuthenticator() {
    return authenticator;
  }

  public String getRealm() {
    return realm;
  }

  public Set<String> getRequiredAuthorities() {
    return requiredAuthorities;
  }

  @Override
  public T getValue(HttpContext httpContext) {

    try {

      // Get the Authorization header
    	String sessionToken = httpContext.getRequest().getHeaderValue("Authentication");
    	sessionToken = sessionToken.substring("Bearer ".length()).trim();

        final Credentials credentials = new Credentials(sessionToken, requiredAuthorities);

        final Optional<T> result = authenticator.authenticate(credentials);
        if (result.isPresent()) {
          return result.get();

      }
    } catch (IllegalArgumentException e) {
      log.debug("Error decoding credentials",e);
    } catch (AuthenticationException e) {
      log.warn("Error authenticating credentials",e);
      throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
    } catch (Exception e) {
        throw new WebApplicationException(Response.Status.UNAUTHORIZED);
	}

    // Must have failed to be here
    throw new WebApplicationException(Response.Status.UNAUTHORIZED);
  }

}

