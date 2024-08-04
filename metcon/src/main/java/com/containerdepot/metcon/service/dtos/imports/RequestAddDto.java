package com.containerdepot.metcon.service.dtos.imports;
import com.containerdepot.metcon.model.enums.ContainerIsoType;
import com.containerdepot.metcon.model.enums.RequestEnum;
import com.containerdepot.metcon.service.validation.ValidRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
@ValidRequest
public class RequestAddDto {
    @NotNull(message = "Request type must be selected!")
    private RequestEnum type;
    @NotBlank(message = "Container number must not be empty!")
    @Pattern(regexp = "([a-zA-Z]{3})([UJZujz])(\\d{6})(\\d)", message = "Container number is not in correct format!")
    private String containerNumber;
    @NotNull(message = "Container type must be selected!")
    private ContainerIsoType containerType;
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

    public ContainerIsoType getContainerType() {
        return containerType;
    }

    public void setContainerType(ContainerIsoType containerType) {
        this.containerType = containerType;
    }

    public String getTruck() {
        return truck;
    }

    public void setTruck(String truck) {
        this.truck = truck;
    }
}
