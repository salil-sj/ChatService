## Intermediate Operations

Intermediate operations are those that transform a stream into another stream, and they are lazy. They are not executed until a terminal operation is invoked. Some common intermediate operations include:




*  filter(Predicate<? super T> predicate): Filters elements based on a predicate.

*  map(Function<? super T, ? extends R> mapper): Transforms elements using a function.

*  flatMap(Function<? super T, ? extends Stream<? extends R>> mapper): Flattens nested streams into a single stream.

*  distinct(): Removes duplicate elements.

*  sorted(): Sorts elements in natural order.

*  sorted(Comparator<? super T> comparator): Sorts elements using a comparator.

*  peek(Consumer<? super T> action): Performs an action on each element (useful for debugging).
```java
public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

        names.stream()
             .filter(name -> name.length() > 3)     // Filter names with more than 3 characters
             .peek(name -> System.out.println("Filtered name: " + name))  // Log each filtered name
             .map(String::toUpperCase)              // Convert names to uppercase
             .forEach(System.out::println);        // Print each name
    }
```
* limit(long maxSize): Truncates the stream to the specified size.
```java
 public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");

        List<String> limitedNames = names.stream()
                                         .limit(3)  // Limit the stream to the first 3 elements
                                         .collect(Collectors.toList());

        System.out.println(limitedNames);  // Output: [Alice, Bob, Charlie]
    }
```
* skip(long n): Skips the first n elements of the stream.
  - The skip method in Java Streams is used to bypass a specified number of elements at the beginning of the stream and then process the remaining elements.
    takeWhile(Predicate<? super T> predicate): Takes elements while the predicate is true.
```java
public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");

        List<String> skippedNames = names.stream()
                                         .skip(2)  // Skip the first 2 elements
                                         .collect(Collectors.toList());

        System.out.println(skippedNames);  // Output: [Charlie, David, Eve]
    }
```
* dropWhile(Predicate<? super T> predicate): Skips elements while the predicate is true.
  - The dropWhile method in Java Streams is an intermediate operation that allows you to skip elements in the stream as long as a given predicate is true
```java
public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        List<Integer> result = numbers.stream()
                                      .dropWhile(n -> n < 5)  // Skip elements while they are less than 5
                                      .collect(Collectors.toList());

        System.out.println(result);  // Output: [5, 6, 7, 8, 9]
    }
```  
* mapToInt(ToIntFunction<? super T> mapper): Converts a stream of objects to an IntStream.
  * The mapToInt method in Java Streams is a specialized intermediate operation that transforms each element of a stream into an int value. It is part of the Stream API and operates on streams of objects, converting them into an IntStream. 
mapToDouble(ToDoubleFunction<? super T> mapper): Converts a stream of objects to a DoubleStream.

  * The mapToInt method in Java Streams uses the ToIntFunction functional interface, not the more general Function interface. Here's a clear distinction:

    * ToIntFunction<T>
    Definition: ToIntFunction<T> is a functional interface that represents a function that takes an input of type T and returns an int value.
```java
    public static void main(String[] args) {

        List<String> list = Arrays.asList("1" , "2", "3", "4", "5");

        List<Integer> lis2 =  list.stream().mapToInt(x-> Integer.valueOf(2)).collect(Collectors.toList());


        }
        
        //The above code will fail because: mapToInt returns an IntStream, which is a specialized stream for primitive int values, and does not support direct collection into a List<Integer>. The collect method in the Stream API works with streams of objects, but IntStream is a stream of primitives, so you need to convert it back to a stream of boxed integers before collecting.
        //  You can do this using the boxed() method, which converts each int to an Integer. After converting, you can then use collect(Collectors.toList()).

public static void main(String[] args) {

        List<String> list = Arrays.asList("1" , "2", "3", "4", "5");

        List<Integer> lis2 =  list.stream().mapToInt(x-> Integer.valueOf(2)).boxed().collect(Collectors.toList());


        }

```


* mapToLong(ToLongFunction<? super T> mapper): Converts a stream of objects to a LongStream.
  * The mapToLong method in Java Streams is similar to mapToInt, but it maps each element of the stream to a long value instead of an int. It’s an intermediate operation that transforms the elements of the stream into a LongStream. 
  * LongStream mapToLong(ToLongFunction<? super T> mapper)
```java
 List<String> numbers = Arrays.asList("10", "20", "30", "40", "50");

        LongStream longStream = numbers.stream()
                                       .mapToLong(Long::parseLong)  // Convert each String to long
                                       .sorted();  // Sort the LongStream

        longStream.forEach(System.out::println);  // Output: 10 20 30 40 50
    }
```
* boxed(): Converts a primitive stream to a stream of objects (e.g., IntStream to Stream<Integer>).


## Terminal Operations
### Terminal operations are operations that produce a result or a side-effect and terminate the stream pipeline. They trigger the processing of the stream. Some common terminal operations include:



* forEach(Consumer<? super T> action): Performs an action for each element.
  * The forEach method in Java Streams is a terminal operation that performs an action for each element of the stream. It processes each element in the stream and applies the specified action, typically to perform side effects like printing or modifying external state. 
```java
public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

        names.stream()
             .forEach(name -> System.out.println(name));  // Print each name

        // Alternatively, using method reference
        names.stream()
             .forEach(System.out::println);
    }
```
* collect(Collector<? super T, A, R> collector): Collects the elements into a collection or other results.
  * The collect method in Java Streams is a terminal operation that transforms the elements of a stream into a different form, typically a collection like a List, Set, or Map. It is a powerful and flexible method that allows you to accumulate the results of stream processing into a container. 

```java
        List<String> list = stream.collect(Collectors.toList());
        //
        Set<String> set = stream.collect(Collectors.toSet());
        //
        Map<String, Integer> map = stream.collect(Collectors.toMap(String::toUpperCase, String::length));
        //
        String result = stream.collect(Collectors.joining(", ", "[", "]"));
        //
        Map<Integer, List<String>> groupedByLength = stream.collect(Collectors.groupingBy(String::length));
```  

* reduce(T identity, BinaryOperator<T> accumulator): Performs a reduction on the elements using an associative accumulation function and an identity value.
  * The reduce method in Java Streams is a terminal operation that performs a reduction on the elements of the stream using an associative accumulation function and returns an Optional result. It's used to aggregate or combine the elements of the stream into a single result. 
reduce(BinaryOperator<T> accumulator): Performs a reduction on the elements using an associative accumulation function.

count(): Counts the number of elements in the stream.

min(Comparator<? super T> comparator): Finds the minimum element according to the comparator.

max(Comparator<? super T> comparator): Finds the maximum element according to the comparator.

anyMatch(Predicate<? super T> predicate): Checks if any elements match the predicate.

allMatch(Predicate<? super T> predicate): Checks if all elements match the predicate.

noneMatch(Predicate<? super T> predicate): Checks if no elements match the predicate.

findFirst(): Finds the first element of the stream.

findAny(): Finds any element of the stream.

toArray(): Converts the stream to an array.

toArray(IntFunction<A[]> generator): Converts the stream to an array with a specified generator.

sum(): Computes the sum of elements in numeric streams (IntStream, LongStream, DoubleStream).

average(): Computes the average of elements in numeric streams (IntStream, LongStream, DoubleStream).

join(CharSequence delimiter): Concatenates the elements of the stream into a single String, with the specified delimiter.

toString(): Returns a String representation of the stream.
