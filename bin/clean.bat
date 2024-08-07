@echo off
echo.
echo targetをクリア
echo.

%~d0
cd %~dp0

cd ..
call mvn clean

pause