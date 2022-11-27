# SpringLearning
学习Spring底层原理代码实践

#### BeanFactory和ApplicationContext
- BeanFactory为最上层的Bean接口，他提供了一些容器的基本约定，比如getBean等，主要通过DefaultListableBeanFactory进行实现,但是并不带有注解扫描等功能，这些都需要在ApplicationContext中进行拓展p
- ApplicationContext是IOC容器实现的主要载体，分为四种
  - ClassPathXmlApplicationContext：主要对xml文件进行读取
  ```java
   //读取resources文件下的xml文件
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring_bean.xml");
  ```
  - FileSystemXmlApplicationContext：与xml功能类似，这两种方式主要用于ssm中对xml的文件配置，springboot之后不再对xml文件进行配置了，而是通过注解的方式
  ```java
    //与xml不同的是从绝对或者相对路径下读取bean的配置，
    FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("src\\main\\resources\\spring_bean.xml");
  ```
  - AnnotationConfigApplicationContext:对注解进行扫描
  ```java
    // 输入带有@Configuration的类，对其中的注解进行扫描，并且进行Bean对象的注入
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
  ```
  - AnnotationConfigServletWebServerApplicationContext:适用于带有web的注解扫描
  ```java
    AnnotationConfigServletWebServerApplicationContext context = new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);
    ```
  上面的几种实现方式其实也是通过DefaultListableBeanFactory结合文件读取类来封装实现的
  ```java
    DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
    XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
    reader.loadBeanDefinitions(new FileSystemResource("src\\main\\resources\\spring_bean.xml"));

  ```
  
#### Bean的生命周期和模板方法设计模式
- Bean的周期分为五步
  - 创建Bean实例
  - 配置属性值
  - 初始化方法
  - 获取Bean实例对象
  - 执行销毁方法
- 模板方法：因为Bean的注入可能涉及到多种注解，怎么对代码的涉及更加灵活及简约，模板方法就非常重要，而Spring里面的很多后处理器都是通过模板方法来实现的

#### Bean后处理器

Bean后处理器主要为Bean生命周期的各个阶段进行拓展
- AutowiredAnnotationBeanPostProcessor:对@Autowired @Value注解的对象及变量进行处理
  - findAutowiringMetadata:对添加了相关注解的对象及变量进行扫描，返回InjectionMetadata类型
  - InjectionMetadata.inject 对Bean进行注册注入
