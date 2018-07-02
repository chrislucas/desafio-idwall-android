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

Para facilitar o desenvolvimento desse app foram utilizadas algumas bibliotecas foram utilizadas, a seguir:

- Retrofit (http://square.github.io/retrofit/)
	- Para facilitar a implementação de Requests HTTP fora da Main Thread sem ter a 
	preocupação de criar uma Thread separada. Além disso a library disponibiliza um bom design 
	para realizar requests síncronos/assíncronos, com callbacks de 
	retorno e um recurso extremamente interessante para manipular/enviar objetos no REQUEST e RESPONSE.
	
- Picasso (http://square.github.io/picasso/)
	- Para facilitar a implementação de download, carregamento e cache de imagens proveniente de uma URL ou URI. 
	Implementação de cache, otimização, operações de transformação em 
	bitmap não são triviais, sendo justificável o uso de uma library bem implementada e documentada.
	
	
Para realizar a tarefa de manter os dados do usuário que realizou o login, foi utilizado como banco de dados o SQLite 
bem como as classes do Android que auxiliam no acesso ao banco e as tabelas criadas para o App.

Para executar o aplicativo em seu celular sem a necessidade de compilar o projeto desse repositório foi criado um arquivo
executável (APK). Ele se encontra na pasta apk na raiz desse repositório. Baixe-o e adicione-o a uma pasta no seu celular com
S.O android, procure-o no aplicativo de gerenciamento de arquivos do seu dispositivo e instale-o.
