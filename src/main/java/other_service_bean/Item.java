package other_service_bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Item {
	
	private String itemId;
	
	private Integer productId;
	
	private String productName;
	
	private Integer quantity=0;
	
	private Double price=0.0;
	
}
