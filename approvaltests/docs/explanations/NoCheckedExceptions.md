<a id="top"></a>

# The No Checked Exceptions Philosophy

<!-- toc -->
## Contents
 
  * [Paintables](#paintables)
    * [Why](#why)
  * [How To](#how-to)
    * [create an animated gif](#create-an-animated-gif)<!-- endToc -->
    
## Checked Exceptions Were a Mistake
Java was the first (and last) language to experiment with checked exceptions. The fact that no other language has adopted this "feature" should be strong enough evidence that it was a mistake.

The "no checked exceptions" philosophy means to stop the propagation of this mistake as we move forward.

## Mechanics
Java provides two mechanisms for unchecked exceptions. The widely-known `RuntimeException` and the lesser-known `Error`. The implementation of this philosophy is to simply wrap all checked exceptions in one of these two unchecked exceptions and all new exceptions that are created are of this type or a derivative.

### ObjectUtils.throwAsError
We have created utility methods to facilitate this.

#### ObjectUtils.throwAsError(() -> methodThatThrowsException(parameters))
If you have a single line that throws a checked exception, you can easily wrap it in a lambda and get the same thing that you would with an unchecked exception.

#### throw ObjectUtils.throwAsError(myCheckedException)
If you want to rethrow from a try/catch block this method will rethrow what is passed-in wrapped in an unchecked exception (if needed). This method also states that it returns an unchecked exception allowing you to pretend to throw it, so you can easily compile code without having to add unnecessary return statements. For example:

snippet: throw_as_error_does_not_compile

Even though this would run, it won't compile. So instead you have to write the code like:

snippet: throw_as_error_with_return

Since this return statement is never reached, instead we write it as throwing an exception returned from the method call.

snippet: throw_as_error_with_throw




