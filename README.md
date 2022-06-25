# Controle de Servidor Público API

Este projeto usa Quarkus, o Supersonic Subatomic Java Framework.\
Se você quiser saber mais sobre o Quarkus, visite o site : https://quarkus.io/ .

## Executando o aplicativo
##### Pré-requisitos
1. **Java 11**

Você pode executar seu aplicativo no modo dev que permite a codificação ao vivo usando:
```shell
./mvnw compile quarkus:dev
```

> **_NOTA:_**  O Quarkus vem com uma Dev UI, que está disponível no modo dev apenas em http://localhost:8080/q/dev/.

## Executando testes
##### Pré-requisitos
1. **Java 11**

Você pode o comando executar todos os testes automatizados rodando o comando abaixo na raiz do projeto

```shell
./mvnw test
```

**Principais endpoints**\
/autenticacao/login\
/autenticacao/refresh\
/servidores/efetivo\
/servidores/temporario\
/unidades\
/lotacoes\
/pessoas/{pessoaId}/fotos

### Ambiente utilizado para desenvolvimento
IntelliJ IDEA 2022.1.1 (Community Edition)\
Build #IC-221.5591.52, built on May 10, 2022\
Runtime version: 11.0.14.1+1-b2043.45 amd64\
VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.\
Linux 5.13.0-48-generic\
GC: G1 Young Generation, G1 Old Generation\
OpenJDK 11.0.14.1
