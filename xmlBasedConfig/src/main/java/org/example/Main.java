package org.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.example.models.Owner;
import org.example.models.Animal;
import org.example.models.HelloWorld;
import java.util.List;
import java.util.ArrayList;
import java.lang.String;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ApplicationContext contextXML = new ClassPathXmlApplicationContext("beans.xml");
        HelloWorld obj = (HelloWorld) contextXML.getBean("helloWorld");
        obj.getMessage();

        Animal animal1 = (Animal) contextXML.getBean("animal1");
        System.out.println(animal1);

        Animal animal2 = (Animal) contextXML.getBean("animal2");
        System.out.println(animal2);

        Owner owner1 = (Owner) contextXML.getBean("owner1");
        System.out.println(owner1);

        Owner owner2 = (Owner) contextXML.getBean("owner2");
        System.out.println(owner2);

        List<String> animalTypes = (List<String>) contextXML.getBean("animals");
        System.out.println(animalTypes);


    }
}