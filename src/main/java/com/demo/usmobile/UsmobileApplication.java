package com.demo.usmobile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class UsmobileApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsmobileApplication.class, args);
	}

}


//{
//	  "cycles": [
//	    {
//	      "mdn": "3543645654",
//	      "startDate": "21-12-2021",
//	      "endDate": "21-12-2023",
//	      "userId": "fdgfdg"
//	    },
//	    {
//	      "mdn": "3543645654",
//	      "startDate": "21-12-2021",
//	      "endDate": "21-12-2023",
//	      "userId": "fdgfdg"
//	    },
//	    {
//	      "mdn": "3543645654",
//	      "startDate": "21-12-2021",
//	      "endDate": "21-12-2023",
//	      "userId": "fdgfdg"
//	    }
//	  ]
//	}



//



//{
//	  "daily_usage": [
//	    {
//	      "id": "1",
//	      "mdn": "43645645",
//	      "userId": "dsfsedg",
//	      "usageDate": "21-12-2021",
//	      "usedInMb": "100"
//	    },
//	    {
//	      "id": "2",
//	      "mdn": "34634565",
//	      "userId": "sdgdfgd",
//	      "usageDate": "11-07-2024",
//	      "usedInMb": "200"
//	    },
//	    {
//	      "id": "3",
//	      "mdn": "43346432",
//	      "userId": "sgrdfgd",
//	      "usageDate": "12-09-2024",
//	      "usedInMb": "500"
//	    }
//	  ]
//	}


//Assume that the usage collection is updated every 15 minutes for the usedInMb field. 
//
//Assume that we can transfer an mdn (the phone number) from user A to user B.
//
//Assume that the userId provided is trustworthy (extracted from JWT) and from a higher level micro-service.
//
// 
//
//Create some APIs, assume all input and output format is JSON:
//
	//Get the current cycle daily usage for a given customer 
//
		//Input: userId, mdn 
//
		//Output: list of {date, daily usage}
//



	//Get the cycle history of a given mdn
//
		//Input: userId, mdn
//
		//Output: list of {cycleId, startDate, endDate}




//
	//Create a new user
//
		//Input: first name, last name, email, password
//
		//Output: {id, firstName, lastName, email}
//




	//Update existing user profile
//
		//Input: userId, firstname, last name, email
//
		//Output: {id, firstName, lastName, email}



