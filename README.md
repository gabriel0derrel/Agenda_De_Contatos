# Lista de Contatos em Java

Este repositório contém um projeto de lista de contatos desenvolvido em Java usando o IDE NetBeans. O objetivo deste projeto é criar uma aplicação simples para gerenciar contatos, permitindo adicionar, editar, remover e visualizar informações de contato.

## Organização do Projeto

- Persistencia:
  - ContatoDao: Responsável pela persistência dos dados.
- Controle: Responsável pela comunicação entre a tela e a persistencia.
- Modelos Principais:
  - Contato
  - Icrud (Interface de Comunicação)
- Modelos Secundários:
  - Telefone
  - Endereco

## Funcionalidades

- Adicionar novo contato com nome, telefone, e-mail e endereço.
- Editar informações de um contato existente.
- Remover contato da lista.
- Visualizar lista de contatos cadastrados.
- Persistência de dados utilizando arquivo.

## Tecnologias Utilizadas

- Linguagem de Programação: Java
- IDE: NetBeans
- Interface Gráfica: Swing (Javax.swing)
- Persistência de Dados: Arquivo Texto(csv) e SGBD(PostgreSQL)

## Autor

- [Gabriel Derrel](https://github.com/gabriel0derrel)
- [Lucas Pereira Nunes](https://github.com/Prizrak2)

## Atenção!
- Para executar escolha a versão do projeto e execute o arquivo (.jar) que há dentro da pasta "dist" do respectivo projeto, é necessário ter o "JAVA SE Development Kit" para que seja executado com sucesso, também pode ser aberto pelo "NETBEANS IDE"

- Imagens não serão mostradas se executado da primeira maneira, caso queira ver o projeto completo é necessário o uso do "NETBEANS"

- Caso queira testar o projeto com o SGBD "PostgreeSQL" é necessário abrir um servidor de banco de dados no "pgAdmin" e alterar as informações do servidor no arquivo "ContatoDao"
