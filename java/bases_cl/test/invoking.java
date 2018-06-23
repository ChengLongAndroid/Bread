package bases_cl.test;

import org.junit.Test;

import com.ebo.storage.redis.JRedis;

import redis.clients.jedis.Jedis;



public class invoking {

	static JRedis jedis ;
	
	
	
	@Test
    public void testString() {
       //-----添加数据----------  
		
        jedis.set("name".getBytes(),"xinxin".getBytes());//向key-->name中放入了value-->xinxin  
        System.out.println(jedis.get("name".getBytes()));//执行结果：xinxin  
        //设置多个键值对
        
        jedis.incr("age".getBytes()); //进行加1操作
        
  }
	
	
	
	   @Test  
	     public void testList(){  
	         //开始前，先移除所有的内容  
	         jedis.del("java framework".getBytes());  
	         System.out.println(jedis.lrange("java framework".getBytes(),0,-1));  
	         //先向key java framework中存放三条数据  
	         jedis.lpush("java framework".getBytes(),"spring".getBytes());  
	         jedis.lpush("java framework".getBytes(),"struts".getBytes());  
	         jedis.lpush("java framework".getBytes(),"hibernate".getBytes());  
	        //再取出所有数据jedis.lrange是按范围取出，  
	         // 第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有  
	        System.out.println(jedis.lrange("java framework".getBytes(),0,-1));  
	         
	         jedis.del("java framework".getBytes());
	         jedis.rpush("java framework".getBytes(),"spring".getBytes());  
	         jedis.rpush("java framework".getBytes(),"struts".getBytes());  
	         jedis.rpush("java framework".getBytes(),"hibernate".getBytes()); 
	         System.out.println(jedis.lrange("java framework".getBytes(),0,-1));
	     }  
	
	
	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		Person person = new Person(100, "alan");
//	
		
		
		
		
		
//		jRedis.set("person:100".getBytes(), SerializeUtil.serialize(person));
//		
////		person = new Person(101, "bruce");
////		jRedis.set("person:101".getBytes(), SerializeUtil.serialize(person));
//		
//		System.out.println("----------------");
//		
//		
//		getperson(101);
//		
//		Person person1 = getperson(100);
//		System.out.println(person1.getId());
//		
//		
////		System.out.println(person1.getName());
////		person1 = getperson(101);
////		System.out.println(person1.getId());
////		System.out.println(person1.getName());
//			
//			
//	}
	
//	public  void intoe(){
//		
//	}

	
//	public static  Person getperson(int id){
//		 
//			byte[] person = jRedis.get(("person:" + id).getBytes());
//			return (Person) SerializeUtil.unserialize(person);
//			
//	}
}
