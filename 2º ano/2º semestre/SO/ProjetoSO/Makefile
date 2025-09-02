all: mobileUser systemManager backOfficeUser

mobileUser: mobileUser.c
	gcc -Wall -Wextra -o mobileUser mobileUser.c -pthread

systemManager: systemManager.c
	gcc -Wall -Wextra -o systemManager systemManager.c -pthread

backOfficeUser: backOfficeUser.c
	gcc -Wall -Wextra -o backOfficeUser backOfficeUser.c -pthread

clean:
	rm -f mobileUser systemManager backOfficeUser
