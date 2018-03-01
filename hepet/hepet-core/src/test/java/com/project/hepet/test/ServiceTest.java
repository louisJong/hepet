package com.project.hepet.test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.UUID;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.project.hepet.service.impl.AddressServiceImpl;
import com.project.hepet.service.impl.FileServiceImpl;
import com.project.hepet.service.impl.OrderServiceImpl;

public class ServiceTest extends BaseTestCase {

	
	@SpringBean("addressServiceImpl")
	AddressServiceImpl addressService;
	
	@SpringBean("fileServiceImpl")
	FileServiceImpl fileServiceImpl;
	
	@SpringBean("orderServiceImpl")
	OrderServiceImpl orderService;
	
	@Test
	public void test_01(){
		System.out.println(addressService.addressList("", 1l));
	}
	
	@Test
	public void test_02() throws Exception{
		InputStream inp = ServiceTest.class.getResourceAsStream("/swiper.jpg");
		ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        byte[] b = new byte[1024];  
        int n;  
        while ((n = inp.read(b)) != -1)  
        {  
            bos.write(b, 0, n);  
        }  
		System.out.println(fileServiceImpl.uploadByByte("swiper.jpg", bos.toByteArray(), "goods"));
	}
	
	@Test
	public void test_03() throws Exception{
		System.out.println(orderService.queryKdInfo(31l, 571664422));
	}
	
	
	@Test
	public void test_04() throws InterruptedException{
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				orderService.confirmAgain(116l, "201803011544205360129", UUID.randomUUID().toString().replace("-", ""), "13127856928", 1);
			}
		});
		t.setDaemon(true);
		t.start();
		Thread.sleep(100000);
		System.out.println();
	}
}
