Manual de instruções

Como compilar:

Basta executar o ficheiro compile.sh com ./compile.sh
Como executar:

Para o ficheiro Downloader:
cd target
java -cp ".:lib/jsoup-1.18.3.jar" search.Downloader

Para o resto dos ficheiros:

java -cp target search."file"
PS: O primeiro ficheiro a ser executado é sempre o Gateway

No final temos um ficheiro stop_all.sh com ./stop_all.sh que termina todos os processos.
