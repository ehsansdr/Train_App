package com.example.trainproject;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

//@SpringBootTest
class TrainProjectApplicationTests {

    @Test
    void streamTesting() {
        ArrayList<Integer> la1 =  new ArrayList<Integer>();
        la1.add(1);
        la1.add(2);
        la1.add(3);
        la1.add(4);
        la1.add(5);
        la1.add(6);
        la1.add(7);
        System.out.println("la1 : " + la1);

        ArrayList<Integer> la2 =  new ArrayList();
        la2.add(1);
        la2.add(2);
        la2.add(3);
        la2.add(4);
        System.out.println("la2 : " + la2);

        List<Integer> la3;
        // this will create the list of lists 2D list mabye
        // la3 = Stream.of(la1, la2).toList();

        // System.out.println("la3 : " + la3);
        List<Integer> la4;
        la4 = la1.stream()
                .filter(l -> l.equals(3)) // l is the single element of list
                .toList();
        System.out.println("filter : la4 -> "  + la4);

        List<Integer> la5;
        la5 = la1.stream()
                // map() is used for transforming the element
                .map(l -> l + 8) // doing operation on each element
                .toList();
        System.out.println("map : la5 -> " +  la5);

        List<Integer> la6;
        la6 = la1.stream()
                .sorted(Comparator.reverseOrder()) // doing operation on each element
                .toList();
        System.out.println("sort : la6 -> " +  la6);

        List<Integer> la7 = Arrays.asList(1, 2, 3, 4, 5, 2, 3, 3,3,3,3,3,3,3, 1, 6);
        List<Integer> la8;
        la8 = la7.stream()
                .distinct() // doing operation on each element
                .sorted(Comparator.reverseOrder())
                .toList();
        System.out.println("distinct : la8 -> " +  la8);

        System.out.println("******");
        Stream.of("ehsan","ali","hassan")
                .filter(s -> s.length() > 3)
                .peek(System.out::println) // do on each object. peek() is mainly used for debugging or observing elements
                // Elements that are processed inside peek() might not be eligible for terminal operation
                .collect(Collectors.toList()); // with out this, will not execute
        System.out.println("******");

        Stream.of("ehsan","ali","hassan")
                .map(String::toUpperCase) // with peek it does not work
                .peek(System.out::println)
                .collect(Collectors.toList());  // with out this, will not execute




    }

}
