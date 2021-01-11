| Example Date | RegEx Pattern |
| :-------------------- | :----------------------- | 
#foreach($f in $commons.asArray($formats))
    | $f.get() | $datescrubber.getScrubberFor($f.get()).getPattern() |
#end
