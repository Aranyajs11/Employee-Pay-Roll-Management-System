@echo off
setlocal
cd /d "%~dp0"

if not exist out mkdir out

javac -d out -sourcepath src src\com\college\app\Main.java
if errorlevel 1 (
    echo Compilation failed. Make sure a JDK is installed and javac is on PATH.
    exit /b 1
)

java -cp out com.college.app.Main
