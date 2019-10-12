package by.milosh.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rate {

    private int curID;
    private LocalDateTime date;
    private String curAbbreviation;
    private int curScale;
    private String curName;
    private BigDecimal curOfficialRate;

    @JsonProperty("Cur_ID")
    public void setCurID(int curID) {
        this.curID = curID;
    }

    @JsonProperty("Date")
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @JsonProperty("Cur_Abbreviation")
    public void setCurAbbreviation(String curAbbreviation) {
        this.curAbbreviation = curAbbreviation;
    }

    @JsonProperty("Cur_Scale")
    public void setCurScale(int curScale) {
        this.curScale = curScale;
    }

    @JsonProperty("Cur_Name")
    public void setCurName(String curName) {
        this.curName = curName;
    }

    @JsonProperty("Cur_OfficialRate")
    public void setCurOfficialRate(BigDecimal curOfficialRate) {
        this.curOfficialRate = curOfficialRate;
    }
}
