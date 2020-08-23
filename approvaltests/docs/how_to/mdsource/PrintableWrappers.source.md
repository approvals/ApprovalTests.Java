<a id="top"></a>

# How to customize another objects `toString()` 
toc

### Printable wrappers

Sometimes an objects `toString()` are not what is desired for the give situation. This is particularly true with the parameter names used with `CombinationApprovals` 
Printable wrappers gives you a way to control this.

### Wrap a individual object
#### Label

If you just wanted to wrap a single object. You can create a printable wrapper by:
 
snippet: printable_single_label

to access the value, simply call

snippet: printable_access

#### Alternative `toString()` Function

You can also write a different function(lambda) to create the `toString()` and then apply it to the object.

snippet: printable_single_lambda

### Wrap an array of objects

For convenience, if you want to do this to a whole list of items we have 

snippet: printable_array_lambda

would produce

snippet: PrintableTest.testCreate.approved.txt

### Named objects 

We also has a convenience builder if you want each object to have it's own label.

snippet: printable_array_labels

would produce:

snippet: PrintableTest.testLabels.approved.txt


---

[Back to User Guide](../README.md#top)
