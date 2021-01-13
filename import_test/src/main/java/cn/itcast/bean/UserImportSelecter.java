package cn.itcast.bean;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class UserImportSelecter implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
//        获取配置类名称
        return new String[]{UserConfigation.class.getName()};
    }
}
