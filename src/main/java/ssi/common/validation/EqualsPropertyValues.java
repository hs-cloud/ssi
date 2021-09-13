package ssi.common.validation;

import javax.validation.*;
import java.lang.annotation.*;

@Documented
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface EqualsPropertyValues {
	
	String message() default "error　2つ異なるよ";
	Class<?>[] group() default{};
	Class<? extends Payload>[] payload() default{};
	
	String property();
	String comparingProperty();
	
	@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface List {
		EqualsPropertyValues[] value();
	}
}
