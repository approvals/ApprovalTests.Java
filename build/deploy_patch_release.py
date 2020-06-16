#! /usr/bin/env python3

from scripts_java import version
from scripts_java.prepare_release import build

if __name__ == '__main__':
    build(lambda v: v.update_patch())
