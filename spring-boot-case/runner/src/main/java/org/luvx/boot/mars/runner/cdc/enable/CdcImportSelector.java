package org.luvx.boot.mars.runner.cdc.enable;

import org.luvx.boot.mars.runner.cdc.MysqlBinlogListener;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class CdcImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{MysqlBinlogListener.class.getName()};
    }
}
