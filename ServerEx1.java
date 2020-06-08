package programming;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ServerEx1 {
	public static void main(String[] args) {
		SharedCounter2 counter = new SharedCounter2();
		Client1 client = null;
		Server1 server = null;
		BufferedReader in = null;
		BufferedWriter out = null;
		try {
			server = new Server1(counter,9999);
			System.out.println("������ ��ٸ��� �ֽ��ϴ�.");
			//�ٿ� ĳ����: Socket�� Client1���� 
			client = (Client1) server.accept();
			System.out.println("����Ǿ����.");
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			while(true) {
				String inputMessage = in.readLine();
				System.out.println("Ŭ���̾�Ʈ: "+ inputMessage);
				if(inputMessage.equalsIgnoreCase("0")) {
					System.out.println("Ŭ���̾�Ʈ�ʿ��� 0�� �Ǿ� �����Ҳ���.");
					break;
				}
				System.out.print("������>>");
				int count_server = counter.count;
				String outputMessage = Integer.toString(count_server);
				out.write(outputMessage+"\n");
				out.flush();
			}
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}
		
	}
}
