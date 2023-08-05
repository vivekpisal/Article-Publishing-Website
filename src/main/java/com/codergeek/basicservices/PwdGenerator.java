package com.codergeek.basicservices;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.stereotype.Service;

@Service
public class PwdGenerator {
	
	public String getSecurePassword() {
		String password = null;
		CharacterRule LCR = new CharacterRule(EnglishCharacterData.LowerCase);
		LCR.setNumberOfCharacters(2);
		CharacterRule UCR = new CharacterRule(EnglishCharacterData.UpperCase);
		UCR.setNumberOfCharacters(2); 
		CharacterRule DR = new CharacterRule(EnglishCharacterData.Digit);  
		DR.setNumberOfCharacters(2);
		CharacterRule SR = new CharacterRule(EnglishCharacterData.Special);  
		SR.setNumberOfCharacters(2); 
		PasswordGenerator passGen = new PasswordGenerator();
		password = passGen.generatePassword(8, SR, LCR, UCR, DR);
		return password;
	}
}
