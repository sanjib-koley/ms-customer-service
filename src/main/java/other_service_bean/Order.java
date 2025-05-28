package other_service_bean;

import java.util.LinkedList;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Order {
    
 
    private String id;
    
	private String orderId;
	
	private Integer cartId;
	
	private Long customerId;
	
	private List<Item> items = new LinkedList<>();
	
	private Double orderValue=0.0;
	
	private String address;
	
	private String paymentInfo;
	
	private String orderStatus;

}
