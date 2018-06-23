package bases_cl.props;
//道具表 ：  道具ID ，道具name  道具类型       道具价值，道具使用付款物品（钻石，奶酪）
public class Prop {

	private int  id;
	private String name;
	private String quality;
	private int price;
	private int pattern;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQuality() {
		return quality;
	}
	public void setQuality(String quality) {
		this.quality = quality;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getPattern() {
		return pattern;
	}
	public void setPattern(int pattern) {
		this.pattern = pattern;
	}
	@Override
	public String toString() {
		return "Prop [id=" + id + ", name=" + name + ", quality=" + quality
				+ ", price=" + price + ", pattern=" + pattern + "]";
	}
	public Prop(int id, String name, String quality, int price, int pattern) {
		super();
		this.id = id;
		this.name = name;
		this.quality = quality;
		this.price = price;
		this.pattern = pattern;
	}
	
	
	
	
	
	
	
	

	
	
}
