/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gordon.study.spring.geekbang.u03overview.dependency.injection;

import gordon.study.spring.geekbang.u03overview.repository.UserRepository;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

/**
 * 依赖注入示例
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since
 */
public class DependencyInjectionDemo {

    public static void main(String[] args) {
        // 配置 XML 配置文件
        // 启动 Spring 应用上下文
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/geekbang/overview/dependency-injection-context.xml");

        UserRepository userRepository = applicationContext.getBean("userRepository", UserRepository.class);
        // 依赖来源一：自定义 Bean
        System.out.println(userRepository.getUsers());
        // 依赖来源二：依赖注入（內建依赖）
        System.out.println(userRepository.getBeanFactory());
        System.out.println(userRepository.getBeanFactory() == applicationContext); // false

        ObjectFactory objectFactory = userRepository.getObjectFactory();
        System.out.println(objectFactory.getObject());
        System.out.println(objectFactory.getObject() == applicationContext); // true

        ObjectFactory userObjectFactory = userRepository.getUserObjectFactory();
        System.out.println(userObjectFactory.getObject());
        System.out.println(userRepository.getUsers().contains(userObjectFactory.getObject())); // true

        // 依赖查找（错误）
//        System.out.println(beanFactory.getBean(BeanFactory.class));

        // 依赖来源三：容器內建 Bean
        Environment environment = applicationContext.getBean(Environment.class);
        System.out.println("获取 Environment 类型的 Bean：" + environment);

        whoIsIoCContainer(userRepository, applicationContext);
    }

    private static void whoIsIoCContainer(UserRepository userRepository, ApplicationContext applicationContext) {
        // ConfigurableApplicationContext <- ApplicationContext <- BeanFactory
        // ConfigurableApplicationContext#getBeanFactory()

        // 这个表达式为什么不会成立
        System.out.println(userRepository.getBeanFactory() == applicationContext); // false

        // ApplicationContext is BeanFactory
        ////-- ApplicationContext 是 BeanFactory 的超集。ApplicationContext 通过组合而非继承的方式获得 BeanFactory 的能力。
        System.out.println(userRepository.getBeanFactory() == ((ConfigurableApplicationContext) applicationContext).getBeanFactory()); // true
    }

}

/*
    UserRepository 中的 BeanFactory beanFactory 属性为什么注入的是 DefaultListableBeanFactory，而不是 ClassPathXmlApplicationContext？
既然他们两者属于 BeanFactory 接口，按 type 注入，为什么不是 ClassPathXmlApplicationContext？
    答案在 org.springframework.context.support.AbstractApplicationContext#prepareBeanFactory 方法中，其中：
beanFactory.registerResolvableDependency(BeanFactory.class, beanFactory);
beanFactory.registerResolvableDependency(ResourceLoader.class, this);
beanFactory.registerResolvableDependency(ApplicationEventPublisher.class, this);
beanFactory.registerResolvableDependency(ApplicationContext.class, this);
以上代码明确地指定了 BeanFactory 类型的对象是 ApplicationContext#getBeanFactory() 方法的内容，而非它自身。
*/