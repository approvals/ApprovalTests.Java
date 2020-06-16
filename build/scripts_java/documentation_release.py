import shutil
import time
from collections import Callable

from scripts_java.release_constants import release_constants
from scripts.utilities import read_file, use_directory, replace_text_in_file, run, check_step


from scripts_java.release_details import ReleaseDetails
from typing import Callable
class PrepareDocumentationRelease:
    @staticmethod
    def prepare_documentation(details: ReleaseDetails) -> None:
        PrepareDocumentationRelease.update_readme_and_docs(details)


    @staticmethod
    def update_readme_and_docs(details: ReleaseDetails) -> None:
        replace_text_in_file("README.source.md", details.old_version.get_version_text_without_v(), details.new_version.get_version_text_without_v())

