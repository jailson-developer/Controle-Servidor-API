package br.com.pjc.controle_servidor.modules.servidor.controller;

import br.com.pjc.controle_servidor.modules.pessoa.dto.EnderecoDto;
import br.com.pjc.controle_servidor.modules.security.dto.AuthRequestDto;
import br.com.pjc.controle_servidor.modules.servidor.dto.UnidadeRequestDto;
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

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UnidadeControllerTest {
    private final Faker faker = new Faker();
    private static UnidadeRequestDto dto;

    private static Long unidadeId;

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
        dto = UnidadeRequestDto.builder()
                .unidNome(faker.name().firstName())
                .unidSigla(faker.name().firstName())
                .endereco(enderecoDto)
                .build();
        Response res = given()
                .when()
                .contentType(ContentType.JSON)
                .body(dto).post("/v1/unidades")
                .then()
                .statusCode(201).extract().response();
        unidadeId = res.jsonPath().getLong("id");
        return unidadeId;
    }

    @Test
    @Order(2)
    void findById() {
        given().contentType(ContentType.JSON)
                .when()
                .get("/v1/unidades/".concat(unidadeId.toString()))
                .then().body("unidSigla", equalTo(dto.getUnidSigla()))
                .statusCode(200);
    }

    @Test
    @Order(3)
    void findAll() {
        given()
                .when().queryParam("nome", dto.getUnidNome())
                .contentType(ContentType.JSON)
                .get("/v1/unidades/")
                .then().body("totalCount", Matchers.equalTo(1))
                .statusCode(200);
    }

    @Test
    @Order(4)
    void excluir() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .delete("/v1/unidades/".concat(unidadeId.toString()))
                .then()
                .statusCode(204);
    }

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
}