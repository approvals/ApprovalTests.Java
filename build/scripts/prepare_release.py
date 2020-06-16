import os
import shutil
from typing import Callable

from git import Repo

from scripts import version
from scripts.documentation_release import PrepareDocumentationRelease
from scripts.release_constants import release_constants
from scripts.release_details import ReleaseDetails
from scripts.starter_project_release import PrepareStarterProjectRelease
from scripts.utilities import run
from scripts.version import Version


class PrepareRelease:
    def __init__(self, details: ReleaseDetails) -> None:
        self.details = details

def publish_to_maven(details: ReleaseDetails) -> None:
    new = details.new_version.get_version_text_without_v()
    run(["mvn", "versions:set", f"-DnewVersion={new}"])
    # run("./publish_maven.sh")

def set_snapshot(details: ReleaseDetails) -> None:
    next = details.new_version.update_patch().get_version_text_without_v()
    run(["mvn", "versions:set", f"-DnewVersion={next}-SNAPSHOT"])

def build(update_version: Callable[[Version], Version]) -> None:
    old_version = load_current_version()
    new_version = update_version(old_version)
    release_details = ReleaseDetails(old_version, new_version)

    publish_to_maven(release_details)
    set_snapshot(release_details)
    PrepareDocumentationRelease.prepare_documentation(release_details)
    PrepareStarterProjectRelease.prepare_starter_project(release_details)


def load_current_version() -> Version:
    os.chdir("../../ApprovalTests.java")
    return Version.read(release_constants.build_dir)
