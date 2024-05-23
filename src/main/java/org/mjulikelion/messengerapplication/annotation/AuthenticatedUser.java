package org.mjulikelion.messengerapplication.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)//런타임까지 어노테이션이 살아있도록
public @interface AuthenticatedUser {
}
