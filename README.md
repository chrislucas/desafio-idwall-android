# desafio-idwall-android
Repositório com o projeto da ID Wall

# Projeto criado para o teste de desenvolvimento mobile para ID Wall.

Para o desenvolvimento desse App foi planejado a construção de uma arquitetura MVP, 
para uma boa separação de responsabilidades. Na camada de Modelo que representam as entidades do domínio do problema proposto, 
na camada de visualização temos as classes responsáveis por exibir componentes gráficos para o usuário 
como a tela de autenticação e a tela que lista os Feeds, e na camada de apresentação temos as classes responsáveis 
por recuperar os dados que queremos mostrar para o usuário, 
construir os objetos a partir dos modelos propostos e enviá-los para camada de visualização.

Essa arquitetura foi escolhida para separar bem as responsabilidades de cada componente que forma esse app, 
facilitando o desenvolvimento, teste e correções de bugs, e ainda pela maior 
familiaridade de quem o fez com tal arquitetura do que as demais que podemos encontrar no universo da engenharia de software.