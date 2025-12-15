# Add Date Scrubber
**note:** All code examples are in python, please convert to the language of this project

The date scrubber has a method called `add_scrubber` that does the following:

## Purpose
Allows users to register custom date format patterns that aren't included in the built-in supported formats.

## Message
When a custom scrubber is added, a message is displayed to the user to let them know that a custom scrubber has been added 
Message: 
    You are using a custom date scrubber. If you think the format you want to scrub would be useful for others, please add it to https://github.com/approvals/ApprovalTests.Python/issues/124.

    To suppress this message, use 
    `DateScrubber.add_scrubber("<date format>", "<regex>", display_message=False)`


## Behavior
1. **Validates the regex pattern**: Checks if the provided regex is syntactically valid
2. **Validates the example**: Ensures the regex actually matches the provided example
3. **Stores the custom scrubber**: Adds the pattern to the internal `_custom_scrubbers` list
4. **Throws exceptions**: If regex is invalid or doesn't match the example

## Usage Example
```python
DateScrubber.add_scrubber("2023-Dec-25", r"\d{4}-[A-Za-z]{3}-\d{2}")
```

## Integration
Custom scrubbers are automatically included when calling `get_scrubber_for()`
You can clear the scrubbers by calling `DateScrubber._clear_custom_scrubbers()`





