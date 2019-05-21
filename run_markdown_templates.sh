#!/usr/bin/env bash

# to run this in cygwin (after installing dos2unix):
#   cat run_markdown_templates.sh | dos2unix | bash

# ---------------------------------------------------
# Update tables of contents
# ---------------------------------------------------

# To install:
#   npm install -g doctoc

doctoc --title '**Contents**' .

# ---------------------------------------------------
# Update code examples
# ---------------------------------------------------
# For info on mdsnippets, see https://github.com/SimonCropp/MarkdownSnippets

# install dotnet SDK from http://go.microsoft.com/fwlink/?LinkID=798306&clcid=0x409
# Then install MarkdownSnippets.Tool with
#   dotnet tool install -g MarkdownSnippets.Tool
# To update:
#   dotnet tool update  -g MarkdownSnippets.Tool

dotnet tool update  -g MarkdownSnippets.Tool
~/.dotnet/tools/mdsnippets --exclude target 
