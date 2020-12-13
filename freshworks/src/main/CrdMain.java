package main;

import org.json.*;
import java.util.*;

public class CrdMain {
	public static void main(String[] args) {
		JSONObject jsonObject = new JSONObject();
		Scanner obj=new Scanner(System.in);
		while(true){
			
			System.out.println("1.CREATE");
			System.out.println("2.READ");
			System.out.println("3.DELETE");
			int operation=obj.nextInt();
			Crd myDataStore= new Crd();
			if(operation==1)
			{
				System.out.print("Enter the number of keys: ");
				int noOfKeys=obj.nextInt();
				for(int i=0;i<noOfKeys;i++){
					System.out.println("Enter JSON key");
					String key =obj.next();
					System.out.println("Enter JSON value");
					String value=obj.next();
					jsonObject.put(key, value);
				}
			System.out.println("Enter FILE key");
			String fkey=obj.next();
			System.out.println("Please enter TimeToLive else enter -1");
			int ttl= obj.nextInt();
			if(ttl==-1)	
				System.out.println(myDataStore.create(fkey, jsonObject));
			else
				System.out.println(myDataStore.create(fkey, jsonObject,ttl));
			}
			else if(operation==2){
				System.out.println("Enter file key");
				String fileKey= obj.next();

				System.out.println(myDataStore.read(fileKey));
			}
			else if(operation==3){
				System.out.println("Enter file key");
				String fileKey= obj.next();
				System.out.println(myDataStore.delete(fileKey));
				
			}
			System.out.println("To continue Press 1 else Press 0");
			int choice =obj.nextInt();
			if(choice==0){
				System.out.println("Operation Terminated!");
				break;
			}
				
			
		}
		

	}
}