package br.com.pjc.controle_servidor.modules.servidor.controller;

import br.com.pjc.controle_servidor.modules.pessoa.dto.EnderecoDto;
import br.com.pjc.controle_servidor.modules.pessoa.enums.ESexo;
import br.com.pjc.controle_servidor.modules.security.dto.AuthRequestDto;
import br.com.pjc.controle_servidor.modules.servidor.dto.ServidorTemporarioRequestDTO;
import br.com.pjc.controle_servidor.modules.servidor.dto.UnidadeRequestDto;
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
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ServidorTemporarioControllerTest {
    private final Faker faker = new Faker();
    private static ServidorTemporarioRequestDTO dto;
    private static Long servidorId;

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
                .get("/v1/servidores/temporario/".concat(servidorId.toString()))
                .then().body("nome", equalTo(dto.getNome()))
                .statusCode(200);
    }

    @Test
    @Order(1)
    void salvar() {
        EnderecoDto enderecoDto = EnderecoDto.builder()
                .cidId(1L)
                .endBairro(faker.address().city())
                .endLogradouro(faker.address().firstName())
                .endNumero(faker.address().buildingNumber())
                .endTipoLogradouro(faker.address().countryCode())
                .build();
        dto = ServidorTemporarioRequestDTO.builder()
                .servidorDataAdmissao(LocalDate.now())
                .servidorDataDemissao(LocalDate.now())
                .endereco(enderecoDto)
                .mae(faker.name().fullName())
                .pai(faker.name().firstName())
                .nome(faker.name().firstName())
                .sexo(ESexo.values()[faker.random().nextInt(0, ESexo.values().length - 1)])
                .build();
        Response res = given()
                .when()
                .contentType(ContentType.JSON)
                .body(dto).post("/v1/servidores/temporario")
                .then()
                .statusCode(201).extract().response();
        servidorId = res.jsonPath().getLong("id");
    }

    @Test
    @Order(2)
    void editar() {
        dto.setNome(faker.name().fullName());
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(dto).put("/v1/servidores/temporario/".concat(servidorId.toString()))
                .then()
                .statusCode(200);
    }

    @Test
    @Order(5)
    void deleteById() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .delete("/v1/servidores/temporario/".concat(servidorId.toString()))
                .then()
                .statusCode(204);
    }

    @Test
    @Order(3)
    void findAll() {
        given()
                .when().queryParam("nome", dto.getNome())
                .contentType(ContentType.JSON)
                .get("/v1/servidores/temporario")
                .then().body("totalCount", Matchers.equalTo(1))
                .statusCode(200);
    }
}