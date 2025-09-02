# Relatório do Projeto: Googol - Motor de Pesquisa de Páginas Web

## 1. Objetivos do Projeto

O objetivo do projeto é desenvolver um motor de pesquisa distribuído para páginas web, que se assemelha aos serviços de pesquisa de grandes motores. O sistema deverá permitir a indexação automática de URLs, execução de pesquisas com base em palavras e fornecimento de dados relevantes, como o URL, título e uma citação de texto de cada página. A arquitetura será baseada em sistemas distribuídos, utilizando comunicação via RPC/RMI e multicast fiável para garantir a consistência dos dados entre os servidores.

## 2. Arquitetura do Sistema

A arquitetura do Googol é composta por quatro componentes principais:

- **Downloaders**: São responsáveis por obter as páginas web, processá-las e enviar os dados para os **Barrels**. Eles trabalham em paralelo para melhorar o desempenho e utilizam uma fila de URLs.
  
- **Barrels**: Servem como armazenamento do índice invertido e informações associadas. Eles recebem as atualizações dos **Downloaders** e respondem a consultas realizadas pela **Gateway**.

- **Gateway**: É o ponto de entrada para os clientes e comunica-se com os **Barrels** através de RMI (Remote Method Invocation). A **Gateway** escolhe qual Barrel consultar para realizar a busca.

- **Clientes (Client)**: Interagem com a **Gateway** para realizar pesquisas, adicionar URLs ao índice e consultar backlinks ou estatísticas.

A comunicação entre os componentes é gerida por **RMI** (para chamadas remotas) e **multicast fiável**.

## 3. Funcionalidades Implementadas

O sistema tem as seguintes funcionalidades:

- **Indexação de URLs**: O cliente pode adicionar URLs para indexação, que são processados pelos **Downloaders** e armazenados nos **Barrels**.
- **Pesquisa de Termos**: O cliente pode pesquisar termos.
- **Backlinks**: É possível consultar as páginas que apontam para uma URL específica.
- **Estatísticas**: O sistema fornece informações como o número de páginas indexadas, palavras no índice, backlinks registrados e as 10 pesquisas mais comuns.
  
## 4. Componentes do Sistema

- **Client.java**: Implementa a interface do cliente, permitindo que o usuário interaja com o sistema e faça consultas, adicione URLs e consulte estatísticas&#8203;:contentReference[oaicite:0]{index=0}.
  
- **BarrelInterface.java e Barrel.java**: O **BarrelInterface** define os métodos remotos que os **Barrels** devem implementar. O **Barrel** armazena os índices invertidos e responde a consultas de pesquisa e backlinks&#8203;:contentReference[oaicite:1]{index=1}&#8203;:contentReference[oaicite:2]{index=2}.

- **Gateway.java**: A **Gateway** implementa a interface que interage com os **Barrels** e aceita solicitações de clientes para pesquisa e adição de URLs.&#8203;:contentReference[oaicite:3]{index=3}.

- **URLQueue.java e URLQueueInterface.java**: O **URLQueue** armazena as URLs que os **Downloaders** devem visitar, garantindo que o sistema seja escalável e eficiente&#8203;:contentReference[oaicite:4]{index=4}&#8203;:contentReference[oaicite:5]{index=5}.

- **ReliableMulticastService.java e ReliableMulticastServiceImpl.java**: Define e implementa o serviço multicast fiável, que garante a distribuição de dados entre os **Barrels**&#8203;:contentReference[oaicite:6]{index=6}&#8203;:contentReference[oaicite:7]{index=7}.

- **Downloader.java**: O **Downloader** é responsável por buscar as páginas da web, processá-las e enviar as informações para os **Barrels**. Ele também garante a descoberta de novas URLs a serem indexadas&#8203;:contentReference[oaicite:8]{index=8}.


## 5. Conclusão

O sistema Googol foi projetado para fornecer um motor de pesquisa distribuído eficiente e confiável, com redundância e failover implementados para garantir alta disponibilidade. A arquitetura baseada em **RPC/RMI** e **multicast fiável** assegura a consistência dos dados, enquanto a estrutura distribuída melhora o desempenho ao permitir que múltiplos **Downloaders** e **Barrels** operem em paralelo. O projeto também aborda a necessidade de garantir que o sistema permaneça funcional mesmo em caso de falhas de componentes.

