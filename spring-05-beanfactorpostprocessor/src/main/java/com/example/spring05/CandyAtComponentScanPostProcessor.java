package com.example.spring05;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @time: 2022/11/27
 * @author: yuanyongan
 * @description: 自定义实现BeanFactory的ComponentScan后处理器
 */
public class CandyAtComponentScanPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanFactory) throws BeansException {
        try {

            // 查找又@ComponentScan的包名，结果为com.xxx.xx
            ComponentScan componentScan = AnnotationUtils.findAnnotation(Config.class, ComponentScan.class);
            if (componentScan != null) {
                for (String basePage : componentScan.basePackages()) {
                    System.out.println(basePage);
                    // 转换包名成路径名，com/xx/xx
                    String path = "classpath*:" + basePage.replace('.', '/') + "/**/*.class";
                    // 通过ApplicationContext的到所有文件
//                    GenericApplicationContext context = new GenericApplicationContext();
//                    Resource[] resources = context.getResources(path);

                    CachingMetadataReaderFactory factory = new CachingMetadataReaderFactory();
                    AnnotationBeanNameGenerator generator = new AnnotationBeanNameGenerator();
                    for (Resource resource : new PathMatchingResourcePatternResolver().getResources(path)) {
                        // 通过CachingMetadataReaderFactory读取类的元信息（二进制信息）
                        MetadataReader reader = factory.getMetadataReader(resource);
                        // 得到类类名
                        String className = reader.getClassMetadata().getClassName();
                        String name = Component.class.getName();
                        // 得到类中的所有注解结果
                        AnnotationMetadata annotationMetadata = reader.getAnnotationMetadata();

                        // 判断是否包含@Component或者相关派生注解
                        if (annotationMetadata.hasAnnotation(Component.class.getName())
                                || annotationMetadata.hasMetaAnnotation(name)) {
                            // 转换成BeanDefinition注入容器
                            AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(className).getBeanDefinition();
                            // 生成BeanName
                            String beanName = generator.generateBeanName(beanDefinition, beanFactory);

                            beanFactory.registerBeanDefinition(beanName, beanDefinition);
                        }

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
