package com.escola.gerencimentoEscola;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.escola.gerencimentoEscola.model.Aluno;
import com.escola.gerencimentoEscola.model.AlunoDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AlunoControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate tRestTemplate;

    @Test 
    public void testGetAlunos() throws Exception {
        var url = String.format("http://localhost:%d/%s", port, "/alunos");
        var response = tRestTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<AlunoDTO>>() {});
        assert(response.getStatusCode() == HttpStatus.OK);
        assert(response.getBody().isEmpty());
    }

    @Test
    public void postAluno() throws Exception {
        var url = String.format("http://localhost:%d/%s", port, "/alunos");
        var requestBody = "{ \"matricula\": \"aaaaa\", \"nome\": \"joão\", \"idade\": 19 }";
        var httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        var request = new HttpEntity<>(requestBody, httpHeaders);
        var response = tRestTemplate.postForEntity(url, request, Aluno.class);
        System.out.println(response.getBody());
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody().getNome() == "joão";
        assert response.getBody().getMatricula() == "aaaaa";
        assert response.getBody().getIdade() == 19;
    }

}
