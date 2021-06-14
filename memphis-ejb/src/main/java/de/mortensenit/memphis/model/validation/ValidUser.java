package de.mortensenit.memphis.model.validation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Annotation used by JSR 303 validation framework to validate a user entity
 * 
 * @author Frederik Mortensen
 */
@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { UserValidator.class })
@Documented
public @interface ValidUser {

	String message() default "Ungültiger Benutzer!";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}