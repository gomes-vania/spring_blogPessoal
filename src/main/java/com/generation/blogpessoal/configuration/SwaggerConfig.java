package com.generation.blogpessoal.configuration;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // COMENTADA ABAIXO
public class SwaggerConfig {

    /*
    A Anotação (Annotation) @Configuration indica que a Classe é do tipo configuração, ou seja,
    define uma Classe como fonte de definições de beans. A anotação @Configuration é uma das anotações essenciais ao utilizar uma configuração baseada em Java.
    A Anotação @Bean utilizada em Métodos de uma Classe, geralmente marcada com @Configuration,
    indica ao Spring que ele deve invocar aquele Método e gerenciar o objeto retornado por ele, ou seja,
    agora este objeto pode ser injetado em qualquer ponto da sua aplicação. by: @VictorSa/Meli
    Bean: No Spring, os objetos que formam a espinha dorsal da sua aplicação e que são gerenciados pelo Spring são chamados de Beans.
    Um Bean é um objeto que é instanciado, montado e gerenciado pelo Spring.
    Existem diversas formas de se criar Beans no Spring. Você pode criar Classes anotadas com @Configuration ou @Service para serem gerenciadas pelo Spring,
    assim como pode usar a anotação @Bean em um Método, e transformar a instância retornada pelo Método
    em um Objeto gerenciado pelo Spring (seja de uma Classe própria ou de terceiros).
    Estas Classes, que na visão do Spring são os Beans, para você nada mais são do que Classes que você irá escrever as regras de funcionamento da sua aplicação,
    que poderão ser utilizadas em qualquer Classe, diferente da Injeção de Dependência criada pela anotação @Autowired,
    que só permite o uso dentro da Classe em que foi criada.
    */


    @Bean // COMENTADA ACIMA
    public OpenAPI springBlogpessoalOpenAPI() { //Cria um Objeto da Classe OpenAPI, que gera a documentação no Swagger utilizando a especificação OpenAPI.
        return new OpenAPI()

                //Insere as informações sobre a API (Nome do projeto (Title), Descrição e Versão, Licença, Links, E-mails, e contatos.)
                .info(new Info()
                        .title("Blogpessoal")
                        .description("Blogpessoal - Vania Gomes Mercado Livre")
                        .version("1.0")
                        .license(new License()
                                .name("Mercado Livre")
                                .url("https://github.com/gomes-vania/spring_blogpessoal"))
                        .contact(new Contact().name("vaniagomes")
                                .email("vania.vgomes@mercadolivre.com").url("https://github.com/gomes-vania/spring_blogpessoal")))
                .externalDocs(new ExternalDocumentation()
                        .description("GitHub")
                        .url("https://github.com/gomes-vania/spring_blogpessoal"));
    }

    @Bean
    public OpenApiCustomiser customerGlobalHeaderOpenApiCustomiser(){
        /*
        A Classe OpenApiCustomiser permite personalizar o Swagger, baseado na Especificação OpenAPI.
        O Método acima, personaliza todas as mensagens HTTP Responses (Respostas das requisições) do Swagger.
        */

        return openApi -> {
            openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> { //  Cria um Objeto da Classe OpenAPI, que gera a documentação no Swagger utilizando a especificação OpenAPI.
                    /*
                        Cria um primeiro looping que fará a leitura de todos os recursos (Paths) através do Método getPaths(),
                        que retorna o caminho de cada endpoint. Na sequência, cria um segundo looping que Identificará qual Método HTTP (Operations),
                        está sendo executado em cada endpoint através do Método readOperations(). Para cada Método,
                        todas as mensagens serão lidas e substituídas pelas novas mensagens.
                        */


                //Cria um Objeto da Classe ApiResponses, que receberá as Respostas HTTP de cada endpoint (Paths) através do Método getResponses().
                ApiResponses apiResponses = operation.getResponses();

                //Adiciona as novas Respostas no endpoint.
                apiResponses.addApiResponse("200", createApiResponse("Sucesso!"));
                apiResponses.addApiResponse("201", createApiResponse("Objeto Persistido!"));
                apiResponses.addApiResponse("204", createApiResponse("Objeto Excluido !"));
                apiResponses.addApiResponse("400", createApiResponse("Erro na Requisição!"));
                apiResponses.addApiResponse("401", createApiResponse("Acesso nao autorizado!"));
                apiResponses.addApiResponse("404", createApiResponse("Objeto nao encontrado!"));
                apiResponses.addApiResponse("500", createApiResponse("Erro na aplicação!"));



            }));
        };


    }
    // O Método createApiResponse() adiciona uma descrição (Mensagem), em cada Resposta HTTP.
    private ApiResponse createApiResponse(String message) {

        return new ApiResponse().description(message);
    }
}




