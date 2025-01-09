@echo off

FOR /F "usebackq delims=" %%L IN ("..\approvaltests-tests\.approval_tests_temp\.failed_comparison.log") DO (
    REM Store the entire line in an environment variable named LINE
    SET "LINE=%%L"
    REM Call a subroutine to parse and handle the line
    CALL :ProcessLine
)

GOTO :EOF

:ProcessLine
    REM Enable delayed expansion so we can safely manipulate LINE
    SETLOCAL ENABLEDELAYEDEXPANSION

    REM Replace " -> " with a pipe character so we can split easily
    SET "LINE=!LINE: -> =|!"

    REM Now split the line on the pipe and capture each side
    FOR /F "tokens=1,2 delims=|" %%A IN ("!LINE!") DO (
        ECHO Moving "%%~A" to "%%~B"
        MOVE "%%~A" "%%~B"
    )

    ENDLOCAL
GOTO :EOF