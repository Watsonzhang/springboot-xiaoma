package com.xiaoma.zhangwei.app.service;

import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2020/12/15 下午4:04
 */
@Service
public class TransactionalServiceBootstrap {
    /*public static void main(String[] args) throws IOException {
        String name = TransactionalServiceBootstrap.class.getName();
        CachingMetadataReaderFactory factory = new CachingMetadataReaderFactory();
        MetadataReader metadataReader = factory.getMetadataReader(name);
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        Set<String> annotationTypes = annotationMetadata.getAnnotationTypes();
        for (String s: annotationTypes){
            Set<String> metaAnnotationTypes = annotationMetadata.getMetaAnnotationTypes(s);
            for (String s1:metaAnnotationTypes){
                Set<String> types = annotationMetadata.getMetaAnnotationTypes(s1);
                System.out.println(types);
            }
        }
    }*/

    @Override
    public String toString() {
        System.out.println("this is toString");
        return "TransactionalServiceBootstrap{}";
    }
}
