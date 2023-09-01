package com.example;

import com.example.MyUtils.JedisUtil.JedisPoolFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

import java.util.Random;

@SpringBootTest
public class CheckcodeApplicationTests {

    //检查验证码
    public void check(String phonenum, String code){
        String id=phonenum+":code:";
        Jedis jedis = JedisPoolFactory.getJedis();
        if(jedis.get(id).equals(code)){
            System.out.println("成功");
        }
        else{
            System.out.println("失败");
        }
        jedis.close();
    }

    @Test
    public void test(){
        VerifyCode("12232428");
    }


    public void VerifyCode(String phonenum){
        String id1=phonenum+":code:";
        String id2=phonenum+":count:";
        //模拟发送验证码并存入jedis中
        Jedis jedis=JedisPoolFactory.getJedis();
        String code=getCode(6);
        if(jedis.get(id2)==null||Integer.parseInt(jedis.get(id2))<=2){
            if(jedis.get(id2)==null) jedis.setex(id2,24*60*60,"1");
            else jedis.incr(id2);
            jedis.setex(id1, 120, code);
            System.out.println(jedis.get(id1));
            System.out.println(jedis.get(id2));
            check(phonenum,code);
        }
        else{
            System.out.println(jedis.get(id1));
            System.out.println(jedis.get(id2));
            System.out.println("今天不能发送了");
        }
        jedis.close();
    }

    //生成n位验证码
    public static String getCode(int n){
        Random random=new Random();
        StringBuilder code= new StringBuilder();
        for(int i=0;i<n;i++){
            code.append(random.nextInt(0,10));
        }
        return code.toString();
    }

}
