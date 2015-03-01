package it.siletto.ms.auth;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Set;

import com.google.common.base.Objects;

/**
 * <p>Value object to provide the following to {@link CredentialsAuthenticator}:</p>
 * <ul>
 * <li>Storage of the necessary credentials for OpenID authentication</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class Credentials {

  private final String sessionToken;
  private final Set<String> requiredAuthorities;

  /**
   * @param sessionToken        The session token acting as a surrogate for the OpenID token
   * @param requiredAuthorities The authorities required to authenticate (provided by the {@link uk.co.froot.demo.openid.auth.annotation.RestrictedTo} annotation)
   */
  public Credentials(
    String sessionToken,
    Set<String> requiredAuthorities) {
    this.sessionToken = checkNotNull(sessionToken);
    this.requiredAuthorities = checkNotNull(requiredAuthorities);
  }

  /**
   * @return The OpenID token
   */
  public String getSessionToken() {
    return sessionToken;
  }

  /**
   * @return The authorities required to successfully authenticate
   */
  public Set<String> getRequiredAuthorities() {
    return requiredAuthorities;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if ((obj == null) || (getClass() != obj.getClass())) {
      return false;
    }
    final Credentials that = (Credentials) obj;

    return sessionToken.equals(that.sessionToken);
  }

  @Override
  public int hashCode() {
    return (31 * sessionToken.hashCode());
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this)
      .add("sessionId", sessionToken)
      .add("authorities", requiredAuthorities)
      .toString();
  }

}
