import shutil
import time
from collections import Callable

from scripts.release_constants import release_constants
from scripts.utilities import read_file, use_directory, replace_text_in_file, run, check_step


from scripts.release_details import ReleaseDetails
from typing import Callable
class PrepareStarterProjectRelease:
    @staticmethod
    def prepare_starter_project(details: ReleaseDetails) -> None:
        PrepareStarterProjectRelease.update_pom(details)



    @staticmethod
    def update_pom(details: ReleaseDetails) -> None:
        with use_directory(release_constants.starter_project_dir):
            old = details.old_version.get_version_text_without_v()
            new = details.new_version.get_version_text_without_v()
            replace_text_in_file("pom.xml", old, new)

