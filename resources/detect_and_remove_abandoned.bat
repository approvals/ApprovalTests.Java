@echo off
setlocal enabledelayedexpansion

REM Define paths
set "SCRIPT_DIR=%~dp0"
set "LOG_FILE=%SCRIPT_DIR%.approved_files.log"
set "BASE_DIR=%SCRIPT_DIR%.."

REM Check if the log file exists
if not exist "%LOG_FILE%" (
    echo Error: Log file .approved_files.log not found in script directory.
    exit /b 1
)

REM Find all *.approved.* files recursively from the base directory
echo Scanning for approval files in base directory: %BASE_DIR%...
for /r "%BASE_DIR%" %%f in (*.approved.*) do (
    call :ResolvePath "%%f" resolved_path
    set "ALL_APPROVAL_FILES=!ALL_APPROVAL_FILES!%%f;"
)

REM Resolve paths in .approved_files.log
echo Resolving paths from .approved_files.log...
set "RESOLVED_USED_FILES="
for /f "usebackq delims=" %%l in ("%LOG_FILE%") do (
    call :ResolvePath "%%l" resolved_path
    set "RESOLVED_USED_FILES=!RESOLVED_USED_FILES!!resolved_path!;"
)

REM Compare files and identify abandoned ones
echo Identifying abandoned approval files...
set "ABANDONED_FILES="
for %%f in (!ALL_APPROVAL_FILES:;= !) do (
    call :ResolvePath "%%f" resolved_path
    if "!RESOLVED_USED_FILES:;=!" neq "!RESOLVED_USED_FILES:;=%%resolved_path%%;!" (
        set "ABANDONED_FILES=!ABANDONED_FILES!!resolved_path!;"
    )
)

REM Display abandoned files
if "!ABANDONED_FILES!"=="" (
    echo No abandoned approval files found.
    exit /b 0
)

echo The following approval files are abandoned:
for %%f in (!ABANDONED_FILES:;= !) do (
    echo  %%f
)

REM Prompt for deletion
set /p "RESPONSE=Would you like to delete these files? (y/n): "
if /i "!RESPONSE!"=="y" (
    for %%f in (!ABANDONED_FILES:;= !) do (
        del /q "%%f" && echo Deleted: %%f
    )
    echo All abandoned files deleted.
) else (
    echo No files were deleted.
)

exit /b 0

REM Function to resolve paths
:ResolvePath
for %%i in (%1) do set "%2=%%~fi"
exit /b
