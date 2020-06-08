package programming;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.ServerSocket;

class SharedCounter2{
	int count=100;
	synchronized void decrement() {
		count--;
	}
}
class Client1 extends Socket implements Runnable{
	SharedCounter2 counter;
	public Client1(SharedCounter2 counter, String host, int Port) throws IOException {
		super(host, Port);
		this.counter = counter;
	}
	public void run() {
		//������ colorscript�� ���� Client1 Ŭ�������� SharedCounterŬ������
		//�����ڷ� �޾Ұ� �ٸ� �޼����� run()���� �� Ŭ������ increment()�޼��忡 ����.
		//�ϴ� �� �����ϱ� Ȯ���غ���. wait()-notify()������ 
		//����ȭ �Ϸ�Ǿ����� Ȯ�� �ϴ� ���ݱ��� ��.
		for(int i=0;i<=51;i++) {
			counter.decrement();
		}
		
	}
}
class Server1 extends ServerSocket implements Runnable{
	SharedCounter2 counter;
	int Port;
	public Server1(SharedCounter2 counter, int Port) throws IOException{
		this.counter = counter;
		this.Port = Port;
	}
	public void run() {
		//client�� server�� �� �� ������ �Ѵ� 50�����ؼ� count�� 0�� �Ȱ� �޾�����
		//i�� 50������ �ع����� 0�� �Ȱ� �޴� �ǹ̰� ������� 51���� �ؼ� 0�� �Ǿ��⿡ ����
		//�ϵ��� ����.
		for(int i=0;i<=51;i++) {
			counter.decrement();
		}
		
	}
	public Socket accept() throws IOException{
		return super.accept();
	}
}
public class ClientEx1 {
	public static void main(String[] args) throws IOException{
		SharedCounter2 counter = new SharedCounter2();
		Client1 client = null;
		BufferedReader in = null;
		BufferedWriter out = null;
		try {
			client = new Client1(counter, "127.0.0.1",9999);
			in= new BufferedReader(new InputStreamReader(client.getInputStream()));
			out= new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			 
			while(true) {
				
				System.out.print("������>>");
				int count_client = counter.count;
				String outputMessage = Integer.toString(count_client);
				if(outputMessage.equalsIgnoreCase("0")) {
					out.write(outputMessage+"\n");
					out.flush();
					break;
				}
				out.write(outputMessage+"\n");
				out.flush();
				String inputMessage = in.readLine();
				//���� ���� 0�� �� �� �������� (Ŭ���̾�Ʈ�� ���� 100�� �޴°Ͱ���������)
				//�𸣴ϱ� �����κ��� ���� outputMessage�� 0�̶�� 
				//�� ��쿡�� �����ؾ� �ϴϱ� �Ȱ��� ����.
				if(inputMessage.equalsIgnoreCase("0")) {
					System.out.println("�����ʿ��� 0�̵Ǽ� ���������.");
					break;
				}
				//inputMessage�� �� ����̵� �ƴϵ� ����ؾ��ϴϱ�
				//��������� �����(�ٷ��� �ּ�)
				System.out.println("����: "+inputMessage);
				
			}
			
		}catch(IOException e) {
			//System.out.println("�ý����� client�� �����϶�� �ϳ׿�.");
			System.out.println(e.getMessage());
		}finally {
			try {
				if(client != null)	client.close();
			}catch(IOException e) {
				System.out.println("������ä���߿����߻�");
			}
			
		}
	}
}
