@echo off
echo A terminar todos os componentes Googol (Gateway, Queue, Downloader, Client)...

:: Mata todos os processos Java com a palavra "search." nos pacotes
for /f "tokens=2 delims= " %%i in ('jps -l ^| find "search."') do taskkill /PID %%i /F

echo Todos os processos foram terminados (se existiam).
