# Splay Tree Analysis 

##  Visão Geral do Projeto

O objetivo deste projeto é analisar a eficiência e a utilidade da Splay Tree sob diferentes fatores de carga e padrões de acesso, identificando os cenários em que seu mecanismo de autoajuste oferece vantagens reais em relação a estruturas balanceadas tradicionais, além de conter um material didático sobre a estrutura e simular aplicações reais da mesma.

---

### Visão Geral do Repositório

Esse repositório contém um material didático sobre Splay Trees, sua implementação e a implementação de outras estruturas de dados auxiliares/comparativas, experimentos de análise de sua eficácia e comparação, e outros experimentos adicionais (Com bases de dados reais, por exemplo.)

---

## Estrutura do Repositório

```bash
.
├── experimentreports              # Diretório com experimento e análise
│   └── graficos                   # Gráficos gerados no experimento
├── materialdidatico               # Material didático de splaytrees na estrutura de EDA-UFCG
└── src
    └── main
        ├── java
        │   ├── Bench              # Testes de benchmarks 
        │   ├── DataStructures     # Implementações das estruturas de dados utilizadas
        │   ├── IO                 # Auxílio para benchmarks
        │   └── simulation         # Simulação de aplicação real da splaytree com BD real
        └── resources              # Banco de dados real de livros
``` 
---

### 📊 [Relatório de Experimentos](experimentreports/experimentreports.md)

Relatório completo da análise de desempenho da Splay Tree em comparação a outras estruturas (BST, AVL, Red-Black). Inclui a metodologia dos testes, análise de gráficos de busca/inserção, cenários de Hot Search e ameaças à validade.

### 📚 [Material Didático](materialdidatico/materialdidatico.md)

Material didático focado no funcionamento da Splay Tree, na estrutura e padrão da disciplina de Estruturas de Dados e Algoritmos (EDA-UFCG). Abrange conceitos fundamentais, propriedades, principais funções, e exemplos com implementações mais simples para entendimento de alunos (utilização de int em vez de Object).

###  [Implementações das Estruturas](DataStructures/)

Código em Java da implementação de todas as estruturas de dados analisadas no projeto. Implementações da Splay Tree e das estruturas comparativas (BST, AVL, Red-Black), servindo como base para os benchmarks.

###  [Benchmarks](Bench/)

Estrutura de benchmarks desenvolvida para mensurar o tempo de execução e uso de memória das operações sob diferentes cenários de carga, distribuições de dados e frequências de acesso.

###  [Simulação com Dados Reais](simulation/)

Mapeia um cenário prático no qual a Splay Tree é aplicada a uma base de dados real de livros, demonstrando o ganho de eficiência da propriedade do autoajuste quando há alta localidade de referência (Hot Search) em consultas de sistemas reais.


## Autores

* [**Kauã José**](https://github.com/Kaua-Jose153)
* [**Roan Motta**](https://github.com/roanmotta)
* [**Jhonnata Mikael**](https://github.com/Jhonnata011)
* [**André Mikael**](https://github.com/andremikaelpc)