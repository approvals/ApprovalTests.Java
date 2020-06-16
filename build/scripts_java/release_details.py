from scripts.version import Version


class ReleaseDetails:
    def __init__(self, old_version: Version, new_version: Version) -> None:
        self.old_version = old_version
        self.new_version = new_version

    def old_version_as_text(self) -> str:
        return self.old_version.get_version_text()

    def new_version_as_text(self) -> str:
        return self.new_version.get_version_text()
