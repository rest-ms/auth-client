package it.siletto.ms.auth;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Annotation to provide the following to application:</p>
 * <ul>
 * <li>Concise type-safe reference to {@link uk.co.froot.demo.openid.model.security.Authority}</li>
 * <li>Binds to parameter to assist injection of User</li>
 * </ul>
 * <p>Example:</p>
 * {@code
 * public void doSomething(
 * &#064;RestrictedTo({ROLE_ADMIN, ROLE_PUBLIC})
 * User user
 * )
 * }
 * <p>Would require a User with both authorities to be able to access the method</p>
 * <strong>The default Authority is ROLE_ADMIN indicating that if no authority is specified then only an admin can reach the resource (fail safe).</strong>
 *
 * @since 0.0.1
 *         
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface RestrictedTo {
	String[] value() default "user";
}