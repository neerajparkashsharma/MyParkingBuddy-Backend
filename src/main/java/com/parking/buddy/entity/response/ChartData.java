package com.parking.buddy.entity.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChartData
{
    private String id;


    private List<DataPoint> data;

}
