# splay-tree-analysis

Esse repositório contém a experimentação feita sobre a Splay Tree, sua implementação, sua comparação com a eficácia de outras árvores de busca binária e outros experimentos adicionais (Com bases de dados reais, por exemplo.)

## Objetivo
O objetivo deste projeto é analisar o funcionamento e a utilidade da Splay Tree, avaliando a eficiência de suas operações sob diferentes fatores de cargas e distribuições de dados. Buscando identificar cenários em que sua utilização se torna eficiente e vantajosa em relação a outras árvores binárias de pesquisa balanceadas.

## Metodologia

Nesse sentido, a execução dos experimentos se baseou em 3 etapas:
* 1.Implementação das estruturas que serão comparadas.
* 2.Geração das cargas de testes.
* 3.Análise de desempenho das estruturas quando as cargas são aplicadas.

1.Implementação das estruturas de dados:
<br>
Para possibilitar uma comparação da Splay Tree com outras árvores de pesquisa, foram implementadas as seguintes estruturas: AVL Tree, Red-Black Tree, BST (Binary Search Tree) básica; Splay Tree.
Todas as estruturas implementam a mesma interface de operações, permitindo que sejam submetidas aos mesmos experimentos e condições de teste.

2.Geração das cargas de testes:
<br>
As cargas de testes utilizadas foram geradas automaticamente e adicionadas no diretório DataSets. Foram considerados tamanhos de entrada variando de 10² até 10⁶ elementos.

Além disso, foram gerados conjuntos de dados contendo:

números aleatórios;
números em ordem crescente;
números em ordem decrescente.

3.Análise de desempenho das estruturas quando as cargas são aplicadas.
<br>
A análise de desempenho foi realizada por meio da medição dos tempos de execução das operações das estruturas para cada conjunto de dados.
Inicialmente, foi desenvolvido um benchmark próprio para automatizar os testes, mas, posteriormente, os experimentos passaram a utilizar o JMH (Java Microbenchmark Harness), proporcionando medições mais confiáveis.
Além do uso do JMH, foram adotadas estratégias para reduzir interferências externas durante as medições, como a realização de sequências de aquecimento (warmup), para minimizar o impacto da lentidão das execuções iniciais. Foram realizadas 100 execuções de cada experimento, descartando as 25 primeiras para o warmup, possibilitando o cálculo do tempo médio de execução e da margem de erro estatística.

## Resultados

Nos testes de busca convencional, a AVL Tree e a Red-Black Tree tiveram resultados similares, enquanto a Splay Tree permaneceu acima, levando mais tempo, o que era o esperado, devido ao método splay, chamado ao final de cada busca, que, nesse cenário, não gera benefícios constantes para a Splay Tree, principalmente em entradas de ordem menor.

Nos testes de inserção, a Splay Tree continuou mais custosa que a AVL Tree e a Red-Black Tree quando os dados estavam organizados aleatoriamente, entretanto, para entrada de dados organizados, tanto em ordem crescente quanto decrescente, obteve-se uma maior eficiência da Splay Tree. 

A Splay Tree apresentou um desempenho ainda maior nos experimentos de Search Hot, nos quais um conjunto de 10 elementos é acessado repetidamente. Nesses casos, após as primeiras buscas, as rotações realizadas pela Splay Tree aproximaram os elementos mais acessados da raiz, reduzindo o custo amortizado das seguintes operações. Entretanto, é importante destacar que, para conjuntos de dados menores, o custo adicional das rotações podia superar os benefícios do autoajuste da estrutura.
Dessa forma, nos cenários em que há forte localidade de referência, à medida que o tamanho da entrada aumenta, a vantagem da Splay Tree se torna mais evidente.

## Ameaças à validade
* 1.Benchmarks dependem fortemente de hardware, SO, JVM e configurações da máquina virtual, então, mesmo com uso do JMH, os resultados podem variar se replicados em diferentes máquinas.
* 2.Os experimentos utilizaram apenas três distribuições de dados (aleatória, crescente e decrescente) e um cenário específico de buscas repetidas (Hot Search). Em aplicações reais, outros padrões de acesso podem produzir resultados diferentes. 

## Conclusões Finais
A análise realizada permitiu compreender o funcionamento da Splay Tree e comparar seu desempenho com outras árvores binárias de pesquisa. Os resultados mostraram que a principal vantagem da Splay Tree está em sua capacidade de reorganizar automaticamente a árvore conforme o padrão de acesso, aproximando os elementos mais acessados para próximo da raiz. Isso permite que o custo amortizado seja reduzido em aplicações com forte localidade de referência.
Por outro lado, para situações em que os acessos são distribuídos de maneira uniforme entre todos os elementos, a Splay Tree não apresenta grandes vantagens, muitas vezes sendo até mesmo pior que outras árvores, tendo em vista que ela sempre reorganiza a árvore.
Dessa forma, conclui-se que a Splay Tree não substitui necessariamente as árvores balanceadas tradicionais, mas é uma alternativa interessante para aplicações em que determinados elementos são acessados repetitivamente. Nesses casos, seu mecanismo de auto ajuste pode proporcionar ganhos de desempenho.

