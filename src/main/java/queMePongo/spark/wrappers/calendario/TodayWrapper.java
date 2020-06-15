package queMePongo.spark.wrappers.calendario;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
public class TodayWrapper {

    String strToday;

    public TodayWrapper (){
        Date today = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
         strToday = dateFormat.format(today);
    }



}
