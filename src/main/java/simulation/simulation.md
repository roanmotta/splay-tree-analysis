# Simulação do Caso Ideal da Splay Tree
---

## 1. Objetivo

O objetivo desta simulação, é **demonstrar o comportamento do caso ideal da Splay Tree**, que consiste em uma consulta frequente de um pequeno conjunto de chaves em um grande volume de dados. Utilizando o arquivo livros.csv, a simulação demonstra como o mecanismo de *splaying* reduz o tempo de execução e otimiza o acesso.

---

## 2. Metodologia

### 2.1 Linguagem Utilizada

A simulação foi implementada inteiramente em **Java**.  

### 2.2 Uso da Distribuição de Pareto para o Caso Ideal

Para simular o "caso ideal" da Splay Tree, foi necessário gerar requisições de consulta que simulam o comportamento do mundo real (grande quantidade de buscas de um pequeno conjunto de livros), nos quais o acesso aos dados não é uniforme, alguns dados são bem mais procurados que outros.

Assim utilizou-se a **Distribuição de Pareto** com o intuito de concentrar 80% das buscas nos 10 livros mais requisitados, sendo eles os 10 primeiros livros do arquivo livros.csv, e os outros 20% o restante dos livros.

### 2.3 Lógica da simulação

O arquivo **SimulationMain.java** utiliza as ferramentas presentes na pasta **utils**, gerando uma pasta **results** com um arquivo com o tempo de execução usando AVL, RedBlackTree e SplayTree nas buscas, para demonstrar que com grande quantidade de buscas a SplayTree se sai melhor.

A pasta util lê os dados do arquivo livros.csv, utiliza a lógica de pareto, analisa o tempo de execução de buscas com AVL, RedBlackTree e SplayTree, além de criar um arquivo com os resultados dos tempos das buscas.

---

## 3. Estrutura de simulation 

```bash
src/main/java/simulation/           # Pacote principal da simulação
    ├── simulation.md               # Documentação da pasta simulation
    ├── SimulationMain.java         # Main que executa a simulação
    │
    ├── utils/                      # Classes utilitárias da simulação
    │   ├── CSVReader.java          # lê o arquivo livros.csv
    │   ├── CSVWriter.java          # Responsável por exportar os resultados da simulação
    │   ├── Livro.java              # Armazena os dados de livros.csv
    │   ├── ParetoGenerator.java    # Faz a lógica do princípio de pareto
    │   └── TreeTimer.java          # Responsável por cronometrar a execução
    │
    └── results/                    # pasta criada automaticamente **após** rodar a simulação
        └── results.csv             # Arquivo com as métricas de tempo geradas pelo CSVWriter
```
---

## 4. Como Rodar a Simulação

### 4.1 Pré-requisitos
* **Java Development Kit(JDK):** Versão 11 ou superior instalada.

### 4.2 Passo a Passo de Execução

Na raiz do projeto, compile e execute a simulação com os seguintes comandos:
1. **Compilar os Arquivos Java:**
   ```bash
   javac -sourcepath src/main/java src/main/java/simulation/SimulationMain.java
   ```

2. **Executar a Simulação:**
   ```bash
   java -cp src/main/java simulation.SimulationMain
   ```

---
