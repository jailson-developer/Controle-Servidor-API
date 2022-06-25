package br.com.pjc.controle_servidor.modules.servidor.controller;

import br.com.pjc.controle_servidor.modules.pessoa.dto.EnderecoDto;
import br.com.pjc.controle_servidor.modules.pessoa.enums.ESexo;
import br.com.pjc.controle_servidor.modules.security.dto.AuthRequestDto;
import br.com.pjc.controle_servidor.modules.servidor.dto.LotacaoRequestDto;
import br.com.pjc.controle_servidor.modules.servidor.dto.ServidorEfetivoRequestDTO;
import com.github.javafaker.Faker;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.oauth2;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.Order;

import java.time.LocalDate;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ServidorEfetivoControllerTest {
    private final Faker faker = new Faker();
    private static ServidorEfetivoRequestDTO dto;
    private static Long servidorId;
    private static Long lotacaoId;

    @BeforeEach
    void setUp() {
        AuthRequestDto login = new AuthRequestDto();
        login.setUsername("Admin");
        login.setPassword("219669e6ebcaf1855a89c684e8899ed2");
        Response res = given()
                .when()
                .contentType(ContentType.JSON)
                .body(login).post("/autenticacao/login")
                .then()
                .statusCode(200).extract().response();
        var token = res.jsonPath().getString("access_token");
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.authentication = oauth2(token);
    }

    @Test
    @Order(4)
    void findById() {
        given().contentType(ContentType.JSON)
                .when()
                .get("/v1/servidores/efetivo/".concat(servidorId.toString()))
                .then().body("nome", equalTo(dto.getNome()))
                .statusCode(200);
    }

    @Test
    @Order(5)
    void enderecoFuncional() {
        LotacaoRequestDto lotacaoDto = LotacaoRequestDto.builder()
                .dataLotacao(LocalDate.now())
                .unidadeId(new UnidadeControllerTest().salvar())
                .pessoaId(servidorId)
                .portaria(faker.number().digits(5))
                .build();
        //Salva a lotação antes de consultar
        lotacaoId = given()
                .when()
                .contentType(ContentType.JSON)
                .body(lotacaoDto).post("/v1/lotacoes")
                .then()
                .statusCode(201).extract().response().jsonPath().getLong("id");

        given().contentType(ContentType.JSON)
                .when().pathParam("nomeServidor", dto.getNome().substring(3))
                .get("/v1/servidores/efetivo/endereco-funcional/{nomeServidor}")
                .then().body("totalCount", Matchers.equalTo(1))
                .statusCode(200);
    }

    @Test
    @Order(1)
    Long salvar() {
        EnderecoDto enderecoDto = EnderecoDto.builder()
                .cidId(1L)
                .endBairro(faker.address().city())
                .endLogradouro(faker.address().firstName())
                .endNumero(faker.address().buildingNumber())
                .endTipoLogradouro(faker.address().countryCode())
                .build();
        dto = ServidorEfetivoRequestDTO.builder()
                .matricula(faker.number().digits(10))
                .endereco(enderecoDto)
                .mae(faker.name().fullName())
                .pai(faker.name().firstName())
                .nome(faker.name().firstName())
                .sexo(ESexo.values()[faker.random().nextInt(0, ESexo.values().length - 1)])
                .build();
        Response res = given()
                .when()
                .contentType(ContentType.JSON)
                .body(dto).post("/v1/servidores/efetivo")
                .then()
                .statusCode(201).extract().response();
        servidorId = res.jsonPath().getLong("id");
        return servidorId;
    }

    @Test
    @Order(2)
    void editar() {
        dto.setNome(faker.name().fullName());
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(dto).put("/v1/servidores/efetivo/".concat(servidorId.toString()))
                .then()
                .statusCode(200);
    }

    @Test
    @Order(6)
    void deleteById() {
        //Exclui a lotação antes de excluir o servidor
        given()
                .when()
                .contentType(ContentType.JSON)
                .delete("/v1/lotacoes/".concat(lotacaoId.toString()))
                .then()
                .statusCode(204);

        given()
                .when()
                .contentType(ContentType.JSON)
                .delete("/v1/servidores/efetivo/".concat(servidorId.toString()))
                .then()
                .statusCode(204);
    }

    @Test
    @Order(3)
    void findAll() {
        given()
                .when().queryParam("nome", dto.getNome())
                .contentType(ContentType.JSON)
                .get("/v1/servidores/efetivo")
                .then().body("totalCount", Matchers.equalTo(1))
                .statusCode(200);
    }
}