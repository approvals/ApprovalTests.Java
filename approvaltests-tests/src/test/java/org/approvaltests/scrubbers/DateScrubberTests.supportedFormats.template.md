| Example Date | RegEx Pattern |
| :-------------------- | :----------------------- | 
#foreach($f in $commons.asArray($formats))
    | $commons.asArray($f.get().getExamples()).get(0).get() | $f.get().getRegex() |
#end
