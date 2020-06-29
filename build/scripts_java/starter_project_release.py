from scripts.git_utilities import GitUtilities
from scripts.utilities import use_directory, replace_text_in_file, check_step
from scripts_java.release_constants import release_constants
from scripts_java.release_details import ReleaseDetails


class PrepareStarterProjectRelease:
    @staticmethod
    def prepare_starter_project(details: ReleaseDetails) -> None:
        GitUtilities.reset_and_clean_working_directory(release_constants.starter_project_dir)
        PrepareStarterProjectRelease.update_pom(details)

        GitUtilities.add_and_commit_everything(release_constants.starter_project_dir, details.new_version.get_version_text())
        GitUtilities.push_active_branch_origin(release_constants.starter_project_dir)
    @staticmethod
    def update_pom(details: ReleaseDetails) -> None:
        with use_directory(release_constants.starter_project_dir):
            old = details.old_version.get_version_text_without_v()
            new = details.new_version.get_version_text_without_v()
            replace_text_in_file("pom.xml", old, new)
