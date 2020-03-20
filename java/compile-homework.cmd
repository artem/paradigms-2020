@echo off

if "%~2" == "" (
    echo Usage: %~n0 repository-id homework
    exit /b 1
)

if "%~2" == "3" (
    set tests=queue
) else if "%~2" == "4" (
    set tests=queue
) else if "%~2" == "5" (
    set tests=expression\generic
) else (
    echo Unknown homework "%~2"
    echo Supported homeworks:
    echo    3 Array queue
    echo    4 Queues
    echo    5 Generic expressions
    exit /b 1
)

set "repo=%~dp0..\..\%~1"
call :check_dir "%repo%" || exit /b 1
call :check_dir "%repo%\java-solutions" || exit /b 1
call :check_dir "%repo%\java-solutions\%tests%" || exit /b 1

if exist "%~dp0_build" rmdir /s /q "%~dp0_build"

set sources=
dir /s /b "%repo%\java-solutions\%tests%" | findstr /i .java > "compile-homework.tmp"
for /f %%a in (compile-homework.tmp) do (
    call set "sources=%%sources%% %%a"
)
del "compile-homework.tmp"

echo javac -d "%~dp0_build" -cp "%~dp0;%repo%\java-solutions" %~dp0%tests%\*.java %sources% || exit /b 1
     javac -d "%~dp0_build" -cp "%~dp0;%repo%\java-solutions" %~dp0%tests%\*.java %sources% || exit /b 1

echo SUCCCESS
exit /b 0

:check_dir
if not exist "%~1" (
    echo Directory "%~1" does not exists
    exit /b 1
)
exit /b 0