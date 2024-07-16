package org.luvx.boot.mars.runner.cdc.enable;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({CdcImportSelector.class})
public @interface EnableCdc {
}
