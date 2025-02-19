package com.example.trainproject.Util;

import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.List;

@Service
public class DynamicRepositoryService {

    @Autowired
    private ApplicationContext context;

    public <T> JpaRepository<T,Long> findAllByField(Class<T> entityClass, String fieldName, Object fieldValue) {
        // we get create the repo of the class for getting it repo but should handle exception
        JpaRepository<T,Long> repository = (JpaRepository<T,Long>) context.getBean(entityClass.getName());

        if (repository == null) {
            throw new IllegalArgumentException("No repository found for entity: " + entityClass.getSimpleName());
        }

        // Construct the method name dynamically
        String methodName = "findAllBy" + capitalize(fieldName);

        try {
            // Look for the method in the repository
            Method method = repository.getClass().getMethod(methodName, fieldValue.getClass());

            // Invoke the method dynamically
            return (JpaRepository<T, Long>) method.invoke(repository, fieldValue);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("No such method in repository: " + methodName, e);
        } catch (Exception e) {
            throw new RuntimeException("Error invoking repository method", e);
        }
    }

    public <T> JpaRepository<T, Long> getRepository(Class<T> entityClass) {
        // This assumes you're following a naming convention for repository beans
        String repositoryBeanName = entityClass.getSimpleName() + "Repository";
        return (JpaRepository<T, Long>) context.getBean(repositoryBeanName);
    }

    private String capitalize(String str) {
        // you pass the name of the field as camel case like : shortName and this method return this to ShortName
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}
