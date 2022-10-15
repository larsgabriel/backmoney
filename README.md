Algamoney-API-BACK

Deploy no Heroku

- CRIAR A CONTA NO HEROKU.COM
- APÓS ISSO ABRIR O TERMINAL NA PASTA RAIZ DO PROJETO (ONDE TEM O POM)
- FAZER LOGIN VIA TERMINAL NO HEROKU
	heroku login
- SERA ABERTA UMA PAGINA WEB PARA QUE VC FAÇA O LOGIN.
- ESTANDO LOGADO CRIE O NOME DA SUA APLICAÇÃO CASO NÃO EXISTA:
	 exemplo: heroku create backmoney-lars
- AGORA É NECESSÁRIO ADICIONAR PLUGINS PARA CRIAR UM BANCO SQL PARA A APLICAÇÃO
	heroku addons:create jawsdb:kitefin
- NA RESPOSTA VIRA ALGO ASSIM:
	Use heroku addons:info jawsdb-solid-11539 to check creation progress
- USE O COMANDO DADO PARA VER AS INFORMAÇÕES:
	heroku addons:info jawsdb-solid-11539
- SERÁ RETORNADO ALGO ASSIM:
	=== jawsdb-solid-11539
Attachments:  backmoney-lars::JAWSDB
Installed at: Sat Oct 08 2022 14:02:15 GMT-0300 (GMT-03:00)
Owning app:   backmoney-lars
Plan:         jawsdb:kitefin
Price:        free
State:        created
- PARA VERIFICAR INFO DO BANCO CRIADO USE O COMANDO:
	heroku confing:get JAWSDB_URL
	SERÁ EXIBIDO ALGO ASSIM:
	Warning: confing:get is not a heroku command.
	Did you mean config:get? [y/n]: y
	mysql://ahg3ct4v19nc3i80:o4c7h2clruftz7qk@cwe1u6tjijexv3r6.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/fhobm5dzjrvo8ig9
	
- APÓS ISSO SERÁ NECESSÁRIO CONFIGURAR A URL DO BANCO EXTERNO NA APLICAÇÃO
	No arquivo application-prod.properties estão configuradas variaveis que serão criadas no heroku via linha de comando:
	Este ira criar o valor e a variavel JDBC_DATABASE_URL, o valor é o final da string retornada anteriormente até o @
	heroku config:set JDBC_DATABASE_URL=jdbc:mysql://cwe1u6tjijexv3r6.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/fhobm5dzjrvo8ig9
	USERNAME É O COMEÇO DA URL CIMA ATÉ O DOIS PONTOS
	heroku config:set JDBC_DATABASE_USERNAME=ahg3ct4v19nc3i80
	PASSWORD É DEPOIS DO DOIS PONTOS ATÉ O @
	heroku config:set JDBC_DATABASE_PASSWORD=o4c7h2clruftz7qk
	
- PARA CONFERIR SE FOI CRIADO CORRETAMENTE 
	heroku config
- CRIAR UM ARQUIVO NA RAIZ DO PROJETO CHAMADO Procfile
	
	
	

	