Lirou Store - API Documentation

Bem-vindo ao repositório da API para a Lirou Store, uma loja de Lupas online localizada em Jaboatão dos Guararapes - PE.

Objetivo

O objetivo desta API é fornecer os dados necessários para o funcionamento do frontend da nossa loja. Ela permite que os usuários visualizem os produtos disponíveis, façam compras e gerencie seus pedidos.

Endpoints

Aqui estão alguns dos principais endpoints disponíveis na nossa API:

Para os óculos:

  GET /api/glasses: retorna uma lista de todos os óculos da loja, para a parte admin gerir os óculos.
  
  POST /api/glasses: salva um novo óculos no banco de dados. 
  
  PUT /api/glasses/{identifier}: edita um óculos já existente.
  
  DELETE /api/glasses/{identifier}: exclui um determinado óculos da base de dados.

  PATCH /api/set-available/{identifier}: atualiza óculos para "disponível" ou "indisponível"

Para frete:

  GET /api/shipping/calculate: retorna os preços do frete entre o CEP do cliente e o da loja.

  POST /api/shipping/send-to-superfrete: envia para o SuperFrete as informações de envio para gerar etiqueta.  


Frontend

O frontend que consome essa API está hospedado em https://liroustore.vercel.app/. 
Ele foi desenvolvido utilizando Angular, e seu repositório é o: https://github.com/pamedsi/gui-lirou-store 
