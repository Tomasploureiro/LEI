@echo off
rmdir /s /q target\search
mkdir target\search

javac -d target -cp target\lib\jsoup-1.18.3.jar src\main\java\search\*.java
