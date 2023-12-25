Lirou Store - API Documentation
Bem-vindo ao repositório da API para a Lirou Store, uma loja de Lupas online localizada em Jaboatão dos Guararapes - PE.

Objetivo
O objetivo desta API é fornecer os dados necessários para o funcionamento do frontend da nossa loja. Ela permite que os usuários visualizem os produtos disponíveis, façam compras e gerencie seus pedidos.

Endpoints
Aqui estão alguns dos principais endpoints disponíveis na nossa API:

GET /api/get-all-glasses: retorna uma lista de todos os óculos da loja, para a parte admin gerir os óculos.
POST /api/post-glasses: salva um novo óculos no banco de dados. 
PUT /api/put-glasses: edita um óculos já existente.
DELETE /api/delete-glasses/{identifier}: exclui um determinado óculos da base de dados. 

Frontend
O frontend que consome essa API está hospedado em https://liroustore.vercel.app/. 
Ele foi desenvolvido utilizando Angular, e seu repositório é o: https://github.com/pamedsi/gui-lirou-store 