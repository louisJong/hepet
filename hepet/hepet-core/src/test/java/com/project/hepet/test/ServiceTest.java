package com.project.hepet.test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.project.hepet.service.impl.AddressServiceImpl;
import com.project.hepet.service.impl.FileServiceImpl;

public class ServiceTest extends BaseTestCase {

	
	@SpringBean("addressServiceImpl")
	AddressServiceImpl addressService;
	
	@SpringBean("fileServiceImpl")
	FileServiceImpl fileServiceImpl;
	
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
	
}