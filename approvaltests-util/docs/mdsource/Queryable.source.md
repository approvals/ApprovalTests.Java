<a id="top"></a>

# Queryable

toc

## Extending Queryable

You have a function you wish was part of Queryable.
snippet: custom-query
You can add this to Queryable by implementing the `com.lambda.utils.Extendable` interface.
snippet: implementing-extendable
Now you can add extension methods that are **not static**
snippet: extendable-query
and now you can call it as such
snippet: custom-query-example
whereas previously you had to use
snippet: custom-query-example-static

---

[Back to User Guide](README.md#top)
