package com.mySportPage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public abstract class BaseTest {

    protected abstract String getRootPath();

    @Autowired
    private MockMvc mockMvc;

    protected MvcResult performGETRequest(String path) throws Exception {
        return mockMvc.perform(get(path))
                .andDo(print())
                .andReturn();
    }

    protected MvcResult performPOSTRequest(String path) throws Exception {
        return mockMvc.perform(post(path))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
    }

    protected MvcResult performPOSTRequestWithContent(String path, String content) throws Exception {
        return mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
    }

    protected String createPath(String restPath, Map<String, String> pathParams, Map<String, String> queryParams) {
        StringBuilder path = new StringBuilder(getRootPath() + restPath);
        if (pathParams != null) {
            path = fillPathParams(pathParams, path);
        }
        if (queryParams != null) {
            path = fillQueryParams(queryParams, path);
        }
        return path.toString();
    }

    protected StringBuilder fillPathParams(Map<String, String> pathParams, StringBuilder path) {
        for (Map.Entry<String, String> entry : pathParams.entrySet()) {
            path = new StringBuilder(path.toString()
                    .replace(String.format("{%s}", entry.getKey()), entry.getValue()));
        }
        return path;
    }

    protected StringBuilder fillQueryParams(Map<String, String> queryParams, StringBuilder path) {
        path.append("?");
        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            path.append(String.format("%s=%s&", entry.getKey(), entry.getValue()));
        }
        path = new StringBuilder(path.substring(0, path.lastIndexOf("&")));
        return path;
    }

    protected <T> T mapInternalResponse(MvcResult result, TypeReference<T> type) throws JsonProcessingException, UnsupportedEncodingException {
        String responseBody = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(responseBody);
        JsonNode response = rootNode.path("data");
        if(response.toString().equals("")) {
            response = rootNode.get("errorMessage");
        }
        return objectMapper.readValue(response.toString(), type);
    }
}
