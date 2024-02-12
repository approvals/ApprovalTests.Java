package org.approvaltests;

import org.approvaltests.core.Options;
import org.junit.jupiter.api.Test;
import org.lambda.functions.Function1;
import org.lambda.query.Queryable;

import java.util.Objects;

public class ParseInputTest {

    @Test
    void uppercasse() {
        var expected = """
                a -> A
                b -> B
                hh -> HH
                c -> C
                d -> D
                e -> E
                cco -> CCO
                f -> F
                g -> G
                eue -> EUE
                aza -> AZA
                """;
        ParseInput.create(expected).verifyAll(s -> s.toUpperCase());

    }

    @Test
    void toBinary() {
        var expected = """
                1 -> 1
                2 -> 10
                4 -> 100
                8 -> 1000
                9 -> 1001
                """;

        ParseInput.create(expected, Integer::parseInt)
                .verifyAll(s -> Integer.toBinaryString(s));

    }

    public static final class Person {
        private final String name;
        private final int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getAgeLabel() {
                return age > 20 ? "adult" : "teenager";
            }

        public String name() {
            return name;
        }

        public int age() {
            return age;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (Person) obj;
            return Objects.equals(this.name, that.name) &&
                   this.age == that.age;
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }

        @Override
        public String toString() {
            return "Person[" +
                   "name=" + name + ", " +
                   "age=" + age + ']';
        }


        }

    @Test
    void testPeople() {
        var expected = """
                Llewellyn, 25 -> adult
                Oliver, 15 -> teenager
                """;

        Creator<Person> createPerson = (a) -> new Person(a.first(), Integer.parseInt(a.last()));
        ParseInput.createFromParts(expected, createPerson)
                        .verifyAll(s -> s.getAgeLabel());

    }

    }