#! /usr/bin/env python3

from scripts import version
from scripts.prepare_release import build

if __name__ == '__main__':
    build(lambda v: v.update_patch(), deploy = True)
