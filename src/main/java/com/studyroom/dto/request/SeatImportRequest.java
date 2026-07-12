package com.studyroom.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatImportRequest {

    @JsonProperty("seatNumber")
    private String seatNumber;

    @JsonProperty("no")
    private String no;

    private Integer row;

    private Integer col;

    @JsonProperty("hasSocket")
    private Boolean hasSocket;

    public String getSeatNumber() {
        return seatNumber != null ? seatNumber : no;
    }

    public Integer getRow() {
        if (row != null) {
            return row;
        }
        String seatNo = getSeatNumber();
        if (seatNo != null && seatNo.contains("-")) {
            String rowPart = seatNo.split("-")[0];
            if (rowPart.length() == 1 && Character.isLetter(rowPart.charAt(0))) {
                return rowPart.charAt(0) - 'A' + 1;
            }
        }
        return null;
    }

    public Integer getCol() {
        if (col != null) {
            return col;
        }
        String seatNo = getSeatNumber();
        if (seatNo != null && seatNo.contains("-")) {
            String colPart = seatNo.split("-")[1];
            try {
                return Integer.parseInt(colPart);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }
}