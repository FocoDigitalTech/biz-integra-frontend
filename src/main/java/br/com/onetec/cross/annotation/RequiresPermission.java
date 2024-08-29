package br.com.onetec.cross.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) // Pode ser aplicado a métodos
public @interface RequiresPermission {
    String value(); // Define o tipo de permissão necessário
    boolean canOpenModal() default false; // Define se a permissão inclui abertura de modal

}
