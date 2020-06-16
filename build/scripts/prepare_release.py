import os
import shutil
from typing import Callable

from git import Repo

from scripts import version
from scripts.documentation_release import PrepareDocumentationRelease
from scripts.release_constants import release_constants
from scripts.release_details import ReleaseDetails
from scripts.version import Version


class PrepareRelease:
    def __init__(self, details: ReleaseDetails) -> None:
        self.details = details


def build(update_version: Callable[[Version], Version], deploy: bool) -> None:
    old_version = load_current_version()
    new_version = update_version(old_version)
    release_details = ReleaseDetails(old_version, new_version)

    PrepareDocumentationRelease.prepare_documentation(release_details)


def load_current_version() -> Version:
    os.chdir("../../ApprovalTests.java")
    return Version.read(release_constants.build_dir)
