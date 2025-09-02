all: class_server class_client

class_server: class_server.c
	gcc -Wall -Wextra class_server.c -o class_server -pthread

class_client: class_client.c
	gcc -Wall -Wextra class_client.c -o class_client 

clean:
	rm -f class_server
	rm -f class_client