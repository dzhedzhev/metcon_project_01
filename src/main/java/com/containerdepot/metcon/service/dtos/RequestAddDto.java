package com.containerdepot.metcon.service.dtos;
import com.containerdepot.metcon.model.enums.RequestEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class RequestAddDto {
    @NotNull(message = "Request type must be selected!")
    private RequestEnum type;
    @NotBlank(message = "Container number must not be empty!")
    @Pattern(regexp = "([a-zA-Z]{3})([UJZujz])(\\d{6})(\\d)", message = "Container number is not in correct format!")
    private String containerNumber;
    @NotBlank(message = "Container type must be selected!")
    private String containerType;
    private String truck;

    public RequestAddDto() {
    }

    public RequestEnum getType() {
        return type;
    }

    public void setType(RequestEnum type) {
        this.type = type;
    }

    public String getContainerNumber() {
        return containerNumber;
    }

    public void setContainerNumber(String number) {
        this.containerNumber = number;
    }

    public String getContainerType() {
        return containerType;
    }

    public void setContainerType(String containerType) {
        this.containerType = containerType;
    }

    public String getTruck() {
        return truck;
    }

    public void setTruck(String truck) {
        this.truck = truck;
    }
}
