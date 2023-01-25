# Projeto de Busca de Arquivos
Este projeto é uma aplicação de linha de comando (CLI) escrita em Java 11 que permite buscar arquivos que contenham palavras específicas. Ele utiliza streams paralelos para melhorar o desempenho das operações de busca.

## Problema
Um programa que busque por uma sentença em todos estes arquivos e exiba quantos e quais arquivos possuem esta palavra chave. 
A ordenação dos arquivos deve ser feito em ordem crescente e alfabética. 
O programa deve ser executado a partir da linha de comando do terminal (Linux/MacOS) e 
deve permitir que a sentença que deverá ser encontrada seja passada como parâmetro de execução.

## Requisitos

- O código coberto por testes unitários e deve utilizar somente ferramentas nativas da linguagem que você escolher no desenvolvimento do algoritmo (Node.js, Java ou outra de sua preferência).
- É desejável que o código esteja versionado, preferencialmente usando GIT. Para os testes você pode utilizar frameworks não nativos
- Projeto deve ter um arquivo README.md que contenha as instruções de instalação, execução de testes e execução do programa.
- Lembre-se que as palavras digitadas em um sistema de busca não necessariamente estão na mesma ordem em que elas estão salvas nos arquivos.
- O "match" das palavras deve utilizar o critério "AND". Ou seja, ambas devem existir no arquivo, independente da ordem ou das outras palavras separarem múltiplos termos.
- O processo de busca deve ser executado em até 1 ms.
- Pode pré-processar e preparar a massa de arquivos em um outro script

## 1. Escolhas Arquiteturais
> - [Java 11](https://www.oracle.com/br/java/technologies/javase/jdk11-archive-downloads.html)
> - [Maven v3.8.7](https://maven.apache.org/)
> - [Mockito v3.6.0]((https://site.mockito.org/))
> - [Maven v3.8.7]((https://site.mockito.org/))
## 2. Como usar

Instale as dependencias executando o comando:
```
mvn clean install
```
Abra o prompt de comando e navegue até o local onde está o arquivo search-1.0-SNAPSHOT.jar.

O diretorio *data/* deve ser colocado o no mesmo diretorio que o .jar criado. 

Execute o seguinte comando:
```
java -jar search-1.0-SNAPSHOT.jar palavra1 palavra2 ...
```

Onde palavra1, palavra2, etc. são as palavras que você deseja buscar.

Exemplo:

```
java -jar search-1.0-SNAPSHOT.jar disney walt
```
Resultado:

O programa vai retornar uma lista de arquivos que contenham todas as palavras especificadas.

```
53 [data\a-cowboy-needs-a-horse.txt, data\alice-and-the-three-bears.txt, ...]
588 ms
```

## 3. Execução de testes
Para executar testes unitários:

```
mvn test
```

## 4. Considerações

- Este ReadMe e demais comentários foram adicionados em português para faciltiar o entendimento.
- É recomendado que o programa seja não seja utilizado com arquivos volumosos, pois a massa alvo do projeto não continha arquivos maiores que 2kb.
- Foi mantido o *SearchExecutorService* no projeto pois se mostrou uma abordagem interessante e ficou muito próximo ao *SearchParallelStreamService* escolhido.
Quero guardar para futuras consultas porque aprendi bastante com o experimento.
- O requisito de performace de 1ms não foi alcançado, 
mesmo experimentando diversas abordagens (Scanner, ExecutorService, ParallelStreams entre outros),
o ponto de atenção fica aos recursos de maquina local e também dada as limitações de utilizar somente as soluções nativas do Java 11.
Talvez exista outra linguagem que consiga este nível de performance de maneira nativa.
- O Pré processamento não foi cogitado porque acredito que poderia impossibilitar a execução em Linux/iOS,
dado que o desenvolvimento foi realizado em maquina pessoal Windows.