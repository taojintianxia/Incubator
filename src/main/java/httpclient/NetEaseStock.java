package httpclient;

import lombok.Data;

@Data
public class NetEaseStock {

	String stockId;

	class StockEntity{
		Long code;
		Double percent;
		Double high;
		Double askvol5;
		Double askvol4;
		Double askvol3;
		Double askvol2;
		Double askvol1;
	}
}
