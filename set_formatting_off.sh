#!/usr/bin/env bash

if [[ "$OSTYPE" == "darwin"* ]]; then
  backup = '.back'
fi

sed -i $backup 's/<!-- formatter_plugin_begin -->/<!-- formatter_plugin_begin/g' pom.xml
sed -i $backup 's/<!-- formatter_plugin_end -->/ formatter_plugin_end -->/g' pom.xml
        