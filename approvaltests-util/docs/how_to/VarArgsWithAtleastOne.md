<a id="top"></a>

# How to create non-empty arrays with var args

toc

## The problem

Let's say you want to accept a variable amount of arguments, but you have a minimum of one that is required.
Most solutions for this occur at runtime. Of course, it would be better if they occurred at compile time.

### Runtime solution

snippet: minimalVarargsRuntime

### Compile time solution

An easy way to deal with this is force the first parameter by declaring it explicitly.
If you do this, you will want to recombine this array almost immediately `ArrayUtils.combine(T, T...)` is an elegant way to do this.
Please be aware that it will not work with primitives.

snippet: minimalVarargsCompileTime

### Advantages

If you use the runtime solution, the following will compile but throw an error when you run it.  
If you use the compile time solution, it will not compile.

snippet: minimalVarargsException

### Where to use this

Even though this only adds a small amount of complexity, we don't tend to use it for methods that are only called internally.
However, it is highly suggested if you have a public facing method.

Following the philosophy of [poka-yoke](https://en.wikipedia.org/wiki/Poka-yoke) or mistake proofing.
