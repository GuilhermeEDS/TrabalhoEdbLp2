# TrabalhoEdbLp2

Projeto final das disciplinas de <strong>Estrutura de Dados Básicos II</strong> e <strong>Linguagem de Programação II</strong>.

Grupo:
<ul>
  <li>Deyvid William Silva de Medeiros</li>
  <li>Guilherme Euller Dantas Silva</li>
  <li>Nathãn Vitor de Lima</li>
</ul>
  
Link para visualizar o relatório do Projeto: [Visualizar Relatorio](relatorio-final.pdf)

## Instruções

É necessário ter `java` e `make` instalado.

Para instalar o Java: `sudo apt install openjdk-18-jdk`

Para instalar o make: `sudo apt install make`

Após clonar o projeto, é possível compilar usando o comando

```shell
cd src && make
```

Após executar o comando acima será gerado um jar, e para executar utilize o comando:

```shell
java -jar ArvoreBuscaBinaria.jar <arquivo_valores> <tipo_de_execucao>
```

Onde `arquivo_valores` é o caminho para o arquivo contendo os valores a serem lidos; e `tipo_de_execucao` é qual o tipo de execução deverá ser feito, podendo ser `--simples` e `--complexo`.

O projeto possui alguns arquivos de teste em um de seus diretório (`/src/data`). Sendo necessário você informar na hora da execução qual arquivo deseja testar, por exemplo, a partir da pasta src



