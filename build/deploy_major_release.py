#! /usr/bin/env python3
import scripts_java.include_approvals_path # Note This is used!

from scripts_java.prepare_release import build

if __name__ == '__main__':
    build(lambda v: v.update_major())
