#!/usr/bin/env bash

sed -i 's/<!-- formatter_plugin_begin -->/<!-- formatter_plugin_begin/g' pom.xml
sed -i 's/<!-- formatter_plugin_end -->/ formatter_plugin_end -->/g' pom.xml
        