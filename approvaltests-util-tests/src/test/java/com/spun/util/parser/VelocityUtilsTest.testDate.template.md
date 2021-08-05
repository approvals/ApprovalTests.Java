#set($d = $commons.asDate($date))

| METHOD CALL | RESULT |
| ----------- | ------ |
| getDate("LONG")         | $d.getDate("LONG") |
| getDate("FULL","")      | $d.getDate("FULL", "") |
| getTime("AM/PM")        | $d.getTime("AM/PM") |
| getTime("AM/PM", "CET") | $d.getTime("AM/PM", "CET") |
| getDate()               | $d.getDate() |
| getDay()                | $d.getDay() |
| getMonth(1)             | $d.getMonth(1) |
| getMonthName()          | $d.getMonthName() |
| getYear()               | $d.getYear() |
| isToday()               | $d.isToday() |
| toString()              | $d.toString() |
| getDateAndTime(s,s)     | $d.getDateAndTime("SHORTDAY", "Milli") |
| getDateAndTime()        | $d.getDateAndTime() |
