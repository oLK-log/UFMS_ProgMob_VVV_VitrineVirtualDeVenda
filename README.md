# UFMS_ProgMob_VVV_VitrineVirtualdeVenda
Este repositório concentra as entregas das atividades avaliativas realizadas ao longo da disciplina de Programação Mobile. O objetivo principal é a construção de um aplicativo no Android Studio com linguagem de programação Java aplicando os conceitos apreendidos ao longo das aulas.

* **Faculdade:** UFMS
* **Curso:** Engenharia de Software
* **Ferramentas:**
  - Android Studio

## Ideia Inicial:
### Vitrine Virtual de Joias
A ideia do Aplicativo Vitrine Virtual de Joias é um protótipo funcional de um e-commerce voltado para o mercado de joalheria. Projetado com uma interface limpa e navegação intuitiva, o aplicativo simula a experiência de um catalogo digital interativo para os clientes da loja. A aplicação é formada, a priori, por três telas principais - Fragmentos - gerenciadas por uma barra de navegação inferior(Bottom Navigation). Arquiteturalmente, a interface e as funcionalidades estão divididas em:
* Abas de Destaques: A tela inicial do aplicativo, que abriga um menu suspenso interativo(Spinner) estruturado a partir de dados em XML, permitindo ao cliente filtrar o catálogo por materiais especificos.
* Aba de Categorias: Uma seção de navegação organizada em um formato de lista clássica(ListView), que mapeia os principais setores da loja.
* Aba de Galeria: A vitrine visual interativa construida com uma grade personalizada(Grid View), alimentada por um Adapter em Java. Ela exibe as imagens e informações das Jóias lado a lado e fornece feedback imediato ao usuário através de mensagens flutuantes(Toasts) e efeitos sonoros(MediaPlayer) durante as interações.

 
### Entrega 1:
#### Requisitos:
* Utilização de Basic Activity ou Bottom Navigation Activity;
* Projeto com pelo menos 3 Fragmentos;
* Ter no mínimo: 1 Spinner, 1 ListView, 1 GridView, Sons, Cores Imagens;
* Ações sobre os itens do Spinner, do List e do Grid;
* strings e strings-arry devem estar no arquivo strings.xml.
<div align="center">
  <img src="./img/entrega1_telaDestaques.png" alt="imagem tela Destaques" width="250"/>
  <img src="./img/entrega1_telaCategorias.png" alt="imagem tela Categorias" width="250"/>
  <img src="./img/entrega1_telaGaleria.png" alt="imagem tela Galeria" width="250"/>
</div>

## Evolução:
Nesta etapa, o projeto deixou de ser uma vitrine estática e passou a contar com persistência de dados local, lógica de autenticação e diferentes perfis de usuário (Visitante, Cliente e Lojista), mudando o foco para o cadastro e venda de produtos em geral. Nesse contexto, foi alterado o nome do aplicativo para "VVV - Vitrine Virtual de Venda".
### Ideia:
A VVV - Vitrine Virtual de Venda é uma aplicação Android focada em facilitar a conexão entre pequenos Empreendimentos e o Cliente. Ela não processa pagamentos reais, mas atua como um gerador de pedidos e gerenciador de produtos. Nela, existe dois principais usuários - O Lojista e o Cliente - que serão detalhados mais abaixo:
#### Usuario
O Usuário padrão pode fazer Login, selecionar seu tipo de Usuario(Lojista ou Cliente) e recuperar a senha por meio de envio de e-mail de recuperação.
#### Lojista
O Lojista é quem possui a loja e tem interesse em mostrar seus produtos por meio da Vitrine Virtual. Dentre suas funcionalidades, temos:
* **Gerenciar Produto**: Fazer o CRUD dos Produtos, assim como adicionar, além das informações padrão, as imagem e vídeos. Somado à isso, ele pode definir um produto como "Destaque" para fazê-lo aparecer na aba de destaques para o Cliente.
* **Acompanhar Dados Loja**: O Lojista pode verificar a quantidade de Produtos cadastrados, o Faturamente, as Visitas em sua Loja. Ele também pode verificar os históricos de Pedidos realizados e o detalhes desses Pedidos (data, produtos, cliente).
* **Configurar seu Perfil**: O  Lojista pode além de Visualizar suas informações(foto perfil, tipo de perfil), também definir o endereço da sua loja e até 3 cores para alinhar sua Vitrine à identidade visual de seu negócio(Principal, Secundária e de Fundo).
#### Cliente
O Cliente é quem tem interesse em ver os Produtos disponíveis em determinada Loja. Dado isso, ele no VVV não precisa estar logado para visualizar os Produtos ou fazer um pedido, ele pode entrar como Visitante. Mas se preferir, também pode criar uma conta para ter acesso à todas as funconalidades.
* **Acessar Vitrine**: Escolher acessar determinada Vitrine como Visitante ou fazendo Login. 
* **Visualizar Produto**: O Cliente pode visualizar os Produtos, ver seus detalhes ao clicar, visualizar o vídeo curto do produto(se disponível), assim como fazer a busca de determinado Produto pelo nome. Àdido à isso, ele também pode verificar os Produtos em Destaque.
* **Gerenciar Pedido**: Enquanto aberto um Pedido, o Cliente pode fazer a inclusão e remoção dos Produtos em seu Pedido. Por fim, ele pode Finalizar o Pedido.
* **Visualizar Perfil e Histórico de Pedido**: O Cliente em sua aba Perfil pode além de visualizar suas informações(Foto de Perfil, Nome e tipo de usuário), consultar seu Histórico de Pedidos e os detalhes de um Pedido específico.
* **Visualizar Endereço**: É possível o Cliente vizualizar por meio de mapa(Google Maps- API) no aplicativo a localização da Loja.
#### Organização do Projeto
Decidi organizar os pacotes do projeto a partir dos conceitos do sistema. A organização é a seguinte:
* **core**: Nesse pacote estão contidos os arquivos que comtemplam o sistema como um todo(funções compartilhadas entre todos usuários)
* **cliente**: Esse pacote contém os arquivos que estão limitados conceitualmente ao Cliente.
* **database**: Aqui se encontra os arquivos relacionados ao DataBase.
* **lojista**: Nesse pacote estão contidos os arquivos que estão limitados conceitualmente ao Cliente.
* **model**: O model contém os modelos de entidades do sistema
* **security**: Responsável pela segurança, criptografia, hash.
  
Além disso, as pastas **core**, **cliente** e **lojista** possuem a seguinte sub-divisão:
* **adapter**:comunicaoção entre os objetos
* **fragment**: referente aos fragments do projeto.

### Entrega 2: Persistência de Dados e Gestão de Catálogo

#### Requisitos:
* **Banco de Dados Local:** Utilização da biblioteca **Room Database** (SQLite abstrato) com arquitetura em Entidades e DAOs (Data Access Objects) para Usuários, Produtos e Itens do Pedido.
* **Autenticação e Sessão:** Telas de Login e Cadastro com controle de estado do usuário.

<div align="center">
  <img src="img/VVV/login.jpeg" alt="Tela de Login" width="250"/>
  <img src="img/VVV/cadastroUsuario.jpeg" alt="Cadastro de Usuário" width="250"/>
  <img src="img/VVV/RedefinirSenha.jpeg" alt="Redefinir senha" width="250"/>
</div>
<br>
<div align="center">
  <img src="img/VVV/vitrineUsuariojpeg.jpeg" alt="Vitrine de Produtos" width="250"/>
  <img src="img/VVV/descricaoProduto.jpeg" alt="Descricao produto" width="250"/>
  <img src="img/VVV/pedido.jpeg" alt="Carrinho e Pedidos" width="250"/>
</div>
<br>
<div align="center">
  <img src="img/VVV/gerenciarProdutos.jpeg" alt="Gerenciamento pelo Lojista" width="250"/>  
  <img src="img/VVV/cadastroProduto.jpeg" alt="Cadastro de Produtos" width="250"/>
  <img src="img/VVV/Perfil.jpeg" alt="Perfil do usuario" width="250"/>
</div>

---
### Entrega 3: Entrega Final do Projeto
#### Requisitos requeridos
* a) Fazer testes de caixa preta e tratar erros:
* b) Ter uma tela inicial de login com cadastro de usuários com foto:
* c) Ter tela de cadastro para outras tabelas no projeto:
* d) Tela de favoritos
* e) Recursos
  - Definir imagem para o aplicativo no Manifest
  - Definir Splash na inicialização no Manifest
  - LastView/RecyclerView/CardView para listar os dados das tabelas
  - usar fragments, string, cores, imagens, sons, menu
  - acessar câmera
  - Notificações/Alarmes e requisições HTTP
  - Mapa e Localização


#### Acesso Inicial
<div align="center">
  <img src="img/VVV/entregaFinal/splash.jpeg" alt="" width="250"/>
  <img src="img/VVV/entregaFinal/login.jpeg" alt="" width="250"/>
</div>

  > [!NOTE]
  > <p align="justify"><b>Splash e Login:</b> Tela de inicialização com a identidade visual da aplicação e interface de acesso segura. <br>O sistema utiliza formulários com Material Design, ofuscação de senha nativa e autenticação validada para direcionar corretamente Clientes e Lojistas aos seus respectivos fluxos.</p>

<div align="center">
  <img src="img/VVV/entregaFinal/cadastroUsuario.jpeg" alt="" width="250"/>
  <img src="img/VVV/entregaFinal/recuperarSenha.jpeg" alt="" width="250"/>
</div>

> [!NOTE]
> <p><b>Cadastro e Segurança:</b> Formulário de registro com validação visual de força de senha em tempo real. <br>As senhas são tratadas com criptografia Hash garantindo segurança. Inclui também o fluxo de recuperação de acesso via e-mail por meio do firebase.</p>

#### Lojista
<div align="center">
  <img src="img/VVV/entregaFinal/PerfilLojista.jpeg" alt="" width="250"/>
  <img src="img/VVV/entregaFinal/PerfilLojistap2.jpeg" alt="" width="250"/>
</div>

> [!NOTE]
> <p><b>Painel Administrativo:</b> Tela de perfil unificada que se adapta ao Lojista. <br>Permite a personalização completa da Vitrine através da definição de cores (Primária, Secundária e Fundo) que alteram dinamicamente todo o tema do aplicativo(funcionalidade ainda em melhoria), além da configuração do endereço físico.</p>

<div align="center">
  <img src="img/VVV/entregaFinal/gerenciarProdutosLojista.jpeg" alt="" width="250"/>
  <img src="img/VVV/entregaFinal/cadastrarProdutoLojista.jpeg" alt="" width="250"/>
  <img src="img/VVV/entregaFinal/atualizarProdutoLojista.jpeg" alt="" width="250"/>
</div>

> [!NOTE]
> <p><b>Gestão de Catálogo (CRUD):</b> Interface dedicada para o Lojista adicionar, editar e remover produtos. <br>Suporta a captura de imagens via câmera, inserção de vídeos demonstrativos e a opção estratégica de marcar produtos específicos como "Em Destaque" para a tela inicial do Cliente.</p>

<div align="center">
  <img src="img/VVV/entregaFinal/inicioLojista.jpeg" alt="" width="250"/>
  <img src="img/VVV/entregaFinal/historicoDetalhesPedidoLojista.jpeg" alt="" width="250"/>
  <img src="img/VVV/entregaFinal/notificacao.jpeg" alt="" width="250"/>
</div>

> [!NOTE]
> <p><b>Dashboard e Gestão de Vendas:</b> Tela inicial do Lojista com painel de métricas em tempo real (Faturamento, Visitas e Produtos). <br>Abaixo, um histórico contínuo de vendas onde o Lojista pode abrir um BottomSheet interativo para visualizar os detalhes exatos de cada pedido realizado, suportado por notificações nativas.</p>

#### Cliente/Visitante
<div align="center">
  <img src="img/VVV/entregaFinal/perfilCliente.jpeg" alt="" width="250"/>
  <img src="img/VVV/entregaFinal/meusPedidosCliente.jpeg" alt="" width="250"/>
  <img src="img/VVV/entregaFinal/detalhesPedidosJaRealizadosCliente.jpeg" alt="" width="250"/>
</div>

> [!NOTE]
> <p><b>Perfil do Cliente e Histórico:</b> Visão do consumidor focada na usabilidade. <br>Acesso rápido ao histórico de compras finalizadas. O sistema garante a integridade contábil exibindo os detalhes do pedido com os preços "congelados" no momento da compra (mesmo que o lojista altere o valor do produto no futuro).</p>

<div align="center">
  <img src="img/VVV/entregaFinal/vitrineCliente.jpeg" alt="" width="250"/>
  <img src="img/VVV/entregaFinal/imagemMaps.jpeg" alt="" width="250"/>
</div>

> [!NOTE]
> <p><b>Vitrine Dinâmica e Localização:</b> Catálogo principal interativo com rolagem otimizada (RecyclerView) que exibe produtos em destaque e a lista geral. <br>Integração com a API do Google Maps, permitindo ao cliente visualizar a localização exata do endereço cadastrado pelo lojista.</p>

<div align="center">
  <img src="img/VVV/entregaFinal/detalhesProdutoCliente.jpeg" alt="" width="250"/>
  <img src="img/VVV/entregaFinal/detalhesProdutoVideoCliente.jpeg" alt="" width="250"/>
  <img src="img/VVV/entregaFinal/pedidoCliente.jpeg" alt="" width="250"/>
</div>

> [!NOTE]
  > <p><b>Visualização Premium e Checkout:</b> Tela de detalhes rica em informações, suportando a exibição de mídias (fotos e vídeos curtos do produto). <br>O fluxo de compra flui para um carrinho (Meu Pedido) onde o cliente revisa quantidades e subtotais antes de confirmar e gerar o pedido na base de dados.</p>



---
⚠ **Atenção**: Material com fins de aprendizado, e assim sendo, pode conter **erros** e **insconsistências**.

* ### **Links e material de apoio** 📖
* - [Android Developer](https://developer.android.com/get-started/overview?hl=pt-br)
* - [Guia Android](https://www.devmedia.com.br/guia/android/34580)
* - Material Programação Mobile UFMS - Prof. Ana Karina Dourado
<!--
 - [Android Developer](https://www.devmedia.com.br/guia/android/34580)
 - [Diagrama de Classes](https://deinf.ufma.br/~geraldo/dob/7.Classes.pdf)
 - [C4 Model](https://medium.com/cajudevs/entendendo-o-c4-model-uma-abordagem-para-arquitetura-de-software-3ed0f007ae66)
 <img src="img/VVV/entregaFinal/.jpeg" alt="" width="250"/>
 ->
