package com.jianfei.core.common.utils;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Date;
import java.util.UUID;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.springframework.context.annotation.Lazy;

/**
 *
 * @Description: 封装各种生成唯一性ID算法的工具类.
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月18日 下午1:39:37
 * 
 * @version 1.0.0
 *
 */
@Lazy(false)
public class IdGen implements SessionIdGenerator {

	private static SecureRandom random = new SecureRandom();

	/**
	 * 参数machineId：是集群时的机器代码，可以1-9任意。部署时，分别为部署的项目手动修改该值，以确保集群的多台机器在系统时间上不一致的问题（毫无疑问每台机器的毫秒数基本上不一致）。
	 参数System.currentTimeMillis():这是java里面的获取1970年到目前的毫秒数,是一个13位数的数字，与Date.getTime()函数的结果一样，比如1378049585093。经过研究，在2013年，前三位是137，在2023年是168，到2033年才199.所以，我决定第一位数字1可以去掉，不要占位置了。可以肯定绝大多数系统用不了10年20年。这样，参数2就变成了12位数的数字，加上参数1machineId才13位数。
	 参数System.nanoTime()：这是java里面的取纳秒数，经过深入研究，在同一毫秒内，位置7,8,9这三个数字是会变化的。所以决定截取这三个数字出来拼接成一个16位数的订单号。
	 总结：理论上此方案在同一秒内，可以应对1000*1000个订单号，但是经过测试，在每秒并发2000的时候，还是会出现2-10个重复
	 */
	public static String uuid() {
		return "0"+
				(System.currentTimeMillis()+"").substring(1)+
				(System.nanoTime()+"").substring(7,10);
	}

	/**
	 * 使用SecureRandom随机生成Long.
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}

	/**
	 * 基于Base62编码的SecureRandom随机生成bytes.
	 */
	public static String randomBase62(int length) {
		byte[] randomBytes = new byte[length];
		random.nextBytes(randomBytes);
		return Encodes.encodeBase62(randomBytes);
	}

	@Override
	public Serializable generateId(Session session) {
		return IdGen.uuid();
	}

	public static void main(String[] args) {
		System.out.println(IdGen.uuid());
		System.out.println(IdGen.uuid().length());

	}

}
