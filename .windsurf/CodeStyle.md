# Code Style

## Comments

Remove all comments from the code.
If the comment is explaining a block of code, extract the block as a reusable method and give it a name
If the comment is explaining a variable, extract one with a name or give an existing variable a better name
Keep comments related to .md snippets (these comments will start with // begin-snippet and end with // end-snippet)

## Testing Console output

When you are testing Terminal output, use a try with resources to redirect the console output to a string and then verify that string. 

## Clean Up

Assume tests always start in a clean state, 
use a finally block to clean up any global state that was Created for this test.
