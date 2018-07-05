#!/bin/sh
clear
export GPG_TTY=$(tty)
mvn clean deploy -Prelease
