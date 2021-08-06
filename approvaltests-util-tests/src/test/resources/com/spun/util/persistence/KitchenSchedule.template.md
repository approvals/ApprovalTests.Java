#set ( $double_hash = '##' )

${double_hash} Schedule for Kitchen
${double_hash}# ${commons.asDate($day.getTime())}

| Person | Position | Start Time | 
| ---- | ------- | ------ |
#foreach($f in $commons.asArray($shifts))
| $f.get().getName() | $f.get().getPosition() | $commons.asDate($f.get().getStartTime().getTime()).getTime("AM/PM", "CET") |
#end
