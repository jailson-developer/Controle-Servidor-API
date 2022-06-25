package br.com.pjc.controle_servidor.modules.servidor.controller;

import br.com.pjc.controle_servidor.modules.security.dto.AuthRequestDto;
import br.com.pjc.controle_servidor.modules.servidor.dto.LotacaoRequestDto;
import com.github.javafaker.Faker;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.oauth2;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LotacaoControllerTest {
    private final Faker faker = new Faker();
    private static LotacaoRequestDto dto;

    @Test
    @Order(1)
    void salvar() {
        dto = LotacaoRequestDto.builder()
                .dataLotacao(LocalDate.now())
                .unidadeId(new UnidadeControllerTest().salvar())
                .pessoaId(new ServidorEfetivoControllerTest().salvar())
                .portaria(faker.number().digits(5))
                .build();

        given()
                .when()
                .contentType(ContentType.JSON)
                .body(dto).post("/v1/lotacoes")
                .then()
                .statusCode(201);
    }

    @Test
    @Order(2)
    void editar() {
        dto.setDataLotacao(LocalDate.of(2020, 6, 6));

        given()
                .when()
                .contentType(ContentType.JSON)
                .body(dto).put("/v1/lotacoes/1")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(3)
    void findById() {
        given().contentType(ContentType.JSON)
                .when()
                .get("/v1/lotacoes/1")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(4)
    void findAll() {
        given()
                .when().queryParam("portaria", dto.getPortaria())
                .contentType(ContentType.JSON)
                .get("/v1/lotacoes")
                .then().body("totalCount", Matchers.equalTo(1))
                .statusCode(200);
    }

    @Test
    @Order(5)
    void excluir() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .delete("/v1/lotacoes/1")
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