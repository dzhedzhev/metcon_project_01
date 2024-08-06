package com.lineragency.metcon_tasks.web;

import com.lineragency.metcon_tasks.data.TaskRepository;
import com.lineragency.metcon_tasks.model.entity.Task;
import com.lineragency.metcon_tasks.model.enums.ContainerIsoType;
import com.lineragency.metcon_tasks.model.enums.RequestEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerIT {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private MockMvc mockMvc; //Механизъм, с който можем да си пращаме истински HTTP заявки към нашия контролер.

    @Test
    public void testGetTaskById() throws Exception {
        Task testTask = new Task();
        testTask.setType(RequestEnum.RECEIVE);
        testTask.setCompany("TEST");
        testTask.setContainerNumber("TEST0000001");
        testTask.setContainerType(ContainerIsoType.FORTY_FT_HC);
        testTask.setDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0));
        testTask.setTruck("T0000TT");
        testTask.setRequestId(1L);
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
}
