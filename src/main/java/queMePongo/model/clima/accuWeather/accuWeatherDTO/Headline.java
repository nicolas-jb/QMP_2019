package queMePongo.model.clima.accuWeather.accuWeatherDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Headline {
	private String effectiveDate;
	private long effectiveEpochDate;
	private int severity;
	private String text;
	private String category;
	private String endDate;
	private int endEpochDate;
	private String mobileLink;
	private String link;
}
