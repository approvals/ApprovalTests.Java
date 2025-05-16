# Bash Script Style Process

- Use `#!/usr/bin/env bash` as shebang.
- Always use `set -euxo pipefail` for safety and debugging.
- Keep scripts minimal: no unnecessary comments or echoes.
- Only do minimal input validation; print a usage message to stderr and exit if inputs are missing or invalid.
- Do not check for installed commands if failure will be obvious on use.
- Make script executable: `chmod +x <script>`.
- Prefer concise, direct logic over verbosity.
