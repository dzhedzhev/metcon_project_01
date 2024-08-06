package com.lineragency.metcon_tasks.web;

import com.jayway.jsonpath.JsonPath;
import com.lineragency.metcon_tasks.data.TaskRepository;
import com.lineragency.metcon_tasks.model.entity.Task;
import com.lineragency.metcon_tasks.model.enums.ContainerIsoType;
import com.lineragency.metcon_tasks.model.enums.RequestEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerIT {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private MockMvc mockMvc; //Механизъм, с който можем да си пращаме истински HTTP заявки към нашия контролер.

    @Test
    public void testGetTaskById() throws Exception {
        Task testTask = createTestTask();
        Task actualTask = taskRepository.save(testTask);
//        {
//                "id": 6,
//                "type": "RELEASE",
//                "company": "MET LTD.",
//                "containerNumber": "MKLU4034500",
//                "containerType": "FORTY_FT_HC",
//                "truck": "A4500AH",
//                "dateTime": "2024-08-03T20:10:00",
//                "requestId": 3
//        }
        mockMvc.perform(get("/tasks/{id}", actualTask.getId())
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.type", is(actualTask.getType().toString())))
                .andExpect(jsonPath("$.company", is(actualTask.getCompany())))
                .andExpect(jsonPath("$.containerNumber", is(actualTask.getContainerNumber())))
                .andExpect(jsonPath("$.dateTime", is(actualTask.getDateTime().format(DateTimeFormatter.ISO_DATE_TIME))))
                .andExpect(jsonPath("$.requestId", is((int)actualTask.getRequestId())));
    }

    @Test
    public void testCreateTask() throws Exception {

        MvcResult result = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "type" : "RELEASE",
                                  "company" : "TEST",
                                  "containerNumber" : "TEST4000000",
                                  "containerType" : "FORTY_FT_HC",
                                  "truck" : "T0000TT",
                                  "dateTime" : "2024-01-01T10:00",
                                  "requestId" : 10
                                }
                                """))
                .andExpect(status().isCreated()).andExpect(header().exists("Location"))
                .andReturn();

        String body = result.getResponse().getContentAsString();
        int id = JsonPath.read(body, "$.id");

        Optional<Task> optionalTask = taskRepository.findById((long) id);
        Assertions.assertTrue(optionalTask.isPresent());
        Task task = optionalTask.get();
        Assertions.assertEquals("RELEASE", task.getType().toString());
        Assertions.assertEquals("TEST", task.getCompany());
        Assertions.assertEquals("TEST4000000", task.getContainerNumber());
        Assertions.assertEquals("FORTY_FT_HC", task.getContainerType().toString());
        Assertions.assertEquals("T0000TT", task.getTruck());
        Assertions.assertEquals("2024-01-01T10:00:00", task.getDateTime().format(DateTimeFormatter.ISO_DATE_TIME));
        Assertions.assertEquals(10L, task.getRequestId());
    }

    @Test
    public void testDeleteTask() throws Exception {
        Task savedTask = taskRepository.save(createTestTask());
        mockMvc.perform(delete("/tasks/delete/{id}", savedTask.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        Assertions.assertTrue(taskRepository.findById(savedTask.getId()).isEmpty());
    }
    @Test
    public void testAPITaskNotFound() throws Exception {
        mockMvc.perform(get("/tasks/{id}",999999L)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    private Task createTestTask() {
        Task testTask = new Task();
        testTask.setType(RequestEnum.RECEIVE);
        testTask.setCompany("TEST");
        testTask.setContainerNumber("TEST0000001");
        testTask.setContainerType(ContainerIsoType.FORTY_FT_HC);
        testTask.setDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0));
        testTask.setTruck("T0000TT");
        testTask.setRequestId(1L);
        return testTask;
    }
}
