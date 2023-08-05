package com.codergeek.basicservices;


import java.util.function.Supplier;

import org.springframework.stereotype.Service;

@Service
public class OtpGenerator {
	
	Supplier<Integer> getOtp = ()->{
		return (int) (Math.random()*10);
	};
	
	public Integer generateOTP() {
		StringBuilder otp = new StringBuilder();
		for(int i=0;i<6;i++) {
			otp.append(getOtp.get());
		}
		return Integer.valueOf(otp.toString());
	}
}
