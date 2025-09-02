cmake_minimum_required(VERSION 3.28)
project(projetoSOv2 C)

set(CMAKE_C_STANDARD 11)

add_executable(projetoSOv2
        systemManager.c
        mobileUser.c
        backofficeUser.c)
